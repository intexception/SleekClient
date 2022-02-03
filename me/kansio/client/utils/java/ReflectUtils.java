package me.kansio.client.utils.java;

import java.util.*;
import org.reflections.scanners.*;
import org.reflections.*;
import java.util.stream.*;
import java.lang.annotation.*;

public class ReflectUtils
{
    public static <T> List<Class<? extends T>> getReflects(final String lIIlIIllIlIl, final Class<T> lIIlIIllIIlI) {
        return new Reflections(lIIlIIllIlIl, new Scanner[0]).getSubTypesOf(lIIlIIllIIlI).stream().filter(lIIlIIlIllll -> lIIlIIlIllll.getDeclaredAnnotation(NotUsable.class) == null).collect((Collector<? super Object, ?, List<Class<? extends T>>>)Collectors.toList());
    }
    
    public @interface NotUsable {
    }
}
