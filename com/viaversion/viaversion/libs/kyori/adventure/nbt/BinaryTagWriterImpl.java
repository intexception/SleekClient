package com.viaversion.viaversion.libs.kyori.adventure.nbt;

import org.jetbrains.annotations.*;
import java.nio.file.*;
import java.io.*;
import java.util.*;

final class BinaryTagWriterImpl implements BinaryTagIO.Writer
{
    static final BinaryTagIO.Writer INSTANCE;
    
    @Override
    public void write(@NotNull final CompoundBinaryTag tag, @NotNull final Path path, final BinaryTagIO.Compression compression) throws IOException {
        final OutputStream os = Files.newOutputStream(path, new OpenOption[0]);
        try {
            this.write(tag, os, compression);
            if (os != null) {
                os.close();
            }
        }
        catch (Throwable t) {
            if (os != null) {
                try {
                    os.close();
                }
                catch (Throwable t2) {
                    t.addSuppressed(t2);
                }
            }
            throw t;
        }
    }
    
    @Override
    public void write(@NotNull final CompoundBinaryTag tag, @NotNull final OutputStream output, final BinaryTagIO.Compression compression) throws IOException {
        final DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(compression.compress(IOStreamUtil.closeShield(output))));
        try {
            this.write(tag, (DataOutput)dos);
            dos.close();
        }
        catch (Throwable t) {
            try {
                dos.close();
            }
            catch (Throwable t2) {
                t.addSuppressed(t2);
            }
            throw t;
        }
    }
    
    @Override
    public void write(@NotNull final CompoundBinaryTag tag, @NotNull final DataOutput output) throws IOException {
        output.writeByte(BinaryTagTypes.COMPOUND.id());
        output.writeUTF("");
        BinaryTagTypes.COMPOUND.write(tag, output);
    }
    
    @Override
    public void writeNamed(final Map.Entry<String, CompoundBinaryTag> tag, @NotNull final Path path, final BinaryTagIO.Compression compression) throws IOException {
        final OutputStream os = Files.newOutputStream(path, new OpenOption[0]);
        try {
            this.writeNamed(tag, os, compression);
            if (os != null) {
                os.close();
            }
        }
        catch (Throwable t) {
            if (os != null) {
                try {
                    os.close();
                }
                catch (Throwable t2) {
                    t.addSuppressed(t2);
                }
            }
            throw t;
        }
    }
    
    @Override
    public void writeNamed(final Map.Entry<String, CompoundBinaryTag> tag, @NotNull final OutputStream output, final BinaryTagIO.Compression compression) throws IOException {
        final DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(compression.compress(IOStreamUtil.closeShield(output))));
        try {
            this.writeNamed(tag, (DataOutput)dos);
            dos.close();
        }
        catch (Throwable t) {
            try {
                dos.close();
            }
            catch (Throwable t2) {
                t.addSuppressed(t2);
            }
            throw t;
        }
    }
    
    @Override
    public void writeNamed(final Map.Entry<String, CompoundBinaryTag> tag, @NotNull final DataOutput output) throws IOException {
        output.writeByte(BinaryTagTypes.COMPOUND.id());
        output.writeUTF(tag.getKey());
        BinaryTagTypes.COMPOUND.write(tag.getValue(), output);
    }
    
    static {
        INSTANCE = new BinaryTagWriterImpl();
    }
}
