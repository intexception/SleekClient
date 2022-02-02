package org.reflections.util;

import java.util.stream.*;
import java.lang.reflect.*;
import org.reflections.*;
import javax.annotation.*;
import java.util.*;
import java.util.function.*;

public interface NameHelper
{
    public static final List<String> primitiveNames = Arrays.asList("boolean", "char", "byte", "short", "int", "long", "float", "double", "void");
    public static final List<Class<?>> primitiveTypes = Arrays.asList(Boolean.TYPE, Character.TYPE, Byte.TYPE, Short.TYPE, Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE, Void.TYPE);
    public static final List<String> primitiveDescriptors = Arrays.asList("Z", "C", "B", "S", "I", "J", "F", "D", "V");
    
    default String toName(final AnnotatedElement element) {
        return element.getClass().equals(Class.class) ? this.toName((Class<?>)element) : (element.getClass().equals(Constructor.class) ? this.toName((Constructor<?>)element) : (element.getClass().equals(Method.class) ? this.toName((Method)element) : (element.getClass().equals(Field.class) ? this.toName((Field)element) : null)));
    }
    
    default String toName(Class<?> type) {
        int dim = 0;
        while (type.isArray()) {
            ++dim;
            type = type.getComponentType();
        }
        return type.getName() + String.join("", Collections.nCopies(dim, "[]"));
    }
    
    default String toName(final Constructor<?> constructor) {
        return String.format("%s.<init>(%s)", constructor.getName(), String.join(", ", this.toNames((AnnotatedElement[])constructor.getParameterTypes())));
    }
    
    default String toName(final Method method) {
        return String.format("%s.%s(%s)", method.getDeclaringClass().getName(), method.getName(), String.join(", ", this.toNames((AnnotatedElement[])method.getParameterTypes())));
    }
    
    default String toName(final Field field) {
        return String.format("%s.%s", field.getDeclaringClass().getName(), field.getName());
    }
    
    default Collection<String> toNames(final Collection<? extends AnnotatedElement> elements) {
        return elements.stream().map((Function<? super AnnotatedElement, ?>)this::toName).filter(Objects::nonNull).collect((Collector<? super Object, ?, Collection<String>>)Collectors.toList());
    }
    
    default Collection<String> toNames(final AnnotatedElement... elements) {
        return this.toNames(Arrays.asList(elements));
    }
    
    default <T> T forName(final String name, final Class<T> resultType, final ClassLoader... loaders) {
        return (T)(resultType.equals(Class.class) ? this.forClass(name, loaders) : (resultType.equals(Constructor.class) ? this.forConstructor(name, loaders) : (resultType.equals(Method.class) ? this.forMethod(name, loaders) : (resultType.equals(Field.class) ? this.forField(name, loaders) : (resultType.equals(Member.class) ? this.forMember(name, loaders) : null)))));
    }
    
    default Class<?> forClass(final String typeName, final ClassLoader... loaders) {
        if (NameHelper.primitiveNames.contains(typeName)) {
            return NameHelper.primitiveTypes.get(NameHelper.primitiveNames.indexOf(typeName));
        }
        String type;
        if (typeName.contains("[")) {
            final int i = typeName.indexOf("[");
            type = typeName.substring(0, i);
            final String array = typeName.substring(i).replace("]", "");
            if (NameHelper.primitiveNames.contains(type)) {
                type = NameHelper.primitiveDescriptors.get(NameHelper.primitiveNames.indexOf(type));
            }
            else {
                type = "L" + type + ";";
            }
            type = array + type;
        }
        else {
            type = typeName;
        }
        final ClassLoader[] classLoaders = ClasspathHelper.classLoaders(loaders);
        final int length = classLoaders.length;
        int j = 0;
        while (j < length) {
            final ClassLoader classLoader = classLoaders[j];
            if (type.contains("[")) {
                try {
                    return Class.forName(type, false, classLoader);
                }
                catch (Throwable t) {}
            }
            try {
                return classLoader.loadClass(type);
            }
            catch (Throwable t2) {
                ++j;
                continue;
            }
            break;
        }
        return null;
    }
    
    default Member forMember(final String descriptor, final ClassLoader... loaders) throws ReflectionsException {
        final int p0 = descriptor.lastIndexOf(40);
        final String memberKey = (p0 != -1) ? descriptor.substring(0, p0) : descriptor;
        final String methodParameters = (p0 != -1) ? descriptor.substring(p0 + 1, descriptor.lastIndexOf(41)) : "";
        final int p2 = Math.max(memberKey.lastIndexOf(46), memberKey.lastIndexOf("$"));
        final String className = memberKey.substring(0, p2);
        final String memberName = memberKey.substring(p2 + 1);
        Class<?>[] parameterTypes = null;
        if (!methodParameters.isEmpty()) {
            final String[] parameterNames = methodParameters.split(",");
            parameterTypes = Arrays.stream(parameterNames).map(name -> this.forClass(name.trim(), loaders)).toArray(Class[]::new);
        }
        Class<?> aClass;
        try {
            aClass = this.forClass(className, loaders);
        }
        catch (Exception e) {
            return null;
        }
        while (aClass != null) {
            try {
                if (!descriptor.contains("(")) {
                    return aClass.isInterface() ? aClass.getField(memberName) : aClass.getDeclaredField(memberName);
                }
                if (descriptor.contains("init>")) {
                    return aClass.isInterface() ? aClass.getConstructor(parameterTypes) : aClass.getDeclaredConstructor(parameterTypes);
                }
                return aClass.isInterface() ? aClass.getMethod(memberName, parameterTypes) : aClass.getDeclaredMethod(memberName, parameterTypes);
            }
            catch (Exception e) {
                aClass = aClass.getSuperclass();
                continue;
            }
            break;
        }
        return null;
    }
    
    @Nullable
    default <T extends AnnotatedElement> T forElement(final String descriptor, final Class<T> resultType, final ClassLoader[] loaders) {
        final Member member = this.forMember(descriptor, loaders);
        return (T)((member != null && member.getClass().equals(resultType)) ? ((AnnotatedElement)member) : null);
    }
    
    @Nullable
    default Method forMethod(final String descriptor, final ClassLoader... loaders) throws ReflectionsException {
        return this.forElement(descriptor, Method.class, loaders);
    }
    
    default Constructor<?> forConstructor(final String descriptor, final ClassLoader... loaders) throws ReflectionsException {
        return this.forElement(descriptor, (Class<Constructor<?>>)Constructor.class, loaders);
    }
    
    @Nullable
    default Field forField(final String descriptor, final ClassLoader... loaders) {
        return this.forElement(descriptor, Field.class, loaders);
    }
    
    default <T> Collection<T> forNames(final Collection<String> names, final Class<T> resultType, final ClassLoader... loaders) {
        return names.stream().map(name -> this.forName(name, resultType, loaders)).filter(Objects::nonNull).collect((Collector<? super Object, ?, Collection<T>>)Collectors.toCollection((Supplier<R>)LinkedHashSet::new));
    }
    
    default Collection<Class<?>> forNames(final Collection<String> names, final ClassLoader... loaders) {
        return this.forNames(names, (Class<Class<?>>)Class.class, loaders);
    }
}
