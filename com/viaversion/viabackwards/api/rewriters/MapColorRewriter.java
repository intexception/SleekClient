package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.packet.*;

public final class MapColorRewriter
{
    public static PacketHandler getRewriteHandler(final IdRewriteFunction rewriter) {
        int iconCount;
        int i;
        final short columns;
        byte[] data;
        int j;
        int color;
        int mappedColor;
        return wrapper -> {
            for (iconCount = wrapper.passthrough((Type<Integer>)Type.VAR_INT), i = 0; i < iconCount; ++i) {
                wrapper.passthrough((Type<Object>)Type.VAR_INT);
                wrapper.passthrough((Type<Object>)Type.BYTE);
                wrapper.passthrough((Type<Object>)Type.BYTE);
                wrapper.passthrough((Type<Object>)Type.BYTE);
                if (wrapper.passthrough((Type<Boolean>)Type.BOOLEAN)) {
                    wrapper.passthrough(Type.COMPONENT);
                }
            }
            columns = wrapper.passthrough((Type<Short>)Type.UNSIGNED_BYTE);
            if (columns >= 1) {
                wrapper.passthrough((Type<Object>)Type.UNSIGNED_BYTE);
                wrapper.passthrough((Type<Object>)Type.UNSIGNED_BYTE);
                wrapper.passthrough((Type<Object>)Type.UNSIGNED_BYTE);
                for (data = wrapper.passthrough(Type.BYTE_ARRAY_PRIMITIVE), j = 0; j < data.length; ++j) {
                    color = (data[j] & 0xFF);
                    mappedColor = rewriter.rewrite(color);
                    if (mappedColor != -1) {
                        data[j] = (byte)mappedColor;
                    }
                }
            }
        };
    }
}
