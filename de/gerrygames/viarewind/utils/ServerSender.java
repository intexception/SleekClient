package de.gerrygames.viarewind.utils;

import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.protocol.*;

public interface ServerSender
{
    void sendToServer(final PacketWrapper p0, final Class<? extends Protocol> p1, final boolean p2, final boolean p3) throws Exception;
}
