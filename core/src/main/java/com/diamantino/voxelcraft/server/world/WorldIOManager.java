package com.diamantino.voxelcraft.server.world;

import com.badlogic.gdx.Gdx;
import com.diamantino.voxelcraft.common.Constants;
import com.diamantino.voxelcraft.common.utils.FileUtils;
import com.diamantino.voxelcraft.common.world.World;
import com.diamantino.voxelcraft.common.world.chunk.*;
import dev.ultreon.ubo.DataIo;
import dev.ultreon.ubo.types.MapType;

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

        try {
            DataIo.writeCompressed(chunk.saveChunkData(), chunkFile);
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

        try {
            chunk.loadChunkData(DataIo.readCompressed(chunkFile, new MapType()));
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
