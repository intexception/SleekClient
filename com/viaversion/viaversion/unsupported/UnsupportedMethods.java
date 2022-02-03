package com.viaversion.viaversion.unsupported;

import java.util.*;
import java.lang.reflect.*;

public final class UnsupportedMethods
{
    private final String className;
    private final Set<String> methodNames;
    
    public UnsupportedMethods(final String className, final Set<String> methodNames) {
        this.className = className;
        this.methodNames = Collections.unmodifiableSet((Set<? extends String>)methodNames);
    }
    
    public String getClassName() {
        return this.className;
    }
    
    public final boolean findMatch() {
        try {
            for (final Method method : Class.forName(this.className).getDeclaredMethods()) {
                if (this.methodNames.contains(method.getName())) {
                    return true;
                }
            }
        }
        catch (ClassNotFoundException ex) {}
        return false;
    }
}
