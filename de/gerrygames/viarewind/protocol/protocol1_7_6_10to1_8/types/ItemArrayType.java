package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types;

import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import io.netty.buffer.*;

public class ItemArrayType extends Type<Item[]>
{
    private final boolean compressed;
    
    public ItemArrayType(final boolean compressed) {
        super(Item[].class);
        this.compressed = compressed;
    }
    
    @Override
    public Item[] read(final ByteBuf buffer) throws Exception {
        final int amount = Type.SHORT.read(buffer);
        final Item[] items = new Item[amount];
        for (int i = 0; i < amount; ++i) {
            items[i] = (this.compressed ? Types1_7_6_10.COMPRESSED_NBT_ITEM : Types1_7_6_10.ITEM).read(buffer);
        }
        return items;
    }
    
    @Override
    public void write(final ByteBuf buffer, final Item[] items) throws Exception {
        Type.SHORT.write(buffer, (short)items.length);
        for (final Item item : items) {
            (this.compressed ? Types1_7_6_10.COMPRESSED_NBT_ITEM : Types1_7_6_10.ITEM).write(buffer, item);
        }
    }
}
