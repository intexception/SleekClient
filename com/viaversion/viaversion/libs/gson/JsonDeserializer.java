package com.viaversion.viaversion.libs.gson;

import java.lang.reflect.*;

public interface JsonDeserializer<T>
{
    T deserialize(final JsonElement p0, final Type p1, final JsonDeserializationContext p2) throws JsonParseException;
}
