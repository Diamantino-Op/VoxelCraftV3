package com.diamantino.voxelcraft.client.resources;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AssetLoader;

public class VoxelAssetManager extends AssetManager {
    public VoxelAssetManager() {
        super();
    }

    public synchronized <T> void load(String fileName, T instance, Class<T> type) {
        AssetLoader loader = getLoader(type, fileName);
    }
}
