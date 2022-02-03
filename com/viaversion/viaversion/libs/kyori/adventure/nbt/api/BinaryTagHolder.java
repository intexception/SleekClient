package com.viaversion.viaversion.libs.kyori.adventure.nbt.api;

import org.jetbrains.annotations.*;
import com.viaversion.viaversion.libs.kyori.adventure.util.*;

public interface BinaryTagHolder
{
    @NotNull
    default <T, EX extends Exception> BinaryTagHolder encode(@NotNull final T nbt, @NotNull final Codec<? super T, String, ?, EX> codec) throws EX, Exception {
        return new BinaryTagHolderImpl(codec.encode((Object)nbt));
    }
    
    @NotNull
    default BinaryTagHolder of(@NotNull final String string) {
        return new BinaryTagHolderImpl(string);
    }
    
    @NotNull
    String string();
    
    @NotNull
     <T, DX extends Exception> T get(@NotNull final Codec<T, String, DX, ?> codec) throws DX, Exception;
}
