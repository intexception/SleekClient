package org.yaml.snakeyaml.composer;

import org.yaml.snakeyaml.parser.*;
import org.yaml.snakeyaml.resolver.*;
import org.yaml.snakeyaml.*;
import org.yaml.snakeyaml.error.*;
import java.util.*;
import org.yaml.snakeyaml.events.*;
import org.yaml.snakeyaml.nodes.*;

public class Composer
{
    protected final Parser parser;
    private final Resolver resolver;
    private final Map<String, Node> anchors;
    private final Set<Node> recursiveNodes;
    private int nonScalarAliasesCount;
    private final LoaderOptions loadingConfig;
    
    public Composer(final Parser parser, final Resolver resolver) {
        this(parser, resolver, new LoaderOptions());
    }
    
    public Composer(final Parser parser, final Resolver resolver, final LoaderOptions loadingConfig) {
        this.nonScalarAliasesCount = 0;
        this.parser = parser;
        this.resolver = resolver;
        this.anchors = new HashMap<String, Node>();
        this.recursiveNodes = new HashSet<Node>();
        this.loadingConfig = loadingConfig;
    }
    
    public boolean checkNode() {
        if (this.parser.checkEvent(Event.ID.StreamStart)) {
            this.parser.getEvent();
        }
        return !this.parser.checkEvent(Event.ID.StreamEnd);
    }
    
    public Node getNode() {
        this.parser.getEvent();
        final Node node = this.composeNode(null);
        this.parser.getEvent();
        this.anchors.clear();
        this.recursiveNodes.clear();
        return node;
    }
    
    public Node getSingleNode() {
        this.parser.getEvent();
        Node document = null;
        if (!this.parser.checkEvent(Event.ID.StreamEnd)) {
            document = this.getNode();
        }
        if (!this.parser.checkEvent(Event.ID.StreamEnd)) {
            final Event event = this.parser.getEvent();
            final Mark contextMark = (document != null) ? document.getStartMark() : null;
            throw new ComposerException("expected a single document in the stream", contextMark, "but found another document", event.getStartMark());
        }
        this.parser.getEvent();
        return document;
    }
    
    private Node composeNode(final Node parent) {
        if (parent != null) {
            this.recursiveNodes.add(parent);
        }
        Node node;
        if (this.parser.checkEvent(Event.ID.Alias)) {
            final AliasEvent event = (AliasEvent)this.parser.getEvent();
            final String anchor = event.getAnchor();
            if (!this.anchors.containsKey(anchor)) {
                throw new ComposerException(null, null, "found undefined alias " + anchor, event.getStartMark());
            }
            node = this.anchors.get(anchor);
            if (!(node instanceof ScalarNode)) {
                ++this.nonScalarAliasesCount;
                if (this.nonScalarAliasesCount > this.loadingConfig.getMaxAliasesForCollections()) {
                    throw new YAMLException("Number of aliases for non-scalar nodes exceeds the specified max=" + this.loadingConfig.getMaxAliasesForCollections());
                }
            }
            if (this.recursiveNodes.remove(node)) {
                node.setTwoStepsConstruction(true);
            }
        }
        else {
            final NodeEvent event2 = (NodeEvent)this.parser.peekEvent();
            final String anchor = event2.getAnchor();
            if (this.parser.checkEvent(Event.ID.Scalar)) {
                node = this.composeScalarNode(anchor);
            }
            else if (this.parser.checkEvent(Event.ID.SequenceStart)) {
                node = this.composeSequenceNode(anchor);
            }
            else {
                node = this.composeMappingNode(anchor);
            }
        }
        this.recursiveNodes.remove(parent);
        return node;
    }
    
    protected Node composeScalarNode(final String anchor) {
        final ScalarEvent ev = (ScalarEvent)this.parser.getEvent();
        final String tag = ev.getTag();
        boolean resolved = false;
        Tag nodeTag;
        if (tag == null || tag.equals("!")) {
            nodeTag = this.resolver.resolve(NodeId.scalar, ev.getValue(), ev.getImplicit().canOmitTagInPlainScalar());
            resolved = true;
        }
        else {
            nodeTag = new Tag(tag);
        }
        final Node node = new ScalarNode(nodeTag, resolved, ev.getValue(), ev.getStartMark(), ev.getEndMark(), ev.getScalarStyle());
        if (anchor != null) {
            node.setAnchor(anchor);
            this.anchors.put(anchor, node);
        }
        return node;
    }
    
    protected Node composeSequenceNode(final String anchor) {
        final SequenceStartEvent startEvent = (SequenceStartEvent)this.parser.getEvent();
        final String tag = startEvent.getTag();
        boolean resolved = false;
        Tag nodeTag;
        if (tag == null || tag.equals("!")) {
            nodeTag = this.resolver.resolve(NodeId.sequence, null, startEvent.getImplicit());
            resolved = true;
        }
        else {
            nodeTag = new Tag(tag);
        }
        final ArrayList<Node> children = new ArrayList<Node>();
        final SequenceNode node = new SequenceNode(nodeTag, resolved, children, startEvent.getStartMark(), null, startEvent.getFlowStyle());
        if (anchor != null) {
            node.setAnchor(anchor);
            this.anchors.put(anchor, node);
        }
        while (!this.parser.checkEvent(Event.ID.SequenceEnd)) {
            children.add(this.composeNode(node));
        }
        final Event endEvent = this.parser.getEvent();
        node.setEndMark(endEvent.getEndMark());
        return node;
    }
    
    protected Node composeMappingNode(final String anchor) {
        final MappingStartEvent startEvent = (MappingStartEvent)this.parser.getEvent();
        final String tag = startEvent.getTag();
        boolean resolved = false;
        Tag nodeTag;
        if (tag == null || tag.equals("!")) {
            nodeTag = this.resolver.resolve(NodeId.mapping, null, startEvent.getImplicit());
            resolved = true;
        }
        else {
            nodeTag = new Tag(tag);
        }
        final List<NodeTuple> children = new ArrayList<NodeTuple>();
        final MappingNode node = new MappingNode(nodeTag, resolved, children, startEvent.getStartMark(), null, startEvent.getFlowStyle());
        if (anchor != null) {
            node.setAnchor(anchor);
            this.anchors.put(anchor, node);
        }
        while (!this.parser.checkEvent(Event.ID.MappingEnd)) {
            this.composeMappingChildren(children, node);
        }
        final Event endEvent = this.parser.getEvent();
        node.setEndMark(endEvent.getEndMark());
        return node;
    }
    
    protected void composeMappingChildren(final List<NodeTuple> children, final MappingNode node) {
        final Node itemKey = this.composeKeyNode(node);
        if (itemKey.getTag().equals(Tag.MERGE)) {
            node.setMerged(true);
        }
        final Node itemValue = this.composeValueNode(node);
        children.add(new NodeTuple(itemKey, itemValue));
    }
    
    protected Node composeKeyNode(final MappingNode node) {
        return this.composeNode(node);
    }
    
    protected Node composeValueNode(final MappingNode node) {
        return this.composeNode(node);
    }
}
