package com.diamantino.voxelcraft.client.resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;

public class VoxelFileHandleResolver implements FileHandleResolver {
    private boolean internal = true;

    public void setInternal(boolean internal) {
        this.internal = internal;
    }

    @Override
    public FileHandle resolve(String fileName) {
        if (internal) {
            return Gdx.files.internal(fileName);
        } else {
            return Gdx.files.absolute(fileName);
        }
    }
}
