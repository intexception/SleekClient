package com.viaversion.viaversion.protocols.protocol1_14to1_13_2.types;

import com.viaversion.viaversion.api.type.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import com.viaversion.viaversion.api.type.types.version.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.api.minecraft.chunks.*;
import java.util.*;
import com.viaversion.viaversion.api.type.types.minecraft.*;

public class Chunk1_14Type extends Type<Chunk>
{
    public Chunk1_14Type() {
        super(Chunk.class);
    }
    
    @Override
    public Chunk read(final ByteBuf input) throws Exception {
        final int chunkX = input.readInt();
        final int chunkZ = input.readInt();
        final boolean fullChunk = input.readBoolean();
        final int primaryBitmask = Type.VAR_INT.readPrimitive(input);
        final CompoundTag heightMap = Type.NBT.read(input);
        Type.VAR_INT.readPrimitive(input);
        final ChunkSection[] sections = new ChunkSection[16];
        for (int i = 0; i < 16; ++i) {
            if ((primaryBitmask & 1 << i) != 0x0) {
                final short nonAirBlocksCount = input.readShort();
                final ChunkSection section = Types1_13.CHUNK_SECTION.read(input);
                section.setNonAirBlocksCount(nonAirBlocksCount);
                sections[i] = section;
            }
        }
        final int[] biomeData = (int[])(fullChunk ? new int[256] : null);
        if (fullChunk) {
            for (int j = 0; j < 256; ++j) {
                biomeData[j] = input.readInt();
            }
        }
        final List<CompoundTag> nbtData = new ArrayList<CompoundTag>(Arrays.asList((CompoundTag[])Type.NBT_ARRAY.read(input)));
        if (input.readableBytes() > 0) {
            final byte[] array = Type.REMAINING_BYTES.read(input);
            if (Via.getManager().isDebug()) {
                Via.getPlatform().getLogger().warning("Found " + array.length + " more bytes than expected while reading the chunk: " + chunkX + "/" + chunkZ);
            }
        }
        return new BaseChunk(chunkX, chunkZ, fullChunk, false, primaryBitmask, sections, biomeData, heightMap, nbtData);
    }
    
    @Override
    public void write(final ByteBuf output, final Chunk chunk) throws Exception {
        output.writeInt(chunk.getX());
        output.writeInt(chunk.getZ());
        output.writeBoolean(chunk.isFullChunk());
        Type.VAR_INT.writePrimitive(output, chunk.getBitmask());
        Type.NBT.write(output, chunk.getHeightMap());
        final ByteBuf buf = output.alloc().buffer();
        try {
            for (int i = 0; i < 16; ++i) {
                final ChunkSection section = chunk.getSections()[i];
                if (section != null) {
                    buf.writeShort(section.getNonAirBlocksCount());
                    Types1_13.CHUNK_SECTION.write(buf, section);
                }
            }
            buf.readerIndex(0);
            Type.VAR_INT.writePrimitive(output, buf.readableBytes() + (chunk.isBiomeData() ? 1024 : 0));
            output.writeBytes(buf);
        }
        finally {
            buf.release();
        }
        if (chunk.isBiomeData()) {
            for (final int value : chunk.getBiomeData()) {
                output.writeInt(value & 0xFF);
            }
        }
        Type.NBT_ARRAY.write(output, chunk.getBlockEntities().toArray(new CompoundTag[0]));
    }
    
    @Override
    public Class<? extends Type> getBaseClass() {
        return BaseChunkType.class;
    }
}
