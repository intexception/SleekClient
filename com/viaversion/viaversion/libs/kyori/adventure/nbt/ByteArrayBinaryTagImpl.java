package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import java.util.stream.*;
import com.viaversion.viaversion.libs.kyori.examination.*;
import org.jetbrains.annotations.*;
import java.util.*;

@Debug.Renderer(text = "\"byte[\" + this.value.length + \"]\"", childrenArray = "this.value", hasChildren = "this.value.length > 0")
final class ByteArrayBinaryTagImpl extends ArrayBinaryTagImpl implements ByteArrayBinaryTag
{
    final byte[] value;
    
    ByteArrayBinaryTagImpl(final byte[] value) {
        this.value = Arrays.copyOf(value, value.length);
    }
    
    @Override
    public byte[] value() {
        return Arrays.copyOf(this.value, this.value.length);
    }
    
    @Override
    public int size() {
        return this.value.length;
    }
    
    @Override
    public byte get(final int index) {
        ArrayBinaryTagImpl.checkIndex(index, this.value.length);
        return this.value[index];
    }
    
    static byte[] value(final ByteArrayBinaryTag tag) {
        return (tag instanceof ByteArrayBinaryTagImpl) ? ((ByteArrayBinaryTagImpl)tag).value : tag.value();
    }
    
    @Override
    public boolean equals(@Nullable final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || this.getClass() != other.getClass()) {
            return false;
        }
        final ByteArrayBinaryTagImpl that = (ByteArrayBinaryTagImpl)other;
        return Arrays.equals(this.value, that.value);
    }
    
    @Override
    public int hashCode() {
        return Arrays.hashCode(this.value);
    }
    
    @NotNull
    @Override
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(ExaminableProperty.of("value", this.value));
    }
    
    @Override
    public Iterator<Byte> iterator() {
        return new Iterator<Byte>() {
            private int index;
            
            @Override
            public boolean hasNext() {
                return this.index < ByteArrayBinaryTagImpl.this.value.length - 1;
            }
            
            @Override
            public Byte next() {
                return ByteArrayBinaryTagImpl.this.value[this.index++];
            }
        };
    }
}
