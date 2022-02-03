package com.viaversion.viaversion.api.type.types.version;

import com.viaversion.viaversion.api.type.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.minecraft.chunks.*;
import java.util.*;
import com.viaversion.viaversion.util.*;
import java.util.function.*;

public class ChunkSectionType1_9 extends Type<ChunkSection>
{
    private static final int GLOBAL_PALETTE = 13;
    
    public ChunkSectionType1_9() {
        super("Chunk Section Type", ChunkSection.class);
    }
    
    @Override
    public ChunkSection read(final ByteBuf buffer) throws Exception {
        final int originalBitsPerBlock;
        int bitsPerBlock = originalBitsPerBlock = buffer.readUnsignedByte();
        if (bitsPerBlock == 0) {
            bitsPerBlock = 13;
        }
        if (bitsPerBlock < 4) {
            bitsPerBlock = 4;
        }
        if (bitsPerBlock > 8) {
            bitsPerBlock = 13;
        }
        final int paletteLength = Type.VAR_INT.readPrimitive(buffer);
        final ChunkSection chunkSection = (bitsPerBlock != 13) ? new ChunkSectionImpl(true, paletteLength) : new ChunkSectionImpl(true);
        for (int i = 0; i < paletteLength; ++i) {
            if (bitsPerBlock != 13) {
                chunkSection.addPaletteEntry(Type.VAR_INT.readPrimitive(buffer));
            }
            else {
                Type.VAR_INT.readPrimitive(buffer);
            }
        }
        final long[] blockData = new long[Type.VAR_INT.readPrimitive(buffer)];
        if (blockData.length > 0) {
            final int expectedLength = (int)Math.ceil(4096 * bitsPerBlock / 64.0);
            if (blockData.length != expectedLength) {
                throw new IllegalStateException("Block data length (" + blockData.length + ") does not match expected length (" + expectedLength + ")! bitsPerBlock=" + bitsPerBlock + ", originalBitsPerBlock=" + originalBitsPerBlock);
            }
            for (int j = 0; j < blockData.length; ++j) {
                blockData[j] = buffer.readLong();
            }
            final int bitsPerEntry = bitsPerBlock;
            final int entries = 4096;
            final long[] data = blockData;
            BiIntConsumer consumer;
            if (bitsPerBlock == 13) {
                final ChunkSection chunkSection2 = chunkSection;
                Objects.requireNonNull(chunkSection2);
                consumer = chunkSection2::setFlatBlock;
            }
            else {
                final ChunkSection chunkSection3 = chunkSection;
                Objects.requireNonNull(chunkSection3);
                consumer = chunkSection3::setPaletteIndex;
            }
            CompactArrayUtil.iterateCompactArray(bitsPerEntry, entries, data, consumer);
        }
        return chunkSection;
    }
    
    @Override
    public void write(final ByteBuf buffer, final ChunkSection chunkSection) throws Exception {
        int bitsPerBlock;
        for (bitsPerBlock = 4; chunkSection.getPaletteSize() > 1 << bitsPerBlock; ++bitsPerBlock) {}
        if (bitsPerBlock > 8) {
            bitsPerBlock = 13;
        }
        final long maxEntryValue = (1L << bitsPerBlock) - 1L;
        buffer.writeByte(bitsPerBlock);
        if (bitsPerBlock != 13) {
            Type.VAR_INT.writePrimitive(buffer, chunkSection.getPaletteSize());
            for (int i = 0; i < chunkSection.getPaletteSize(); ++i) {
                Type.VAR_INT.writePrimitive(buffer, chunkSection.getPaletteEntry(i));
            }
        }
        else {
            Type.VAR_INT.writePrimitive(buffer, 0);
        }
        final int bitsPerEntry = bitsPerBlock;
        final int entries = 4096;
        IntToLongFunction valueGetter;
        if (bitsPerBlock == 13) {
            Objects.requireNonNull(chunkSection);
            valueGetter = chunkSection::getFlatBlock;
        }
        else {
            Objects.requireNonNull(chunkSection);
            valueGetter = chunkSection::getPaletteIndex;
        }
        final long[] data = CompactArrayUtil.createCompactArray(bitsPerEntry, entries, valueGetter);
        Type.VAR_INT.writePrimitive(buffer, data.length);
        for (final long l : data) {
            buffer.writeLong(l);
        }
    }
}
