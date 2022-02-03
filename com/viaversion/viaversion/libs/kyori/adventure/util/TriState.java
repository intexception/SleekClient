package com.viaversion.viaversion.libs.kyori.adventure.util;

import org.jetbrains.annotations.*;

public enum TriState
{
    NOT_SET, 
    FALSE, 
    TRUE;
    
    @NotNull
    public static TriState byBoolean(final boolean value) {
        return value ? TriState.TRUE : TriState.FALSE;
    }
    
    @NotNull
    public static TriState byBoolean(@Nullable final Boolean value) {
        return (value == null) ? TriState.NOT_SET : byBoolean((boolean)value);
    }
}
