package com.diamantino.voxelcraft.server.world;

import com.diamantino.voxelcraft.common.blocks.Blocks;
import com.diamantino.voxelcraft.common.utils.FileUtils;
import com.diamantino.voxelcraft.common.world.World;
import com.diamantino.voxelcraft.common.world.chunk.*;

import java.io.*;

public class WorldIOManager {
    public static void saveWorld(World world) {
        world.chunkMap.forEach((chunkPos, chunk) -> {
            saveChunk(world.settings.name(), chunkPos, chunk);
        });
    }

    public static void saveChunk(String worldName, ChunkPos chunkPos, Chunk chunk) {
        File chunkFile = new File(FileUtils.getVoxelCraftFolder() + "/Saves/" + worldName + "/Chunks/" + chunkPos.x() + "_" + chunkPos.y() + "_" + chunkPos.z() + ".vcc");
        try (FileOutputStream outputStream = new FileOutputStream(chunkFile, false)) {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();

            for (byte y = 0; y < Chunk.sizeY; y++) {
                IChunkLayer layer = chunk.getLayer(y);

                if (layer instanceof SingleBlockChunkLayer sbcl) {
                    try (DataOutputStream stream = new DataOutputStream(byteStream)) {
                        stream.writeBoolean(true);
                        stream.writeShort(sbcl.getBlock().id);
                    } catch (IOException e) {
                        // TODO: Better logs
                        e.printStackTrace();
                    }
                } else if (layer instanceof ChunkLayer cl) {
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
                }
            }

            outputStream.write(byteStream.toByteArray());
        } catch (IOException e) {
            // TODO: Better logs
            throw new RuntimeException(e);
        }
    }

    public static boolean loadChunk(String worldName, ChunkPos chunkPos, Chunk chunk) {
        File chunkFile = new File(FileUtils.getVoxelCraftFolder() + "/Saves/" + worldName + "/Chunks/" + chunkPos.x() + "_" + chunkPos.y() + "_" + chunkPos.z() + ".vcc");

        if (!chunkFile.exists()) {
            return false;
        }

        try (FileInputStream inputStream = new FileInputStream(chunkFile)) {
            ByteArrayInputStream byteStream = new ByteArrayInputStream(inputStream.readAllBytes());

            for (byte y = 0; y < Chunk.sizeY; y++) {
                try (DataInputStream stream = new DataInputStream(byteStream)) {
                    if (stream.readBoolean()) {
                        chunk.setLayer(new SingleBlockChunkLayer(chunk, Blocks.blocks.get(stream.readShort())), y);
                    } else {
                        ChunkLayer layer = new ChunkLayer(chunk);

                        for (byte x = 0; x < Chunk.sizeX; x++) {
                            for (byte z = 0; z < Chunk.sizeZ; z++) {
                                layer.setBlock(Blocks.blocks.get(stream.readShort()), x, z) ;
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            // TODO: Better logs
            throw new RuntimeException(e);
        }

        return true;
    }
}
