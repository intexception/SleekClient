package com.viaversion.viaversion.libs.gson;

import com.viaversion.viaversion.libs.gson.stream.*;
import java.io.*;
import com.viaversion.viaversion.libs.gson.internal.bind.*;

public abstract class TypeAdapter<T>
{
    public abstract void write(final JsonWriter p0, final T p1) throws IOException;
    
    public final void toJson(final Writer out, final T value) throws IOException {
        final JsonWriter writer = new JsonWriter(out);
        this.write(writer, value);
    }
    
    public final TypeAdapter<T> nullSafe() {
        return new TypeAdapter<T>() {
            @Override
            public void write(final JsonWriter out, final T value) throws IOException {
                if (value == null) {
                    out.nullValue();
                }
                else {
                    TypeAdapter.this.write(out, value);
                }
            }
            
            @Override
            public T read(final JsonReader reader) throws IOException {
                if (reader.peek() == JsonToken.NULL) {
                    reader.nextNull();
                    return null;
                }
                return TypeAdapter.this.read(reader);
            }
        };
    }
    
    public final String toJson(final T value) {
        final StringWriter stringWriter = new StringWriter();
        try {
            this.toJson(stringWriter, value);
        }
        catch (IOException e) {
            throw new AssertionError((Object)e);
        }
        return stringWriter.toString();
    }
    
    public final JsonElement toJsonTree(final T value) {
        try {
            final JsonTreeWriter jsonWriter = new JsonTreeWriter();
            this.write(jsonWriter, value);
            return jsonWriter.get();
        }
        catch (IOException e) {
            throw new JsonIOException(e);
        }
    }
    
    public abstract T read(final JsonReader p0) throws IOException;
    
    public final T fromJson(final Reader in) throws IOException {
        final JsonReader reader = new JsonReader(in);
        return this.read(reader);
    }
    
    public final T fromJson(final String json) throws IOException {
        return this.fromJson(new StringReader(json));
    }
    
    public final T fromJsonTree(final JsonElement jsonTree) {
        try {
            final JsonReader jsonReader = new JsonTreeReader(jsonTree);
            return this.read(jsonReader);
        }
        catch (IOException e) {
            throw new JsonIOException(e);
        }
    }
}
