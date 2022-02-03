package com.viaversion.viaversion.velocity.handlers;

import java.lang.reflect.*;
import com.viaversion.viaversion.connection.*;
import com.viaversion.viaversion.protocol.*;
import io.netty.channel.*;
import com.viaversion.viaversion.api.connection.*;

public class VelocityChannelInitializer extends ChannelInitializer<Channel>
{
    public static final String MINECRAFT_ENCODER = "minecraft-encoder";
    public static final String MINECRAFT_DECODER = "minecraft-decoder";
    public static final String VIA_ENCODER = "via-encoder";
    public static final String VIA_DECODER = "via-decoder";
    public static final Object COMPRESSION_ENABLED_EVENT;
    private static final Method INIT_CHANNEL;
    private final ChannelInitializer<?> original;
    private final boolean clientSide;
    
    public VelocityChannelInitializer(final ChannelInitializer<?> original, final boolean clientSide) {
        this.original = original;
        this.clientSide = clientSide;
    }
    
    @Override
    protected void initChannel(final Channel channel) throws Exception {
        VelocityChannelInitializer.INIT_CHANNEL.invoke(this.original, channel);
        final UserConnection user = new UserConnectionImpl(channel, this.clientSide);
        new ProtocolPipelineImpl(user);
        channel.pipeline().addBefore("minecraft-encoder", "via-encoder", new VelocityEncodeHandler(user));
        channel.pipeline().addBefore("minecraft-decoder", "via-decoder", new VelocityDecodeHandler(user));
    }
    
    static {
        try {
            (INIT_CHANNEL = ChannelInitializer.class.getDeclaredMethod("initChannel", Channel.class)).setAccessible(true);
            final Class<?> eventClass = Class.forName("com.velocitypowered.proxy.protocol.VelocityConnectionEvent");
            COMPRESSION_ENABLED_EVENT = eventClass.getDeclaredField("COMPRESSION_ENABLED").get(null);
        }
        catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
}
