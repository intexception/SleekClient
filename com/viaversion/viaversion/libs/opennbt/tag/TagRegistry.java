package com.viaversion.viaversion.libs.opennbt.tag;

import java.util.function.*;
import org.jetbrains.annotations.*;
import com.viaversion.viaversion.libs.fastutil.ints.*;
import com.viaversion.viaversion.libs.fastutil.objects.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;

public class TagRegistry
{
    private static final Int2ObjectMap<Class<? extends Tag>> idToTag;
    private static final Object2IntMap<Class<? extends Tag>> tagToId;
    private static final Int2ObjectMap<Supplier<? extends Tag>> instanceSuppliers;
    
    public static void register(final int id, final Class<? extends Tag> tag, final Supplier<? extends Tag> supplier) throws TagRegisterException {
        if (TagRegistry.idToTag.containsKey(id)) {
            throw new TagRegisterException("Tag ID \"" + id + "\" is already in use.");
        }
        if (TagRegistry.tagToId.containsKey(tag)) {
            throw new TagRegisterException("Tag \"" + tag.getSimpleName() + "\" is already registered.");
        }
        TagRegistry.instanceSuppliers.put(id, supplier);
        TagRegistry.idToTag.put(id, tag);
        TagRegistry.tagToId.put(tag, id);
    }
    
    public static void unregister(final int id) {
        TagRegistry.tagToId.removeInt(getClassFor(id));
        TagRegistry.idToTag.remove(id);
    }
    
    @Nullable
    public static Class<? extends Tag> getClassFor(final int id) {
        return TagRegistry.idToTag.get(id);
    }
    
    public static int getIdFor(final Class<? extends Tag> clazz) {
        return TagRegistry.tagToId.getInt(clazz);
    }
    
    public static Tag createInstance(final int id) throws TagCreateException {
        final Supplier<? extends Tag> supplier = TagRegistry.instanceSuppliers.get(id);
        if (supplier == null) {
            throw new TagCreateException("Could not find tag with ID \"" + id + "\".");
        }
        return (Tag)supplier.get();
    }
    
    static {
        idToTag = new Int2ObjectOpenHashMap<Class<? extends Tag>>();
        tagToId = new Object2IntOpenHashMap<Class<? extends Tag>>();
        instanceSuppliers = new Int2ObjectOpenHashMap<Supplier<? extends Tag>>();
        TagRegistry.tagToId.defaultReturnValue(-1);
        register(1, ByteTag.class, ByteTag::new);
        register(2, ShortTag.class, ShortTag::new);
        register(3, IntTag.class, IntTag::new);
        register(4, LongTag.class, LongTag::new);
        register(5, FloatTag.class, FloatTag::new);
        register(6, DoubleTag.class, DoubleTag::new);
        register(7, ByteArrayTag.class, ByteArrayTag::new);
        register(8, StringTag.class, StringTag::new);
        register(9, ListTag.class, ListTag::new);
        register(10, CompoundTag.class, CompoundTag::new);
        register(11, IntArrayTag.class, IntArrayTag::new);
        register(12, LongArrayTag.class, LongArrayTag::new);
    }
}
