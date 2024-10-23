package com.diamantino.voxelcraft.client.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.PixmapPacker;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;
import com.diamantino.voxelcraft.common.utils.FileUtils;
import com.diamantino.voxelcraft.common.utils.MathUtils;
import com.diamantino.voxelcraft.common.vdo.ArrayVDO;
import com.diamantino.voxelcraft.common.vdo.CompoundVDO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Game atlas manager class.
 *
 * @author Diamantino
 */
public class TextureManager {
    public static Map<String, TextureAtlas> atlases = new HashMap<>();

    public static Map<String, Disposable> graphicalObjects = new HashMap<>();

    public static Map<String, CompoundVDO> vcmetas = new HashMap<>();

    /**
     * Initialize the atlas manager.
     */
    public void init() {
        CompoundVDO texturesVDO = new CompoundVDO();

        texturesVDO.parseVDO(Gdx.files.internal("assets/voxelcraft/textures.json").readString());

        ArrayVDO atlasesVDO = texturesVDO.getArrayVDO("atlases");

        for (int i = 0; i < atlasesVDO.getContent().length(); i++) {
            CompoundVDO atlasVDO = atlasesVDO.getCompoundVDO(i);

            initAtlas(atlasVDO.getStringVDO("name"), atlasVDO.getIntVDO("size"), atlasVDO.getStringVDO("location"));
        }

        ArrayVDO textureLocationsVDO = texturesVDO.getArrayVDO("textureLocations");

        for (int i = 0; i < textureLocationsVDO.getContent().length(); i++) {
            loadTextures(textureLocationsVDO.getStringVDO(i));
        }
    }

    public void initAtlas(String name, int size, String location) {
        int tmpSize = size;

        //TODO: Make compatible with mods
        List<FileHandle> textures = FileUtils.getAllFilesInFolderInternal(Gdx.files.internal("assets/voxelcraft/" + location), "png");
        List<FileHandle> vcmeta = FileUtils.getAllFilesInFolderInternal(Gdx.files.internal("assets/voxelcraft/" + location), "vcmeta");

        int additionalSpace = 0;

        for (FileHandle metaFile : vcmeta) {
            CompoundVDO metaVDO = new CompoundVDO();
            metaVDO.parseVDO(Gdx.files.internal(metaFile.path()).readString());

            if (metaVDO.getContent().has("frames")) {
                additionalSpace += metaVDO.getIntVDO("frames");
            }
        }

        if (size == -1) {
            tmpSize = getAtlasSize(new Texture(textures.getFirst()).getWidth(), textures.size() + additionalSpace);
        }

        PixmapPacker atlasPacker = new PixmapPacker(tmpSize, tmpSize, Pixmap.Format.RGBA8888, 0, false);

        for (FileHandle file : textures) {
            Pixmap pixmap = new Pixmap(file);

            atlasPacker.pack(file.name().replace(".png", ""), pixmap);

            pixmap.dispose();
        }

        TextureAtlas atlas = atlasPacker.generateTextureAtlas(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest, true);

        PixmapIO.writePNG(Gdx.files.absolute(FileUtils.getVoxelCraftFolder() + "/cache/" + name + ".png"), atlas.getTextures().first().getTextureData().consumePixmap());

        atlases.put(name, atlas);
        graphicalObjects.put(name + "_atlas", atlas.getTextures().first());

        atlasPacker.dispose();
    }

    public void loadTextures(String location) {
        List<FileHandle> textures = FileUtils.getAllFilesInFolderInternal(Gdx.files.internal("assets/voxelcraft/" + location), "png");
        List<FileHandle> vcmeta = FileUtils.getAllFilesInFolderInternal(Gdx.files.internal("assets/voxelcraft/" + location), "vcmeta");

        for (FileHandle texture : textures) {

        }

        for (FileHandle vcmetaFile : vcmeta) {
            String vcmetaName = vcmetaFile.nameWithoutExtension();

            if (graphicalObjects.containsKey(vcmetaName)) {
                CompoundVDO metaVDO = new CompoundVDO();
                metaVDO.parseVDO(Gdx.files.internal(vcmetaFile.path()).readString());

                vcmetas.put(vcmetaName, metaVDO);

                String typeVDO = metaVDO.getStringVDO("type");

                if (typeVDO.equals("nine-patch")) {
                    CompoundVDO ninePatchVDO = metaVDO.getCompoundVDO("ninePatch");

                    int cutTop = ninePatchVDO.getIntVDO("cutTop");
                    int cutBottom = ninePatchVDO.getIntVDO("cutBottom");
                    int cutLeft = ninePatchVDO.getIntVDO("cutLeft");
                    int cutRight = ninePatchVDO.getIntVDO("cutRight");

                    NinePatch ninePatch = new NinePatch((Texture) graphicalObjects, cutLeft, cutRight, cutTop, cutBottom);
                }
            }
        }
    }

    /**
     * Dispose the texture manager.
     */
    public void dispose() {
        for (TextureAtlas atlas : atlases.values()) {
            atlas.dispose();
        }

        for (Disposable object : graphicalObjects.values()) {
            object.dispose();
        }
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
    public static int getBlockTextureIndex(String atlasName, String name) {
        TextureAtlas atlas = atlases.get(atlasName);

        for (int i = 0, n = atlas.getRegions().size; i < n; i++) {
            if (atlas.getRegions().get(i).name.equals(name)) {
                return i;
            }
        }

        return getBlockTextureIndex(atlasName, "not_found");
    }
}
