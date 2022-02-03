package com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.data;

import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.packet.*;

public class CommandRewriter1_14 extends CommandRewriter
{
    public CommandRewriter1_14(final Protocol protocol) {
        super(protocol);
        this.parserHandlers.put("minecraft:nbt_tag", wrapper -> wrapper.write(Type.VAR_INT, 2));
        this.parserHandlers.put("minecraft:time", wrapper -> {
            wrapper.write(Type.BYTE, (Byte)1);
            wrapper.write(Type.INT, 0);
        });
    }
    
    @Override
    protected String handleArgumentType(final String argumentType) {
        switch (argumentType) {
            case "minecraft:nbt_compound_tag": {
                return "minecraft:nbt";
            }
            case "minecraft:nbt_tag": {
                return "brigadier:string";
            }
            case "minecraft:time": {
                return "brigadier:integer";
            }
            default: {
                return super.handleArgumentType(argumentType);
            }
        }
    }
}
