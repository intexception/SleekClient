package com.viaversion.viaversion.api.type.types.version;

import com.viaversion.viaversion.api.type.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import com.viaversion.viaversion.api.minecraft.blockentity.*;

public class BlockEntityType1_18 extends Type<BlockEntity>
{
    public BlockEntityType1_18() {
        super(BlockEntity.class);
    }
    
    @Override
    public BlockEntity read(final ByteBuf buffer) throws Exception {
        final byte xz = buffer.readByte();
        final short y = buffer.readShort();
        final int typeId = Type.VAR_INT.readPrimitive(buffer);
        final CompoundTag tag = Type.NBT.read(buffer);
        return new BlockEntityImpl(xz, y, typeId, tag);
    }
    
    @Override
    public void write(final ByteBuf buffer, final BlockEntity entity) throws Exception {
        buffer.writeByte(entity.packedXZ());
        buffer.writeShort(entity.y());
        Type.VAR_INT.writePrimitive(buffer, entity.typeId());
        Type.NBT.write(buffer, entity.tag());
    }
}
