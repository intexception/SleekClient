package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson;

import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.*;
import com.viaversion.viaversion.libs.kyori.adventure.util.*;
import com.viaversion.viaversion.libs.gson.*;
import org.jetbrains.annotations.*;
import java.util.function.*;

public interface GsonComponentSerializer extends ComponentSerializer<Component, Component, String>, Buildable<GsonComponentSerializer, Builder>
{
    @NotNull
    default GsonComponentSerializer gson() {
        return GsonComponentSerializerImpl.Instances.INSTANCE;
    }
    
    @NotNull
    default GsonComponentSerializer colorDownsamplingGson() {
        return GsonComponentSerializerImpl.Instances.LEGACY_INSTANCE;
    }
    
    default Builder builder() {
        return new GsonComponentSerializerImpl.BuilderImpl();
    }
    
    @NotNull
    Gson serializer();
    
    @NotNull
    UnaryOperator<GsonBuilder> populator();
    
    @NotNull
    Component deserializeFromTree(@NotNull final JsonElement input);
    
    @NotNull
    JsonElement serializeToTree(@NotNull final Component component);
    
    public interface Builder extends Buildable.Builder<GsonComponentSerializer>
    {
        @NotNull
        Builder downsampleColors();
        
        @NotNull
        Builder legacyHoverEventSerializer(@Nullable final LegacyHoverEventSerializer serializer);
        
        @NotNull
        Builder emitLegacyHoverEvent();
        
        @NotNull
        GsonComponentSerializer build();
    }
    
    @ApiStatus.Internal
    public interface Provider
    {
        @ApiStatus.Internal
        @NotNull
        GsonComponentSerializer gson();
        
        @ApiStatus.Internal
        @NotNull
        GsonComponentSerializer gsonLegacy();
        
        @ApiStatus.Internal
        @NotNull
        Consumer<Builder> builder();
    }
}
