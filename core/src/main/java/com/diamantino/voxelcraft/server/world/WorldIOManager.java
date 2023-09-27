package com.diamantino.voxelcraft.server.world;

import com.diamantino.voxelcraft.common.utils.FileUtils;
import com.diamantino.voxelcraft.common.world.World;
import com.diamantino.voxelcraft.common.world.chunk.*;
import org.apache.commons.lang3.ArrayUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class WorldIOManager {
    public static void saveWorld(World world) {
        world.chunkMap.forEach((chunkPos, chunk) -> {
            saveChunk(world.settings.name(), chunkPos, chunk);
        });
    }

    public static void saveChunk(String worldName, ChunkPos chunkPos, Chunk chunk) {
        File chunkFile = new File(FileUtils.getVoxelCraftFolder() + "/Saves/" + worldName + "/Chunks/" + chunkPos.x() + "_" + chunkPos.y() + "_" + chunkPos.z() + ".vcc");
        try (FileOutputStream outputStream = new FileOutputStream(chunkFile)) {
            List<Byte> bytes = new ArrayList<>();

            for (byte y = 0; y < Chunk.sizeY; y++) {
                IChunkLayer layer = chunk.getLayer(y);

                if (layer instanceof SingleBlockChunkLayer sbcl) {
                    ByteArrayOutputStream byteStream = new ByteArrayOutputStream(3);

                    try (DataOutputStream stream = new DataOutputStream(byteStream)) {
                        stream.writeBoolean(true);
                        stream.writeShort(sbcl.getBlock().id);
                    } catch (IOException e) {
                        // TODO: Better logs
                        e.printStackTrace();
                    }

                    bytes.addAll(Arrays.asList(ArrayUtils.toObject(byteStream.toByteArray())));
                } else if (layer instanceof ChunkLayer cl) {
                    ByteArrayOutputStream byteStream = new ByteArrayOutputStream((cl.blocksPerLayer * 2) + 1);

                    try (DataOutputStream stream = new DataOutputStream(byteStream)) {
                        stream.writeBoolean(false);

                        for (byte x = 0; x < Chunk.sizeX; x++) {
                            for (byte z = 0; z < Chunk.sizeZ; z++) {
                                stream.writeShort(cl.getBlockId(x, z));
                            }
                        }
                    } catch (IOException e) {
                        // TODO: Better logs
                        e.printStackTrace();
                    }

                    bytes.addAll(Arrays.asList(ArrayUtils.toObject(byteStream.toByteArray())));
                }
            }
        } catch (IOException e) {
            // TODO: Better logs
            throw new RuntimeException(e);
        }
    }
}
