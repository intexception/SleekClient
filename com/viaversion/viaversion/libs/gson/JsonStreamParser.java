package com.viaversion.viaversion.libs.gson;

import java.util.*;
import com.viaversion.viaversion.libs.gson.internal.*;
import com.viaversion.viaversion.libs.gson.stream.*;
import java.io.*;

public final class JsonStreamParser implements Iterator<JsonElement>
{
    private final JsonReader parser;
    private final Object lock;
    
    public JsonStreamParser(final String json) {
        this(new StringReader(json));
    }
    
    public JsonStreamParser(final Reader reader) {
        (this.parser = new JsonReader(reader)).setLenient(true);
        this.lock = new Object();
    }
    
    @Override
    public JsonElement next() throws JsonParseException {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }
        try {
            return Streams.parse(this.parser);
        }
        catch (StackOverflowError e) {
            throw new JsonParseException("Failed parsing JSON source to Json", e);
        }
        catch (OutOfMemoryError e2) {
            throw new JsonParseException("Failed parsing JSON source to Json", e2);
        }
        catch (JsonParseException e3) {
            throw (e3.getCause() instanceof EOFException) ? new NoSuchElementException() : e3;
        }
    }
    
    @Override
    public boolean hasNext() {
        synchronized (this.lock) {
            try {
                return this.parser.peek() != JsonToken.END_DOCUMENT;
            }
            catch (MalformedJsonException e) {
                throw new JsonSyntaxException(e);
            }
            catch (IOException e2) {
                throw new JsonIOException(e2);
            }
        }
    }
    
    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
