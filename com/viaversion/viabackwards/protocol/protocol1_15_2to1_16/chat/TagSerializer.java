package com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.chat;

import java.util.regex.*;
import com.google.common.base.*;
import java.util.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import com.viaversion.viaversion.libs.gson.*;

@Deprecated
public class TagSerializer
{
    private static final Pattern PLAIN_TEXT;
    
    public static String toString(final JsonObject object) {
        final StringBuilder builder = new StringBuilder("{");
        for (final Map.Entry<String, JsonElement> entry : object.entrySet()) {
            Preconditions.checkArgument(entry.getValue().isJsonPrimitive());
            if (builder.length() != 1) {
                builder.append(',');
            }
            final String escapedText = escape(entry.getValue().getAsString());
            builder.append(entry.getKey()).append(':').append(escapedText);
        }
        return builder.append('}').toString();
    }
    
    public static JsonObject toJson(final CompoundTag tag) {
        final JsonObject object = new JsonObject();
        for (final Map.Entry<String, Tag> entry : tag.entrySet()) {
            object.add(entry.getKey(), toJson(entry.getValue()));
        }
        return object;
    }
    
    private static JsonElement toJson(final Tag tag) {
        if (tag instanceof CompoundTag) {
            return toJson((CompoundTag)tag);
        }
        if (tag instanceof ListTag) {
            final ListTag list = (ListTag)tag;
            final JsonArray array = new JsonArray();
            for (final Tag listEntry : list) {
                array.add(toJson(listEntry));
            }
            return array;
        }
        return new JsonPrimitive(tag.getValue().toString());
    }
    
    public static String escape(final String s) {
        if (TagSerializer.PLAIN_TEXT.matcher(s).matches()) {
            return s;
        }
        final StringBuilder builder = new StringBuilder(" ");
        char currentQuote = '\0';
        for (int i = 0; i < s.length(); ++i) {
            final char c = s.charAt(i);
            if (c == '\\') {
                builder.append('\\');
            }
            else if (c == '\"' || c == '\'') {
                if (currentQuote == '\0') {
                    currentQuote = ((c == '\"') ? '\'' : '\"');
                }
                if (currentQuote == c) {
                    builder.append('\\');
                }
            }
            builder.append(c);
        }
        if (currentQuote == '\0') {
            currentQuote = '\"';
        }
        builder.setCharAt(0, currentQuote);
        builder.append(currentQuote);
        return builder.toString();
    }
    
    static {
        PLAIN_TEXT = Pattern.compile("[A-Za-z0-9._+-]+");
    }
}
