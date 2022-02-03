package com.viaversion.viaversion.api.type.types.minecraft;

import io.netty.buffer.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import com.viaversion.viaversion.api.type.*;

public class FlatItemType extends BaseItemType
{
    public FlatItemType() {
        super("FlatItem");
    }
    
    @Override
    public Item read(final ByteBuf buffer) throws Exception {
        final short id = buffer.readShort();
        if (id < 0) {
            return null;
        }
        final Item item = new DataItem();
        item.setIdentifier(id);
        item.setAmount(buffer.readByte());
        item.setTag(Type.NBT.read(buffer));
        return item;
    }
    
    @Override
    public void write(final ByteBuf buffer, final Item object) throws Exception {
        if (object == null) {
            buffer.writeShort(-1);
        }
        else {
            buffer.writeShort(object.identifier());
            buffer.writeByte(object.amount());
            Type.NBT.write(buffer, object.tag());
        }
    }
}
