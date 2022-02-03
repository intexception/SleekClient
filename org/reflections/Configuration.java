package org.reflections;

import java.util.*;
import org.reflections.scanners.*;
import java.net.*;
import java.util.function.*;

public interface Configuration
{
    Set<Scanner> getScanners();
    
    Set<URL> getUrls();
    
    Predicate<String> getInputsFilter();
    
    boolean isParallel();
    
    ClassLoader[] getClassLoaders();
    
    boolean shouldExpandSuperTypes();
}
