package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.legacy;

import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.*;
import com.viaversion.viaversion.libs.kyori.adventure.util.*;
import java.util.regex.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.flattener.*;
import org.jetbrains.annotations.*;
import java.util.function.*;

public interface LegacyComponentSerializer extends ComponentSerializer<Component, TextComponent, String>, Buildable<LegacyComponentSerializer, Builder>
{
    public static final char SECTION_CHAR = '�';
    public static final char AMPERSAND_CHAR = '&';
    public static final char HEX_CHAR = '#';
    
    @NotNull
    default LegacyComponentSerializer legacySection() {
        return LegacyComponentSerializerImpl.Instances.SECTION;
    }
    
    @NotNull
    default LegacyComponentSerializer legacyAmpersand() {
        return LegacyComponentSerializerImpl.Instances.AMPERSAND;
    }
    
    @NotNull
    default LegacyComponentSerializer legacy(final char legacyCharacter) {
        if (legacyCharacter == '�') {
            return legacySection();
        }
        if (legacyCharacter == '&') {
            return legacyAmpersand();
        }
        return builder().character(legacyCharacter).build();
    }
    
    @Nullable
    default LegacyFormat parseChar(final char character) {
        return LegacyComponentSerializerImpl.legacyFormat(character);
    }
    
    @NotNull
    default Builder builder() {
        return new LegacyComponentSerializerImpl.BuilderImpl();
    }
    
    @NotNull
    TextComponent deserialize(@NotNull final String input);
    
    @NotNull
    String serialize(@NotNull final Component component);
    
    public interface Builder extends Buildable.Builder<LegacyComponentSerializer>
    {
        @NotNull
        Builder character(final char legacyCharacter);
        
        @NotNull
        Builder hexCharacter(final char legacyHexCharacter);
        
        @NotNull
        Builder extractUrls();
        
        @NotNull
        Builder extractUrls(@NotNull final Pattern pattern);
        
        @NotNull
        Builder extractUrls(@Nullable final Style style);
        
        @NotNull
        Builder extractUrls(@NotNull final Pattern pattern, @Nullable final Style style);
        
        @NotNull
        Builder hexColors();
        
        @NotNull
        Builder useUnusualXRepeatedCharacterHexFormat();
        
        @NotNull
        Builder flattener(@NotNull final ComponentFlattener flattener);
        
        @NotNull
        LegacyComponentSerializer build();
    }
    
    @ApiStatus.Internal
    public interface Provider
    {
        @ApiStatus.Internal
        @NotNull
        LegacyComponentSerializer legacyAmpersand();
        
        @ApiStatus.Internal
        @NotNull
        LegacyComponentSerializer legacySection();
        
        @ApiStatus.Internal
        @NotNull
        Consumer<Builder> legacy();
    }
}
