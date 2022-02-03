package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.libs.gson.*;

public class ComponentType extends Type<JsonElement>
{
    private static final StringType STRING_TAG;
    
    public ComponentType() {
        super(JsonElement.class);
    }
    
    @Override
    public JsonElement read(final ByteBuf buffer) throws Exception {
        final String s = ComponentType.STRING_TAG.read(buffer);
        try {
            return JsonParser.parseString(s);
        }
        catch (JsonSyntaxException e) {
            Via.getPlatform().getLogger().severe("Error when trying to parse json: " + s);
            throw e;
        }
    }
    
    @Override
    public void write(final ByteBuf buffer, final JsonElement object) throws Exception {
        ComponentType.STRING_TAG.write(buffer, object.toString());
    }
    
    static {
        STRING_TAG = new StringType(262144);
    }
}
