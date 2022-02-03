package com.viaversion.viaversion.api.type.types.minecraft;

import com.viaversion.viaversion.api.type.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.minecraft.*;

public class BlockChangeRecordType extends Type<BlockChangeRecord>
{
    public BlockChangeRecordType() {
        super(BlockChangeRecord.class);
    }
    
    @Override
    public BlockChangeRecord read(final ByteBuf buffer) throws Exception {
        final short position = Type.SHORT.readPrimitive(buffer);
        final int blockId = Type.VAR_INT.readPrimitive(buffer);
        return new BlockChangeRecord1_8(position >> 12 & 0xF, position & 0xFF, position >> 8 & 0xF, blockId);
    }
    
    @Override
    public void write(final ByteBuf buffer, final BlockChangeRecord object) throws Exception {
        Type.SHORT.writePrimitive(buffer, (short)(object.getSectionX() << 12 | object.getSectionZ() << 8 | object.getY()));
        Type.VAR_INT.writePrimitive(buffer, object.getBlockId());
    }
}
