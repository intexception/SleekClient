package com.viaversion.viaversion.protocols.base;

import com.viaversion.viaversion.api.protocol.packet.*;
import java.util.*;
import com.viaversion.viaversion.api.type.*;

public class BaseProtocol1_16 extends BaseProtocol1_7
{
    @Override
    protected UUID passthroughLoginUUID(final PacketWrapper wrapper) throws Exception {
        return wrapper.passthrough(Type.UUID_INT_ARRAY);
    }
}
