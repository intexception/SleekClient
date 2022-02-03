package com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.data;

import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.packet.*;

public class CommandRewriter1_16_2 extends CommandRewriter
{
    public CommandRewriter1_16_2(final Protocol protocol) {
        super(protocol);
        this.parserHandlers.put("minecraft:angle", wrapper -> wrapper.write(Type.VAR_INT, 0));
    }
    
    @Override
    protected String handleArgumentType(final String argumentType) {
        if (argumentType.equals("minecraft:angle")) {
            return "brigadier:string";
        }
        return super.handleArgumentType(argumentType);
    }
}
