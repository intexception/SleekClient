package io.netty.handler.codec.serialization;

import java.lang.ref.*;
import java.util.*;
import io.netty.util.internal.*;

public final class ClassResolvers
{
    public static ClassResolver cacheDisabled(final ClassLoader classLoader) {
        return new ClassLoaderClassResolver(defaultClassLoader(classLoader));
    }
    
    public static ClassResolver weakCachingResolver(final ClassLoader classLoader) {
        return new CachingClassResolver(new ClassLoaderClassResolver(defaultClassLoader(classLoader)), new WeakReferenceMap<String, Class<?>>(new HashMap<String, Reference<Class<?>>>()));
    }
    
    public static ClassResolver softCachingResolver(final ClassLoader classLoader) {
        return new CachingClassResolver(new ClassLoaderClassResolver(defaultClassLoader(classLoader)), new SoftReferenceMap<String, Class<?>>(new HashMap<String, Reference<Class<?>>>()));
    }
    
    public static ClassResolver weakCachingConcurrentResolver(final ClassLoader classLoader) {
        return new CachingClassResolver(new ClassLoaderClassResolver(defaultClassLoader(classLoader)), new WeakReferenceMap<String, Class<?>>((Map<String, Reference<Class<?>>>)PlatformDependent.newConcurrentHashMap()));
    }
    
    public static ClassResolver softCachingConcurrentResolver(final ClassLoader classLoader) {
        return new CachingClassResolver(new ClassLoaderClassResolver(defaultClassLoader(classLoader)), new SoftReferenceMap<String, Class<?>>((Map<String, Reference<Class<?>>>)PlatformDependent.newConcurrentHashMap()));
    }
    
    static ClassLoader defaultClassLoader(final ClassLoader classLoader) {
        if (classLoader != null) {
            return classLoader;
        }
        final ClassLoader contextClassLoader = PlatformDependent.getContextClassLoader();
        if (contextClassLoader != null) {
            return contextClassLoader;
        }
        return PlatformDependent.getClassLoader(ClassResolvers.class);
    }
    
    private ClassResolvers() {
    }
}
