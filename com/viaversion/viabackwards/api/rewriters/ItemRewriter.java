package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viabackwards.api.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import java.util.*;
import com.viaversion.viabackwards.api.data.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.protocol.remapper.*;
import com.viaversion.viaversion.libs.gson.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.protocol.*;

public abstract class ItemRewriter<T extends BackwardsProtocol> extends ItemRewriterBase<T>
{
    protected ItemRewriter(final T protocol) {
        super(protocol, true);
    }
    
    @Override
    public Item handleItemToClient(final Item item) {
        if (item == null) {
            return null;
        }
        CompoundTag display = (item.tag() != null) ? item.tag().get("display") : null;
        if (this.protocol.getTranslatableRewriter() != null && display != null) {
            final StringTag name = display.get("Name");
            if (name != null) {
                final String newValue = this.protocol.getTranslatableRewriter().processText(name.getValue()).toString();
                if (!newValue.equals(name.getValue())) {
                    this.saveStringTag(display, name, "Name");
                }
                name.setValue(newValue);
            }
            final ListTag lore = display.get("Lore");
            if (lore != null) {
                boolean changed = false;
                for (final Tag loreEntryTag : lore) {
                    if (!(loreEntryTag instanceof StringTag)) {
                        continue;
                    }
                    final StringTag loreEntry = (StringTag)loreEntryTag;
                    final String newValue2 = this.protocol.getTranslatableRewriter().processText(loreEntry.getValue()).toString();
                    if (!changed && !newValue2.equals(loreEntry.getValue())) {
                        changed = true;
                        this.saveListTag(display, lore, "Lore");
                    }
                    loreEntry.setValue(newValue2);
                }
            }
        }
        final MappedItem data = this.protocol.getMappingData().getMappedItem(item.identifier());
        if (data == null) {
            return super.handleItemToClient(item);
        }
        if (item.tag() == null) {
            item.setTag(new CompoundTag());
        }
        item.tag().put(this.nbtTagName + "|id", new IntTag(item.identifier()));
        item.setIdentifier(data.getId());
        if (display == null) {
            item.tag().put("display", display = new CompoundTag());
        }
        if (!display.contains("Name")) {
            display.put("Name", new StringTag(data.getJsonName()));
            display.put(this.nbtTagName + "|customName", new ByteTag());
        }
        return item;
    }
    
    @Override
    public Item handleItemToServer(final Item item) {
        if (item == null) {
            return null;
        }
        super.handleItemToServer(item);
        if (item.tag() != null) {
            final IntTag originalId = item.tag().remove(this.nbtTagName + "|id");
            if (originalId != null) {
                item.setIdentifier(originalId.asInt());
            }
        }
        return item;
    }
    
    @Override
    public void registerAdvancements(final ClientboundPacketType packetType, final Type<Item> type) {
        this.protocol.registerClientbound(packetType, new PacketRemapper() {
            @Override
            public void registerMap() {
                final Type<Item> val$type;
                int size;
                int i;
                JsonElement title;
                JsonElement description;
                TranslatableRewriter translatableRewriter;
                int flags;
                int arrayLength;
                int array;
                this.handler(wrapper -> {
                    val$type = type;
                    wrapper.passthrough((Type<Object>)Type.BOOLEAN);
                    for (size = wrapper.passthrough((Type<Integer>)Type.VAR_INT), i = 0; i < size; ++i) {
                        wrapper.passthrough(Type.STRING);
                        if (wrapper.passthrough((Type<Boolean>)Type.BOOLEAN)) {
                            wrapper.passthrough(Type.STRING);
                        }
                        if (wrapper.passthrough((Type<Boolean>)Type.BOOLEAN)) {
                            title = wrapper.passthrough(Type.COMPONENT);
                            description = wrapper.passthrough(Type.COMPONENT);
                            translatableRewriter = ((BackwardsProtocol)ItemRewriter.this.protocol).getTranslatableRewriter();
                            if (translatableRewriter != null) {
                                translatableRewriter.processText(title);
                                translatableRewriter.processText(description);
                            }
                            ItemRewriter.this.handleItemToClient(wrapper.passthrough(val$type));
                            wrapper.passthrough((Type<Object>)Type.VAR_INT);
                            flags = wrapper.passthrough((Type<Integer>)Type.INT);
                            if ((flags & 0x1) != 0x0) {
                                wrapper.passthrough(Type.STRING);
                            }
                            wrapper.passthrough((Type<Object>)Type.FLOAT);
                            wrapper.passthrough((Type<Object>)Type.FLOAT);
                        }
                        wrapper.passthrough(Type.STRING_ARRAY);
                        for (arrayLength = wrapper.passthrough((Type<Integer>)Type.VAR_INT), array = 0; array < arrayLength; ++array) {
                            wrapper.passthrough(Type.STRING_ARRAY);
                        }
                    }
                });
            }
        });
    }
}
