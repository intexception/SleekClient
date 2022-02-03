package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson;

import com.viaversion.viaversion.libs.gson.*;
import com.viaversion.viaversion.libs.kyori.adventure.key.*;
import java.io.*;
import com.viaversion.viaversion.libs.gson.stream.*;

final class KeySerializer extends TypeAdapter<Key>
{
    static final TypeAdapter<Key> INSTANCE;
    
    private KeySerializer() {
    }
    
    @Override
    public void write(final JsonWriter out, final Key value) throws IOException {
        out.value(value.asString());
    }
    
    @Override
    public Key read(final JsonReader in) throws IOException {
        return Key.key(in.nextString());
    }
    
    static {
        INSTANCE = new KeySerializer().nullSafe();
    }
}
