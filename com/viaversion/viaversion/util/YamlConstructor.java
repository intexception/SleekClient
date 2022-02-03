package com.viaversion.viaversion.util;

import org.yaml.snakeyaml.constructor.*;
import org.yaml.snakeyaml.nodes.*;
import java.util.concurrent.*;
import java.util.*;

public class YamlConstructor extends SafeConstructor
{
    public YamlConstructor() {
        this.yamlClassConstructors.put(NodeId.mapping, new ConstructYamlMap());
        this.yamlConstructors.put(Tag.OMAP, new ConstructYamlOmap());
    }
    
    class Map extends ConstructYamlMap
    {
        @Override
        public Object construct(final Node node) {
            final Object o = super.construct(node);
            if (o instanceof Map && !(o instanceof ConcurrentSkipListMap)) {
                return new ConcurrentSkipListMap((java.util.Map<?, ?>)o);
            }
            return o;
        }
    }
    
    class ConstructYamlOmap extends SafeConstructor.ConstructYamlOmap
    {
        @Override
        public Object construct(final Node node) {
            final Object o = super.construct(node);
            if (o instanceof Map && !(o instanceof ConcurrentSkipListMap)) {
                return new ConcurrentSkipListMap((java.util.Map<?, ?>)o);
            }
            return o;
        }
    }
}
