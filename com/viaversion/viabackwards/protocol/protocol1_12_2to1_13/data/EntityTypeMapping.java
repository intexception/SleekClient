package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data;

import com.viaversion.viaversion.libs.fastutil.ints.*;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.*;
import java.lang.reflect.*;
import com.viaversion.viaversion.libs.fastutil.objects.*;

public class EntityTypeMapping
{
    private static final Int2IntMap TYPES;
    
    public static int getOldId(final int type1_13) {
        return EntityTypeMapping.TYPES.get(type1_13);
    }
    
    static {
        (TYPES = new Int2IntOpenHashMap()).defaultReturnValue(-1);
        try {
            final Field field = EntityTypeRewriter.class.getDeclaredField("ENTITY_TYPES");
            field.setAccessible(true);
            final Int2IntMap entityTypes = (Int2IntMap)field.get(null);
            for (final Int2IntMap.Entry entry : entityTypes.int2IntEntrySet()) {
                EntityTypeMapping.TYPES.put(entry.getIntValue(), entry.getIntKey());
            }
        }
        catch (NoSuchFieldException | IllegalAccessException ex3) {
            final ReflectiveOperationException ex2;
            final ReflectiveOperationException ex = ex2;
            ex.printStackTrace();
        }
    }
}
