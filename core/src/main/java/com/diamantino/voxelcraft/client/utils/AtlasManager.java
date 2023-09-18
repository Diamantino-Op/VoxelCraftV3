package com.diamantino.voxelcraft.client.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PixmapPacker;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.diamantino.voxelcraft.common.utils.FileUtils;
import com.diamantino.voxelcraft.common.utils.MathUtils;

import java.util.List;

public class AtlasManager {
    private static PixmapPacker blockPacker;
    public static TextureAtlas blockAtlas;
    public static Texture blockAtlasTexture;

    public void init() {
        initBlocks();
    }

    private void initBlocks() {
        List<FileHandle> blockTextures = FileUtils.getAllFilesInFolderInternal(Gdx.files.internal("assets/voxelcraft/textures/blocks"), "png");

        // TODO: check size
        int size = getAtlasSize(16, blockTextures.size());

        blockPacker = new PixmapPacker(size, size, Pixmap.Format.RGBA8888, 0, false);

        packBlockAtlas(blockTextures);
    }

    public void dispose() {
        blockPacker.dispose();
        blockAtlas.dispose();
    }

    public void packBlockAtlas(List<FileHandle> blockTextures) {
        for (FileHandle file : blockTextures) {
            Pixmap pixmap = new Pixmap(file);

            blockPacker.pack(file.name().replace(".png", ""), pixmap);

            pixmap.dispose();
        }

        blockAtlas = blockPacker.generateTextureAtlas(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest, true);

        PixmapIO.writePNG(Gdx.files.absolute(FileUtils.getVoxelCraftFolder() + "/cache/atlas.png"), blockAtlas.getTextures().first().getTextureData().consumePixmap());
        blockAtlasTexture = blockAtlas.getTextures().first();
    }

    private int getAtlasSize(int textureSize, int files) {
        int sideTex = MathUtils.getNearestPO2((int) Math.round(Math.sqrt(files)));

        return sideTex * textureSize;
    }

    public static int getBlockTextureIndex(String name) {
        for (int i = 0, n = blockAtlas.getRegions().size; i < n; i++) {
            if (blockAtlas.getRegions().get(i).name.equals(name)) {
                return i;
            }
        }

        // TODO: Return notfound texture
        return 0;
    }
}
