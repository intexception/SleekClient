package com.viaversion.viaversion.protocols.protocol1_13to1_12_2;

import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.legacy.*;
import com.viaversion.viaversion.libs.gson.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.legacyimpl.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.*;

public final class ChatRewriter
{
    public static final GsonComponentSerializer HOVER_GSON_SERIALIZER;
    
    public static String legacyTextToJsonString(final String message, final boolean itemData) {
        final Component component = Component.text(builder -> {
            if (itemData) {
                builder.decoration(TextDecoration.ITALIC, false);
            }
            builder.append(LegacyComponentSerializer.legacySection().deserialize(message));
            return;
        });
        return ((ComponentSerializer<Component, O, String>)GsonComponentSerializer.gson()).serialize(component);
    }
    
    public static String legacyTextToJsonString(final String legacyText) {
        return legacyTextToJsonString(legacyText, false);
    }
    
    public static JsonElement legacyTextToJson(final String legacyText) {
        return JsonParser.parseString(legacyTextToJsonString(legacyText, false));
    }
    
    public static String jsonToLegacyText(final String value) {
        try {
            final Component component = ((ComponentSerializer<I, Component, String>)ChatRewriter.HOVER_GSON_SERIALIZER).deserialize(value);
            return LegacyComponentSerializer.legacySection().serialize(component);
        }
        catch (Exception e) {
            Via.getPlatform().getLogger().warning("Error converting json text to legacy: " + value);
            e.printStackTrace();
            return "";
        }
    }
    
    @Deprecated
    public static void processTranslate(final JsonElement value) {
        Via.getManager().getProtocolManager().getProtocol(Protocol1_13To1_12_2.class).getComponentRewriter().processText(value);
    }
    
    static {
        HOVER_GSON_SERIALIZER = GsonComponentSerializer.builder().emitLegacyHoverEvent().legacyHoverEventSerializer(NBTLegacyHoverEventSerializer.get()).build();
    }
}
