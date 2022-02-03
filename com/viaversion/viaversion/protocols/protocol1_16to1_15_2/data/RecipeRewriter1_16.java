package com.viaversion.viaversion.protocols.protocol1_16to1_15_2.data;

import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.data.*;
import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.minecraft.item.*;
import com.viaversion.viaversion.api.type.*;

public class RecipeRewriter1_16 extends RecipeRewriter1_14
{
    public RecipeRewriter1_16(final Protocol protocol) {
        super(protocol);
        this.recipeHandlers.put("smithing", this::handleSmithing);
    }
    
    public void handleSmithing(final PacketWrapper wrapper) throws Exception {
        final Item[] array;
        final Item[] baseIngredients = array = wrapper.passthrough(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
        for (final Item item : array) {
            this.rewrite(item);
        }
        final Item[] array2;
        final Item[] ingredients = array2 = wrapper.passthrough(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
        for (final Item item2 : array2) {
            this.rewrite(item2);
        }
        this.rewrite(wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
    }
}
