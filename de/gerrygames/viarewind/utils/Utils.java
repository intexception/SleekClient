package de.gerrygames.viarewind.utils;

import com.viaversion.viaversion.api.connection.*;
import java.util.*;

public class Utils
{
    public static UUID getUUID(final UserConnection user) {
        return user.getProtocolInfo().getUuid();
    }
}
