package com.viaversion.viaversion.protocols.protocol1_9to1_8.providers;

import com.viaversion.viaversion.api.platform.providers.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.*;
import com.viaversion.viaversion.api.protocol.*;

public class EntityIdProvider implements Provider
{
    public int getEntityId(final UserConnection user) throws Exception {
        return user.getEntityTracker(Protocol1_9To1_8.class).clientEntityId();
    }
}
