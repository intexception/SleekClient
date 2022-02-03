package com.viaversion.viaversion.protocols.protocol1_9to1_8.providers;

import com.viaversion.viaversion.api.platform.providers.*;
import com.viaversion.viaversion.api.connection.*;
import com.viaversion.viaversion.util.*;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.*;
import io.netty.channel.*;

public abstract class MovementTransmitterProvider implements Provider
{
    public abstract Object getFlyingPacket();
    
    public abstract Object getGroundPacket();
    
    public void sendPlayer(final UserConnection userConnection) {
        final ChannelHandlerContext context = PipelineUtil.getContextBefore("decoder", userConnection.getChannel().pipeline());
        if (context != null) {
            if (userConnection.get(MovementTracker.class).isGround()) {
                context.fireChannelRead(this.getGroundPacket());
            }
            else {
                context.fireChannelRead(this.getFlyingPacket());
            }
            userConnection.get(MovementTracker.class).incrementIdlePacket();
        }
    }
}
