package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson;

import com.viaversion.viaversion.libs.kyori.adventure.text.*;
import com.viaversion.viaversion.libs.gson.*;
import java.io.*;
import com.viaversion.viaversion.libs.gson.stream.*;

final class BlockNBTComponentPosSerializer extends TypeAdapter<BlockNBTComponent.Pos>
{
    static final TypeAdapter<BlockNBTComponent.Pos> INSTANCE;
    
    private BlockNBTComponentPosSerializer() {
    }
    
    @Override
    public BlockNBTComponent.Pos read(final JsonReader in) throws IOException {
        final String string = in.nextString();
        try {
            return BlockNBTComponent.Pos.fromString(string);
        }
        catch (IllegalArgumentException ex) {
            throw new JsonParseException("Don't know how to turn " + string + " into a Position");
        }
    }
    
    @Override
    public void write(final JsonWriter out, final BlockNBTComponent.Pos value) throws IOException {
        out.value(value.asString());
    }
    
    static {
        INSTANCE = new BlockNBTComponentPosSerializer().nullSafe();
    }
}
