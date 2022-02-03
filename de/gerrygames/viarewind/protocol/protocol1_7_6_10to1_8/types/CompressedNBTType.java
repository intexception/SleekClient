package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types;

import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import com.viaversion.viaversion.libs.opennbt.*;
import io.netty.buffer.*;
import java.util.zip.*;
import java.io.*;

public class CompressedNBTType extends Type<CompoundTag>
{
    public CompressedNBTType() {
        super(CompoundTag.class);
    }
    
    @Override
    public CompoundTag read(final ByteBuf buffer) throws IOException {
        final short length = buffer.readShort();
        if (length <= 0) {
            return null;
        }
        final ByteBuf compressed = buffer.readSlice(length);
        final GZIPInputStream gzipStream = new GZIPInputStream(new ByteBufInputStream(compressed));
        try {
            final CompoundTag tag = NBTIO.readTag(gzipStream);
            gzipStream.close();
            return tag;
        }
        catch (Throwable t) {
            try {
                gzipStream.close();
            }
            catch (Throwable t2) {
                t.addSuppressed(t2);
            }
            throw t;
        }
    }
    
    @Override
    public void write(final ByteBuf buffer, final CompoundTag nbt) throws Exception {
        if (nbt == null) {
            buffer.writeShort(-1);
            return;
        }
        final ByteBuf compressedBuf = buffer.alloc().buffer();
        try {
            final GZIPOutputStream gzipStream = new GZIPOutputStream(new ByteBufOutputStream(compressedBuf));
            try {
                NBTIO.writeTag(gzipStream, nbt);
                gzipStream.close();
            }
            catch (Throwable t) {
                try {
                    gzipStream.close();
                }
                catch (Throwable t2) {
                    t.addSuppressed(t2);
                }
                throw t;
            }
            buffer.writeShort(compressedBuf.readableBytes());
            buffer.writeBytes(compressedBuf);
        }
        finally {
            compressedBuf.release();
        }
    }
}
