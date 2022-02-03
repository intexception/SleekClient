package org.yaml.snakeyaml.representer;

import org.yaml.snakeyaml.*;
import org.yaml.snakeyaml.introspector.*;
import org.yaml.snakeyaml.nodes.*;
import java.util.*;

public class Representer extends SafeRepresenter
{
    protected Map<Class<?>, TypeDescription> typeDefinitions;
    
    public Representer() {
        this.typeDefinitions = Collections.emptyMap();
        this.representers.put(null, new RepresentJavaBean());
    }
    
    public Representer(final DumperOptions options) {
        super(options);
        this.typeDefinitions = Collections.emptyMap();
        this.representers.put(null, new RepresentJavaBean());
    }
    
    public TypeDescription addTypeDescription(final TypeDescription td) {
        if (Collections.EMPTY_MAP == this.typeDefinitions) {
            this.typeDefinitions = new HashMap<Class<?>, TypeDescription>();
        }
        if (td.getTag() != null) {
            this.addClassTag(td.getType(), td.getTag());
        }
        td.setPropertyUtils(this.getPropertyUtils());
        return this.typeDefinitions.put(td.getType(), td);
    }
    
    @Override
    public void setPropertyUtils(final PropertyUtils propertyUtils) {
        super.setPropertyUtils(propertyUtils);
        final Collection<TypeDescription> tds = this.typeDefinitions.values();
        for (final TypeDescription typeDescription : tds) {
            typeDescription.setPropertyUtils(propertyUtils);
        }
    }
    
    protected MappingNode representJavaBean(final Set<Property> properties, final Object javaBean) {
        final List<NodeTuple> value = new ArrayList<NodeTuple>(properties.size());
        final Tag customTag = this.classTags.get(javaBean.getClass());
        final Tag tag = (customTag != null) ? customTag : new Tag(javaBean.getClass());
        final MappingNode node = new MappingNode(tag, value, DumperOptions.FlowStyle.AUTO);
        this.representedObjects.put(javaBean, node);
        DumperOptions.FlowStyle bestStyle = DumperOptions.FlowStyle.FLOW;
        for (final Property property : properties) {
            final Object memberValue = property.get(javaBean);
            final Tag customPropertyTag = (memberValue == null) ? null : this.classTags.get(memberValue.getClass());
            final NodeTuple tuple = this.representJavaBeanProperty(javaBean, property, memberValue, customPropertyTag);
            if (tuple == null) {
                continue;
            }
            if (!((ScalarNode)tuple.getKeyNode()).isPlain()) {
                bestStyle = DumperOptions.FlowStyle.BLOCK;
            }
            final Node nodeValue = tuple.getValueNode();
            if (!(nodeValue instanceof ScalarNode) || !((ScalarNode)nodeValue).isPlain()) {
                bestStyle = DumperOptions.FlowStyle.BLOCK;
            }
            value.add(tuple);
        }
        if (this.defaultFlowStyle != DumperOptions.FlowStyle.AUTO) {
            node.setFlowStyle(this.defaultFlowStyle);
        }
        else {
            node.setFlowStyle(bestStyle);
        }
        return node;
    }
    
    protected NodeTuple representJavaBeanProperty(final Object javaBean, final Property property, final Object propertyValue, final Tag customTag) {
        final ScalarNode nodeKey = (ScalarNode)this.representData(property.getName());
        final boolean hasAlias = this.representedObjects.containsKey(propertyValue);
        final Node nodeValue = this.representData(propertyValue);
        if (propertyValue != null && !hasAlias) {
            final NodeId nodeId = nodeValue.getNodeId();
            if (customTag == null) {
                if (nodeId == NodeId.scalar) {
                    if (property.getType() != Enum.class && propertyValue instanceof Enum) {
                        nodeValue.setTag(Tag.STR);
                    }
                }
                else {
                    if (nodeId == NodeId.mapping && property.getType() == propertyValue.getClass() && !(propertyValue instanceof Map) && !nodeValue.getTag().equals(Tag.SET)) {
                        nodeValue.setTag(Tag.MAP);
                    }
                    this.checkGlobalTag(property, nodeValue, propertyValue);
                }
            }
        }
        return new NodeTuple(nodeKey, nodeValue);
    }
    
    protected void checkGlobalTag(final Property property, final Node node, final Object object) {
        if (object.getClass().isArray() && object.getClass().getComponentType().isPrimitive()) {
            return;
        }
        final Class<?>[] arguments = property.getActualTypeArguments();
        if (arguments != null) {
            if (node.getNodeId() == NodeId.sequence) {
                final Class<?> t = arguments[0];
                final SequenceNode snode = (SequenceNode)node;
                Iterable<Object> memberList = (Iterable<Object>)Collections.EMPTY_LIST;
                if (object.getClass().isArray()) {
                    memberList = Arrays.asList((Object[])object);
                }
                else if (object instanceof Iterable) {
                    memberList = (Iterable<Object>)object;
                }
                final Iterator<Object> iter = memberList.iterator();
                if (iter.hasNext()) {
                    for (final Node childNode : snode.getValue()) {
                        final Object member = iter.next();
                        if (member != null && t.equals(member.getClass()) && childNode.getNodeId() == NodeId.mapping) {
                            childNode.setTag(Tag.MAP);
                        }
                    }
                }
            }
            else if (object instanceof Set) {
                final Class<?> t = arguments[0];
                final MappingNode mnode = (MappingNode)node;
                final Iterator<NodeTuple> iter2 = mnode.getValue().iterator();
                final Set<?> set = (Set<?>)object;
                for (final Object member2 : set) {
                    final NodeTuple tuple = iter2.next();
                    final Node keyNode = tuple.getKeyNode();
                    if (t.equals(member2.getClass()) && keyNode.getNodeId() == NodeId.mapping) {
                        keyNode.setTag(Tag.MAP);
                    }
                }
            }
            else if (object instanceof Map) {
                final Class<?> keyType = arguments[0];
                final Class<?> valueType = arguments[1];
                final MappingNode mnode2 = (MappingNode)node;
                for (final NodeTuple tuple2 : mnode2.getValue()) {
                    this.resetTag(keyType, tuple2.getKeyNode());
                    this.resetTag(valueType, tuple2.getValueNode());
                }
            }
        }
    }
    
    private void resetTag(final Class<?> type, final Node node) {
        final Tag tag = node.getTag();
        if (tag.matches(type)) {
            if (Enum.class.isAssignableFrom(type)) {
                node.setTag(Tag.STR);
            }
            else {
                node.setTag(Tag.MAP);
            }
        }
    }
    
    protected Set<Property> getProperties(final Class<?> type) {
        if (this.typeDefinitions.containsKey(type)) {
            return this.typeDefinitions.get(type).getProperties();
        }
        return this.getPropertyUtils().getProperties(type);
    }
    
    protected class RepresentJavaBean implements Represent
    {
        @Override
        public Node representData(final Object data) {
            return Representer.this.representJavaBean(Representer.this.getProperties(data.getClass()), data);
        }
    }
}
