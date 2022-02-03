package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import java.util.function.*;
import java.io.*;
import org.jetbrains.annotations.*;
import java.util.*;

public abstract class BinaryTagType<T extends BinaryTag> implements Predicate<BinaryTagType<? extends BinaryTag>>
{
    private static final List<BinaryTagType<? extends BinaryTag>> TYPES;
    
    public abstract byte id();
    
    abstract boolean numeric();
    
    @NotNull
    public abstract T read(@NotNull final DataInput input) throws IOException;
    
    public abstract void write(@NotNull final T tag, @NotNull final DataOutput output) throws IOException;
    
    static <T extends BinaryTag> void write(final BinaryTagType<? extends BinaryTag> type, final T tag, final DataOutput output) throws IOException {
        type.write((BinaryTag)tag, output);
    }
    
    @NotNull
    static BinaryTagType<? extends BinaryTag> of(final byte id) {
        for (int i = 0; i < BinaryTagType.TYPES.size(); ++i) {
            final BinaryTagType<? extends BinaryTag> type = BinaryTagType.TYPES.get(i);
            if (type.id() == id) {
                return type;
            }
        }
        throw new IllegalArgumentException(String.valueOf(id));
    }
    
    @NotNull
    static <T extends BinaryTag> BinaryTagType<T> register(final Class<T> type, final byte id, final Reader<T> reader, @Nullable final Writer<T> writer) {
        return register(new Impl<T>(type, id, reader, writer));
    }
    
    @NotNull
    static <T extends NumberBinaryTag> BinaryTagType<T> registerNumeric(final Class<T> type, final byte id, final Reader<T> reader, final Writer<T> writer) {
        return register(new Impl.Numeric<T>(type, id, reader, writer));
    }
    
    private static <T extends BinaryTag, Y extends BinaryTagType<T>> Y register(final Y type) {
        BinaryTagType.TYPES.add(type);
        return type;
    }
    
    @Override
    public boolean test(final BinaryTagType<? extends BinaryTag> that) {
        return this == that || (this.numeric() && that.numeric());
    }
    
    static {
        TYPES = new ArrayList<BinaryTagType<? extends BinaryTag>>();
    }
    
    static class Impl<T extends BinaryTag> extends BinaryTagType<T>
    {
        final Class<T> type;
        final byte id;
        private final Reader<T> reader;
        @Nullable
        private final Writer<T> writer;
        
        Impl(final Class<T> type, final byte id, final Reader<T> reader, @Nullable final Writer<T> writer) {
            this.type = type;
            this.id = id;
            this.reader = reader;
            this.writer = writer;
        }
        
        @NotNull
        @Override
        public final T read(@NotNull final DataInput input) throws IOException {
            return this.reader.read(input);
        }
        
        @Override
        public final void write(@NotNull final T tag, @NotNull final DataOutput output) throws IOException {
            if (this.writer != null) {
                this.writer.write(tag, output);
            }
        }
        
        @Override
        public final byte id() {
            return this.id;
        }
        
        @Override
        boolean numeric() {
            return false;
        }
        
        @Override
        public String toString() {
            return BinaryTagType.class.getSimpleName() + '[' + this.type.getSimpleName() + " " + this.id + "]";
        }
        
        static class Numeric<T extends BinaryTag> extends Impl<T>
        {
            Numeric(final Class<T> type, final byte id, final Reader<T> reader, @Nullable final Writer<T> writer) {
                super(type, id, reader, writer);
            }
            
            @Override
            boolean numeric() {
                return true;
            }
            
            @Override
            public String toString() {
                return BinaryTagType.class.getSimpleName() + '[' + this.type.getSimpleName() + " " + this.id + " (numeric)]";
            }
        }
    }
    
    interface Reader<T extends BinaryTag>
    {
        @NotNull
        T read(@NotNull final DataInput input) throws IOException;
    }
    
    interface Writer<T extends BinaryTag>
    {
        void write(@NotNull final T tag, @NotNull final DataOutput output) throws IOException;
    }
}
