package com.viaversion.viaversion.libs.gson;

import java.lang.reflect.*;

public interface JsonSerializer<T>
{
    JsonElement serialize(final T p0, final Type p1, final JsonSerializationContext p2);
}
