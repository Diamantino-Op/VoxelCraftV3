package com.diamantino.voxelcraft.client.resources.loaders;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import org.json.JSONObject;

public class JSONObjectLoader extends AsynchronousAssetLoader<JSONObject, JSONObjectLoader.JSONObjectParameters> {
    private JSONObject object;

    public JSONObjectLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    /**
     * Loads the non-OpenGL part of the asset and injects any dependencies of the asset into the AssetManager.
     *
     * @param manager   The AssetManager.
     * @param fileName  The name of the asset to load.
     * @param file      The resolved file to load.
     * @param parameter The parameters to use for loading the asset.
     */
    @Override
    public void loadAsync(AssetManager manager, String fileName, FileHandle file, JSONObjectParameters parameter) {
        object = new JSONObject(file.readString());
    }

    /**
     * Loads the OpenGL part of the asset.
     *
     * @param manager The AssetManager.
     * @param fileName The name of the asset to load.
     * @param file      The resolved file to load.
     * @param parameter The parameters to use for loading the asset.
     */
    @Override
    public JSONObject loadSync(AssetManager manager, String fileName, FileHandle file, JSONObjectParameters parameter) {
        JSONObject obj = this.object;
        this.object = null;
        return obj;
    }

    /**
     * Returns the assets this asset requires to be loaded first. This method may be called on a thread other than the GL thread.
     *
     * @param fileName  name of the asset to load
     * @param file      the resolved file to load
     * @param parameter parameters for loading the asset
     * @return other assets that the asset depends on and need to be loaded first or null if there are no dependencies.
     */
    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, JSONObjectParameters parameter) {
        return null;
    }

    public static class JSONObjectParameters extends AssetLoaderParameters<JSONObject> {}
}
