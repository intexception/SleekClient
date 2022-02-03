package com.viaversion.viaversion.libs.gson;

import com.viaversion.viaversion.libs.gson.reflect.*;

public interface TypeAdapterFactory
{
     <T> TypeAdapter<T> create(final Gson p0, final TypeToken<T> p1);
}
