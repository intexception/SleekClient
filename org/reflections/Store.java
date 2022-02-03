package org.reflections;

import java.util.*;

public class Store extends HashMap<String, Map<String, Set<String>>>
{
    public Store() {
    }
    
    public Store(final Map<String, Map<String, Set<String>>> storeMap) {
        super(storeMap);
    }
}
