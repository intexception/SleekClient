package org.reflections.util;

import java.util.function.*;
import javassist.bytecode.*;
import javassist.bytecode.annotation.*;
import java.util.stream.*;
import java.util.*;

public class JavassistHelper
{
    public static boolean includeInvisibleTag;
    
    public static String fieldName(final ClassFile classFile, final FieldInfo object) {
        return String.format("%s.%s", classFile.getName(), object.getName());
    }
    
    public static String methodName(final ClassFile classFile, final MethodInfo object) {
        return String.format("%s.%s(%s)", classFile.getName(), object.getName(), String.join(", ", getParameters(object)));
    }
    
    public static boolean isPublic(final Object object) {
        if (object instanceof ClassFile) {
            return AccessFlag.isPublic(((ClassFile)object).getAccessFlags());
        }
        if (object instanceof FieldInfo) {
            return AccessFlag.isPublic(((FieldInfo)object).getAccessFlags());
        }
        return object instanceof MethodInfo && AccessFlag.isPublic(((MethodInfo)object).getAccessFlags());
    }
    
    public static Stream<MethodInfo> getMethods(final ClassFile classFile) {
        return classFile.getMethods().stream().filter(MethodInfo::isMethod);
    }
    
    public static Stream<MethodInfo> getConstructors(final ClassFile classFile) {
        return classFile.getMethods().stream().filter(methodInfo -> !methodInfo.isMethod());
    }
    
    public static List<String> getParameters(final MethodInfo method) {
        final List<String> result = new ArrayList<String>();
        final String descriptor = method.getDescriptor().substring(1);
        final Descriptor.Iterator iterator = new Descriptor.Iterator(descriptor);
        Integer prev = null;
        while (iterator.hasNext()) {
            final int cur = iterator.next();
            if (prev != null) {
                result.add(Descriptor.toString(descriptor.substring(prev, cur)));
            }
            prev = cur;
        }
        return result;
    }
    
    public static String getReturnType(final MethodInfo method) {
        String descriptor = method.getDescriptor();
        descriptor = descriptor.substring(descriptor.lastIndexOf(")") + 1);
        return Descriptor.toString(descriptor);
    }
    
    public static List<String> getAnnotations(final Function<String, AttributeInfo> function) {
        final Function<String, List<String>> names = function.andThen(attribute -> (attribute != null) ? attribute.getAnnotations() : null).andThen((Function<? super Object, ? extends List<String>>)JavassistHelper::annotationNames);
        final List<String> result = new ArrayList<String>(names.apply("RuntimeVisibleAnnotations"));
        if (JavassistHelper.includeInvisibleTag) {
            result.addAll(names.apply("RuntimeInvisibleAnnotations"));
        }
        return result;
    }
    
    public static List<List<String>> getParametersAnnotations(final MethodInfo method) {
        final Function<String, List<List<String>>> names = ((Function<String, AttributeInfo>)method::getAttribute).andThen(attribute -> (attribute != null) ? attribute.getAnnotations() : null).andThen(aa -> (aa != null) ? Stream.of(aa).map((Function<? super Annotation[], ?>)JavassistHelper::annotationNames).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()) : Collections.emptyList());
        final List<List<String>> visibleAnnotations = names.apply("RuntimeVisibleParameterAnnotations");
        if (!JavassistHelper.includeInvisibleTag) {
            return new ArrayList<List<String>>(visibleAnnotations);
        }
        final List<List<String>> invisibleAnnotations = names.apply("RuntimeInvisibleParameterAnnotations");
        if (invisibleAnnotations.isEmpty()) {
            return new ArrayList<List<String>>(visibleAnnotations);
        }
        final List<List<String>> result = new ArrayList<List<String>>();
        for (int i = 0; i < Math.max(visibleAnnotations.size(), invisibleAnnotations.size()); ++i) {
            final List<String> concat = new ArrayList<String>();
            if (i < visibleAnnotations.size()) {
                concat.addAll(visibleAnnotations.get(i));
            }
            if (i < invisibleAnnotations.size()) {
                concat.addAll(invisibleAnnotations.get(i));
            }
            result.add(concat);
        }
        return result;
    }
    
    private static List<String> annotationNames(final Annotation[] annotations) {
        return (List<String>)((annotations != null) ? Stream.of(annotations).map((Function<? super Annotation, ?>)Annotation::getTypeName).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()) : Collections.emptyList());
    }
    
    static {
        JavassistHelper.includeInvisibleTag = true;
    }
}
