package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson;

import com.viaversion.viaversion.libs.kyori.adventure.util.*;
import java.io.*;
import com.viaversion.viaversion.libs.gson.stream.*;
import com.viaversion.viaversion.libs.gson.*;

final class IndexedSerializer<E> extends TypeAdapter<E>
{
    private final String name;
    private final Index<String, E> map;
    
    public static <E> TypeAdapter<E> of(final String name, final Index<String, E> map) {
        return new IndexedSerializer<E>(name, map).nullSafe();
    }
    
    private IndexedSerializer(final String name, final Index<String, E> map) {
        this.name = name;
        this.map = map;
    }
    
    @Override
    public void write(final JsonWriter out, final E value) throws IOException {
        out.value(this.map.key(value));
    }
    
    @Override
    public E read(final JsonReader in) throws IOException {
        final String string = in.nextString();
        final E value = this.map.value(string);
        if (value != null) {
            return value;
        }
        throw new JsonParseException("invalid " + this.name + ":  " + string);
    }
}
