package com.viaversion.viaversion.protocols.protocol1_12to1_11_1.providers;

import com.viaversion.viaversion.api.platform.providers.*;
import com.viaversion.viaversion.api.connection.*;

public class InventoryQuickMoveProvider implements Provider
{
    public boolean registerQuickMoveAction(final short windowId, final short slotId, final short actionId, final UserConnection userConnection) {
        return false;
    }
}
