package com.diamantino.voxelcraft.client.resources;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Null;
import com.diamantino.voxelcraft.client.resources.loaders.JSONObjectLoader;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VoxelAssetManager extends AssetManager {
    private final Map<String, Object> instances;

    public VoxelAssetManager() {
        super(new VoxelFileHandleResolver());

        this.setLoader(JSONObject.class, new JSONObjectLoader(this.getFileHandleResolver()));

        this.instances = new HashMap<>();
    }

    public synchronized <T> void load(String fileName, T instance) {
        instances.put(fileName, instance);
    }

    @Override
    public synchronized <T> void load(String fileName, Class<T> type) {
        ((VoxelFileHandleResolver) super.getFileHandleResolver()).setInternal(false);

        super.load(fileName, type);
    }

    public synchronized <T> void load(String fileName, Class<T> type, boolean internal) {
        ((VoxelFileHandleResolver) super.getFileHandleResolver()).setInternal(internal);

        super.load(fileName, type);
    }

    @Override
    public synchronized @Null <T> T get(String fileName, Class<T> type) {
        if (instances.containsKey(fileName)) {
            return type.cast(instances.get(fileName));
        } else {
            return super.get(fileName, type);
        }
    }

    @Override
    public void clear() {
        super.clear();

        instances.clear();
    }
}
