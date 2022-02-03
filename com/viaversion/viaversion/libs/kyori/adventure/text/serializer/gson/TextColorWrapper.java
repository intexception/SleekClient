package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson;

import org.jetbrains.annotations.*;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.*;
import com.viaversion.viaversion.libs.gson.stream.*;
import com.viaversion.viaversion.libs.gson.*;
import java.io.*;

final class TextColorWrapper
{
    @Nullable
    final TextColor color;
    @Nullable
    final TextDecoration decoration;
    final boolean reset;
    
    TextColorWrapper(@Nullable final TextColor color, @Nullable final TextDecoration decoration, final boolean reset) {
        this.color = color;
        this.decoration = decoration;
        this.reset = reset;
    }
    
    static final class Serializer extends TypeAdapter<TextColorWrapper>
    {
        static final Serializer INSTANCE;
        
        private Serializer() {
        }
        
        @Override
        public void write(final JsonWriter out, final TextColorWrapper value) {
            throw new JsonSyntaxException("Cannot write TextColorWrapper instances");
        }
        
        @Override
        public TextColorWrapper read(final JsonReader in) throws IOException {
            final String input = in.nextString();
            final TextColor color = TextColorSerializer.fromString(input);
            final TextDecoration decoration = TextDecoration.NAMES.value(input);
            final boolean reset = decoration == null && input.equals("reset");
            if (color == null && decoration == null && !reset) {
                throw new JsonParseException("Don't know how to parse " + input + " at " + in.getPath());
            }
            return new TextColorWrapper(color, decoration, reset);
        }
        
        static {
            INSTANCE = new Serializer();
        }
    }
}
