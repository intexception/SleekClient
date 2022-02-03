package org.reflections.util;

import java.util.regex.*;
import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;
import org.reflections.*;

public class ReflectionUtilsPredicates
{
    public static <T extends Member> Predicate<T> withName(final String name) {
        return input -> input != null && input.getName().equals(name);
    }
    
    public static <T extends Member> Predicate<T> withPrefix(final String prefix) {
        return input -> input != null && input.getName().startsWith(prefix);
    }
    
    public static <T> Predicate<T> withNamePrefix(final String prefix) {
        return input -> toName(input).startsWith(prefix);
    }
    
    public static <T extends AnnotatedElement> Predicate<T> withPattern(final String regex) {
        return input -> Pattern.matches(regex, input.toString());
    }
    
    public static <T extends AnnotatedElement> Predicate<T> withAnnotation(final Class<? extends Annotation> annotation) {
        return input -> input != null && input.isAnnotationPresent(annotation);
    }
    
    public static <T extends AnnotatedElement> Predicate<T> withAnnotations(final Class<? extends Annotation>... annotations) {
        return input -> input != null && Arrays.equals(annotations, annotationTypes(input.getAnnotations()));
    }
    
    public static <T extends AnnotatedElement> Predicate<T> withAnnotation(final Annotation annotation) {
        return input -> input != null && input.isAnnotationPresent(annotation.annotationType()) && areAnnotationMembersMatching(input.getAnnotation(annotation.annotationType()), annotation);
    }
    
    public static <T extends AnnotatedElement> Predicate<T> withAnnotations(final Annotation... annotations) {
        Annotation[] inputAnnotations;
        return input -> {
            if (input != null) {
                inputAnnotations = input.getAnnotations();
                if (inputAnnotations.length == annotations.length) {
                    return IntStream.range(0, inputAnnotations.length).allMatch(i -> areAnnotationMembersMatching(inputAnnotations[i], annotations[i]));
                }
            }
            return true;
        };
    }
    
    public static Predicate<Member> withParameters(final Class<?>... types) {
        return input -> Arrays.equals(parameterTypes(input), types);
    }
    
    public static Predicate<Member> withParametersAssignableTo(final Class... types) {
        return input -> isAssignable(types, parameterTypes(input));
    }
    
    public static Predicate<Member> withParametersAssignableFrom(final Class... types) {
        return input -> isAssignable(parameterTypes(input), types);
    }
    
    public static Predicate<Member> withParametersCount(final int count) {
        return input -> input != null && parameterTypes(input).length == count;
    }
    
    public static Predicate<Member> withAnyParameterAnnotation(final Class<? extends Annotation> annotationClass) {
        return input -> input != null && annotationTypes(parameterAnnotations(input)).stream().anyMatch(input1 -> input1.equals(annotationClass));
    }
    
    public static Predicate<Member> withAnyParameterAnnotation(final Annotation annotation) {
        return input -> input != null && parameterAnnotations(input).stream().anyMatch(input1 -> areAnnotationMembersMatching(annotation, input1));
    }
    
    public static <T> Predicate<Field> withType(final Class<T> type) {
        return input -> input != null && input.getType().equals(type);
    }
    
    public static <T> Predicate<Field> withTypeAssignableTo(final Class<T> type) {
        return input -> input != null && type.isAssignableFrom(input.getType());
    }
    
    public static <T> Predicate<Method> withReturnType(final Class<T> type) {
        return input -> input != null && input.getReturnType().equals(type);
    }
    
    public static <T> Predicate<Method> withReturnTypeAssignableFrom(final Class<T> type) {
        return input -> input != null && type.isAssignableFrom(input.getReturnType());
    }
    
    public static <T extends Member> Predicate<T> withModifier(final int mod) {
        return input -> input != null && (input.getModifiers() & mod) != 0x0;
    }
    
    public static <T extends Member> Predicate<T> withPublic() {
        return withModifier(1);
    }
    
    public static <T extends Member> Predicate<T> withStatic() {
        return withModifier(8);
    }
    
    public static <T extends Member> Predicate<T> withInterface() {
        return withModifier(512);
    }
    
    public static Predicate<Class<?>> withClassModifier(final int mod) {
        return input -> input != null && (input.getModifiers() & mod) != 0x0;
    }
    
    public static boolean isAssignable(final Class[] childClasses, final Class[] parentClasses) {
        if (childClasses == null || childClasses.length == 0) {
            return parentClasses == null || parentClasses.length == 0;
        }
        return childClasses.length == parentClasses.length && IntStream.range(0, childClasses.length).noneMatch(i -> !parentClasses[i].isAssignableFrom(childClasses[i]) || (parentClasses[i] == Object.class && childClasses[i] != Object.class));
    }
    
    private static String toName(final Object input) {
        return (input == null) ? "" : (input.getClass().equals(Class.class) ? ((Class)input).getName() : ((input instanceof Member) ? ((Member)input).getName() : ((input instanceof Annotation) ? ((Annotation)input).annotationType().getName() : input.toString())));
    }
    
    private static Class[] parameterTypes(final Member member) {
        return (Class[])((member != null) ? ((member.getClass() == Method.class) ? ((Method)member).getParameterTypes() : ((member.getClass() == Constructor.class) ? ((Constructor)member).getParameterTypes() : null)) : null);
    }
    
    private static Set<Annotation> parameterAnnotations(final Member member) {
        final Annotation[][] annotations = (member instanceof Method) ? ((Method)member).getParameterAnnotations() : ((member instanceof Constructor) ? ((Constructor)member).getParameterAnnotations() : null);
        return Arrays.stream((annotations != null) ? annotations : new Annotation[0][]).flatMap((Function<? super Annotation[], ? extends Stream<?>>)Arrays::stream).collect((Collector<? super Object, ?, Set<Annotation>>)Collectors.toSet());
    }
    
    private static Set<Class<? extends Annotation>> annotationTypes(final Collection<Annotation> annotations) {
        return annotations.stream().map((Function<? super Annotation, ?>)Annotation::annotationType).collect((Collector<? super Object, ?, Set<Class<? extends Annotation>>>)Collectors.toSet());
    }
    
    private static Class<? extends Annotation>[] annotationTypes(final Annotation[] annotations) {
        return Arrays.stream(annotations).map((Function<? super Annotation, ?>)Annotation::annotationType).toArray(Class[]::new);
    }
    
    private static boolean areAnnotationMembersMatching(final Annotation annotation1, final Annotation annotation2) {
        if (annotation2 != null && annotation1.annotationType() == annotation2.annotationType()) {
            for (final Method method : annotation1.annotationType().getDeclaredMethods()) {
                if (!ReflectionUtils.invoke(method, annotation1, new Object[0]).equals(ReflectionUtils.invoke(method, annotation2, new Object[0]))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
