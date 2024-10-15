package com.diamantino.voxelcraft.server.world;

import com.badlogic.gdx.Gdx;
import com.diamantino.voxelcraft.common.Constants;
import com.diamantino.voxelcraft.common.blocks.Blocks;
import com.diamantino.voxelcraft.common.utils.FileUtils;
import com.diamantino.voxelcraft.common.world.World;
import com.diamantino.voxelcraft.common.world.chunk.*;

import java.io.*;

/**
 * Manages the input/output operations of the world data
 *
 * @author Diamantino
 */
public class WorldIOManager {
    /**
     * Saves the world data to the disk.
     * @param world The world to save.
     */
    public static void saveWorld(World world) {
        world.chunkMap.forEach((chunkPos, chunk) -> {
            saveChunkBlockData(world.settings.name(), chunkPos, chunk);

            chunk.entityMap.forEach((entityPos, entity) -> {

            });
        });
    }

    //TODO: Fix
    /*public static void loadWorld(World world) {
        File worldFolder = new File(FileUtils.getVoxelCraftFolder() + "/Saves/" + world.settings.name() + "/Chunks/");

        if (!worldFolder.exists()) {
            return;
        }

        for (File chunkFile : worldFolder.listFiles()) {
            String[] chunkPosStr = chunkFile.getName().split("_");
            ChunkPos chunkPos = new ChunkPos(Short.parseShort(chunkPosStr[0]), Short.parseShort(chunkPosStr[1]), Short.parseShort(chunkPosStr[2]));

            Chunk chunk = new Chunk(world, chunkPos);
            if (loadChunk(world.settings.name(), chunkPos, chunk)) {
                world.chunkMap.put(chunkPos, chunk);
            }
        }
    }*/

    /**
     * Saves the block data of a chunk to the disk.
     * @param worldName The name of the world.
     * @param chunkPos The position of the chunk.
     * @param chunk The chunk to save.
     */
    public static void saveChunkBlockData(String worldName, ChunkPos chunkPos, Chunk chunk) {
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
                        Gdx.app.getApplicationLogger().error(Constants.errorLogTag, "Failed to save single block chunk layer at y: " + y + " in chunk: " + chunkPos.x() + ", " + chunkPos.y() + ", " + chunkPos.z());
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
                        Gdx.app.getApplicationLogger().error(Constants.errorLogTag, "Failed to save chunk layer at y: " + y + " in chunk: " + chunkPos.x() + ", " + chunkPos.y() + ", " + chunkPos.z());
                    }
                }
            }

            outputStream.write(byteStream.toByteArray());
        } catch (IOException e) {
            Gdx.app.getApplicationLogger().error(Constants.errorLogTag, "Failed to save chunk: " + chunkPos.x() + ", " + chunkPos.y() + ", " + chunkPos.z());
        }
    }

    /**
     * Loads the block data of a chunk from the disk.
     * @param worldName The name of the world.
     * @param chunkPos The position of the chunk.
     * @param chunk The chunk to load the data into.
     * @return True if the chunk was successfully loaded, false otherwise.
     */
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
            Gdx.app.getApplicationLogger().error(Constants.errorLogTag, "Failed to load chunk: " + chunkPos.x() + ", " + chunkPos.y() + ", " + chunkPos.z());

            return false;
        }

        return true;
    }

    /**
     * Saves the entities of a chunk.
     */
    public static void saveEntities() {

    }

    /**
     * Loads the entities of a chunk.
     */
    public static void loadEntities() {

    }
}
