package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data;

import com.viaversion.viaversion.rewriter.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.api.minecraft.item.*;

public class RecipeRewriter1_13_2 extends RecipeRewriter
{
    public RecipeRewriter1_13_2(final Protocol protocol) {
        super(protocol);
        this.recipeHandlers.put("crafting_shapeless", this::handleCraftingShapeless);
        this.recipeHandlers.put("crafting_shaped", this::handleCraftingShaped);
        this.recipeHandlers.put("smelting", this::handleSmelting);
    }
    
    public void handleSmelting(final PacketWrapper wrapper) throws Exception {
        wrapper.passthrough(Type.STRING);
        final Item[] array;
        final Item[] items = array = wrapper.passthrough(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
        for (final Item item : array) {
            this.rewrite(item);
        }
        this.rewrite(wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
        wrapper.passthrough((Type<Object>)Type.FLOAT);
        wrapper.passthrough((Type<Object>)Type.VAR_INT);
    }
    
    public void handleCraftingShaped(final PacketWrapper wrapper) throws Exception {
        final int ingredientsNo = wrapper.passthrough((Type<Integer>)Type.VAR_INT) * wrapper.passthrough((Type<Integer>)Type.VAR_INT);
        wrapper.passthrough(Type.STRING);
        for (int j = 0; j < ingredientsNo; ++j) {
            final Item[] array;
            final Item[] items = array = wrapper.passthrough(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
            for (final Item item : array) {
                this.rewrite(item);
            }
        }
        this.rewrite(wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
    }
    
    public void handleCraftingShapeless(final PacketWrapper wrapper) throws Exception {
        wrapper.passthrough(Type.STRING);
        for (int ingredientsNo = wrapper.passthrough((Type<Integer>)Type.VAR_INT), j = 0; j < ingredientsNo; ++j) {
            final Item[] array;
            final Item[] items = array = wrapper.passthrough(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
            for (final Item item : array) {
                this.rewrite(item);
            }
        }
        this.rewrite(wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
    }
}
