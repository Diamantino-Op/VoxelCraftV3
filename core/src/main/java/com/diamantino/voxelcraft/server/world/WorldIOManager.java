package com.diamantino.voxelcraft.server.world;

import com.diamantino.voxelcraft.common.utils.FileUtils;
import com.diamantino.voxelcraft.common.world.World;
import com.diamantino.voxelcraft.common.world.chunk.Chunk;
import com.diamantino.voxelcraft.common.world.chunk.ChunkPos;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class WorldIOManager {
    public static void saveWorld(World world) {
        world.chunkMap.forEach((chunkPos, chunk) -> {
            saveChunk(world.settings.name(), chunkPos, chunk);
        });
    }

    public static void saveChunk(String worldName, ChunkPos chunkPos, Chunk chunk) {
        File chunkFile = new File(FileUtils.getVoxelCraftFolder() + "/Saves/" + worldName + "/Chunks/" + chunkPos.x() + "_" + chunkPos.y() + "_" + chunkPos.z() + ".vcc");
        try (FileOutputStream outputStream = new FileOutputStream(chunkFile)) {

        } catch (IOException e) {
            // TODO: Better logs
            throw new RuntimeException(e);
        }
    }
}
