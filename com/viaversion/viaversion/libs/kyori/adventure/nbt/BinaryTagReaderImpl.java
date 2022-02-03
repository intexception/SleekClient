package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import org.jetbrains.annotations.*;
import java.nio.file.*;
import java.io.*;
import java.util.*;

final class BinaryTagReaderImpl implements BinaryTagIO.Reader
{
    private final long maxBytes;
    static final BinaryTagIO.Reader UNLIMITED;
    static final BinaryTagIO.Reader DEFAULT_LIMIT;
    
    BinaryTagReaderImpl(final long maxBytes) {
        this.maxBytes = maxBytes;
    }
    
    @NotNull
    @Override
    public CompoundBinaryTag read(@NotNull final Path path, final BinaryTagIO.Compression compression) throws IOException {
        final InputStream is = Files.newInputStream(path, new OpenOption[0]);
        try {
            final CompoundBinaryTag read = this.read(is, compression);
            if (is != null) {
                is.close();
            }
            return read;
        }
        catch (Throwable t) {
            if (is != null) {
                try {
                    is.close();
                }
                catch (Throwable t2) {
                    t.addSuppressed(t2);
                }
            }
            throw t;
        }
    }
    
    @NotNull
    @Override
    public CompoundBinaryTag read(@NotNull final InputStream input, final BinaryTagIO.Compression compression) throws IOException {
        final DataInputStream dis = new DataInputStream(new BufferedInputStream(compression.decompress(IOStreamUtil.closeShield(input))));
        try {
            final CompoundBinaryTag read = this.read((DataInput)dis);
            dis.close();
            return read;
        }
        catch (Throwable t) {
            try {
                dis.close();
            }
            catch (Throwable t2) {
                t.addSuppressed(t2);
            }
            throw t;
        }
    }
    
    @NotNull
    @Override
    public CompoundBinaryTag read(@NotNull DataInput input) throws IOException {
        if (!(input instanceof TrackingDataInput)) {
            input = new TrackingDataInput(input, this.maxBytes);
        }
        final BinaryTagType<? extends BinaryTag> type = BinaryTagType.of(input.readByte());
        requireCompound(type);
        input.skipBytes(input.readUnsignedShort());
        return BinaryTagTypes.COMPOUND.read(input);
    }
    
    @Override
    public Map.Entry<String, CompoundBinaryTag> readNamed(@NotNull final Path path, final BinaryTagIO.Compression compression) throws IOException {
        final InputStream is = Files.newInputStream(path, new OpenOption[0]);
        try {
            final Map.Entry<String, CompoundBinaryTag> named = this.readNamed(is, compression);
            if (is != null) {
                is.close();
            }
            return named;
        }
        catch (Throwable t) {
            if (is != null) {
                try {
                    is.close();
                }
                catch (Throwable t2) {
                    t.addSuppressed(t2);
                }
            }
            throw t;
        }
    }
    
    @Override
    public Map.Entry<String, CompoundBinaryTag> readNamed(@NotNull final InputStream input, final BinaryTagIO.Compression compression) throws IOException {
        final DataInputStream dis = new DataInputStream(new BufferedInputStream(compression.decompress(IOStreamUtil.closeShield(input))));
        try {
            final Map.Entry<String, CompoundBinaryTag> named = this.readNamed((DataInput)dis);
            dis.close();
            return named;
        }
        catch (Throwable t) {
            try {
                dis.close();
            }
            catch (Throwable t2) {
                t.addSuppressed(t2);
            }
            throw t;
        }
    }
    
    @Override
    public Map.Entry<String, CompoundBinaryTag> readNamed(@NotNull final DataInput input) throws IOException {
        final BinaryTagType<? extends BinaryTag> type = BinaryTagType.of(input.readByte());
        requireCompound(type);
        final String name = input.readUTF();
        return new AbstractMap.SimpleImmutableEntry<String, CompoundBinaryTag>(name, BinaryTagTypes.COMPOUND.read(input));
    }
    
    private static void requireCompound(final BinaryTagType<? extends BinaryTag> type) throws IOException {
        if (type != BinaryTagTypes.COMPOUND) {
            throw new IOException(String.format("Expected root tag to be a %s, was %s", BinaryTagTypes.COMPOUND, type));
        }
    }
    
    static {
        UNLIMITED = new BinaryTagReaderImpl(-1L);
        DEFAULT_LIMIT = new BinaryTagReaderImpl(131082L);
    }
}
