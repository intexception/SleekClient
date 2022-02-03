package com.viaversion.viaversion.api.rewriter;

import com.viaversion.viaversion.api.protocol.*;
import com.viaversion.viaversion.api.minecraft.item.*;

public interface ItemRewriter<T extends Protocol> extends Rewriter<T>
{
    Item handleItemToClient(final Item p0);
    
    Item handleItemToServer(final Item p0);
}
