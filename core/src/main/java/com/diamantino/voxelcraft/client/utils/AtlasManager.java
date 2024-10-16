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
import com.diamantino.voxelcraft.common.vdo.ArrayVDO;
import com.diamantino.voxelcraft.common.vdo.CompoundVDO;

import java.util.List;

/**
 * Game atlas manager class.
 *
 * @author Diamantino
 */
public class AtlasManager {
    /**
     * Block texture packer.
     */
    private static PixmapPacker blockPacker;
    /**
     * The loaded block atlas.
     */
    public static TextureAtlas blockAtlas;
    /**
     * The loaded block atlas' texture.
     */
    public static Texture blockAtlasTexture;

    /**
     * Initialize the atlas manager.
     */
    public void init() {
        CompoundVDO atlasesVDO = new CompoundVDO();

        atlasesVDO.parseVDO(Gdx.files.internal("assets/voxelcraft/atlases.json").readString());

        ArrayVDO atlases = atlasesVDO.getArrayVDO("atlases");

        for (int i = 0; i < atlases.getContent().length(); i++) {
            CompoundVDO atlas = atlases.getCompoundVDO(i);

            String name = atlas.getStringVDO("name");

            if (name.equals("blocks")) {
                initBlocks();
            }
        }


    }

    /**
     * Load and pack the block textures.
     */
    private void initBlocks() {
        List<FileHandle> blockTextures = FileUtils.getAllFilesInFolderInternal(Gdx.files.internal("assets/voxelcraft/textures/blocks"), "png");

        // TODO: check size
        int size = getAtlasSize(16, blockTextures.size());

        blockPacker = new PixmapPacker(size, size, Pixmap.Format.RGBA8888, 0, false);

        packBlockAtlas(blockTextures);
    }

    /**
     * Dispose the atlas manager.
     */
    public void dispose() {
        blockPacker.dispose();
        blockAtlas.dispose();
    }

    /**
     * Packs the block textures into a single atlas and saves it.
     * @param blockTextures The texture file list.
     */
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

    /**
     * Get the size of the width and height of the atlas in pixels.
     * @return Size in pixels.
     */
    private int getAtlasSize(int textureSize, int files) {
        int sideTex = MathUtils.getNearestPO2((int) Math.round(Math.sqrt(files)));

        return sideTex * textureSize;
    }

    /**
     * Get the texture index in the atlas.
     * @param name Name of the texture.
     * @return Index of the texture in the atlas.
     */
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
