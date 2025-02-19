package com.diamantino.voxelcraft.client.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.PixmapPacker;
import com.badlogic.gdx.graphics.g2d.PixmapPackerIO;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.diamantino.voxelcraft.common.Constants;
import com.diamantino.voxelcraft.common.utils.FileUtils;
import com.diamantino.voxelcraft.common.utils.MathUtils;
import com.diamantino.voxelcraft.client.VoxelCraftClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * A utility class for client assets loading.
 *
 * @author Diamantino
 */
public class ClientLoadingUtils {
    private ClientLoadingUtils() {}

    /**
     * Load the resources file of a mod.
     *
     * @param modResources Map of mod resources.
     * @param modId Mod ID.
     */
    public static void loadResourcesFile(Map<String, JSONObject> modResources, String modId) {
        JSONObject resourcesFile = new JSONObject(Gdx.files.internal(FileUtils.mergePaths("assets", modId, "resources.json")).readString());

        modResources.put(modId, resourcesFile);
    }

    /**
     * Load the textures of a mod.
     *
     * @param modId Mod ID.
     * @param modResources Mod resources.
     */
    public static void loadTextures(String modId, JSONObject modResources) {
        JSONArray textureLocationsVDO = modResources.getJSONArray("textureLocations");

        for (int i = 0; i < textureLocationsVDO.length(); i++) {
            String textureLoc = FileUtils.mergePaths("assets", modId, "textures", textureLocationsVDO.getString(i));

            List<FileHandle> textures = FileUtils.getAllFilesInFolderInternal(Gdx.files.internal(textureLoc), "png");
            List<FileHandle> vcMetas = FileUtils.getAllFilesInFolderInternal(Gdx.files.internal(textureLoc), "vcmeta");
            List<String> vcMetasNames = vcMetas.stream().map(FileHandle::pathWithoutExtension).toList();

            textures.forEach(texture -> {
                String textureName = texture.pathWithoutExtension();

                if (vcMetasNames.contains(textureName)) {
                    VoxelCraftClient.getInstance().assetManager.load(texture.path(), loadObject(textureName, texture));
                } else {
                    VoxelCraftClient.getInstance().assetManager.load(texture.path(), Texture.class);
                }
            });
        }
    }

    private static Object loadObject(String textureName, FileHandle texture) {
        String vcMetaPath = textureName + ".vcmeta";

        JSONObject metaVDO = new JSONObject(Gdx.files.internal(vcMetaPath).readString());

        VoxelCraftClient.getInstance().assetManager.load(vcMetaPath, JSONObject.class);

        String typeVDO = metaVDO.getString("type");

        if (typeVDO.equals("nine-patch")) {
            JSONObject ninePatchVDO = metaVDO.getJSONObject("ninePatch");

            int cutTop = ninePatchVDO.getInt("cutTop");
            int cutBottom = ninePatchVDO.getInt("cutBottom");
            int cutLeft = ninePatchVDO.getInt("cutLeft");
            int cutRight = ninePatchVDO.getInt("cutRight");

            return new NinePatch(new Texture(texture), cutLeft, cutRight, cutTop, cutBottom);
        }

        return null;
    }

    /**
     * Get the size of the width and height of the atlas in pixels.
     *
     * @param textureSize Size of the textures in the atlas.
     * @param files Number of files to be packed in the atlas.
     *
     * @return Size in pixels.
     */
    private static int getAtlasSize(int textureSize, int files) {
        int sideTex = MathUtils.getNearestPO2((int) Math.round(Math.sqrt(files)));

        return sideTex * textureSize;
    }

    //TODO: Add loading order for texture packs and catch the exception from the packer.

    /**
     * Save the atlases of a mod.
     *
     * @param atlasPackers Map of atlas packers.
     * @param modId Mod ID.
     * @param modResources Mod resources.
     */
    public static void saveAtlases(Map<String, PixmapPacker> atlasPackers, String modId, JSONObject modResources) {
        JSONArray atlasesVDO = modResources.getJSONArray("atlases");

        for (int i = 0; i < atlasesVDO.length(); i++) {
            JSONObject atlasJson = atlasesVDO.getJSONObject(i);

            String textureLoc = FileUtils.mergePaths("assets", modId, "textures", atlasJson.getString("location"));

            List<FileHandle> textures = FileUtils.getAllFilesInFolderInternal(Gdx.files.internal(textureLoc), "png");
            List<FileHandle> vcMetas = FileUtils.getAllFilesInFolderInternal(Gdx.files.internal(textureLoc), "vcmeta");

            if (textures.isEmpty()) {
                Gdx.app.error(Constants.errorLogTag, "No textures found in the location: " + textureLoc);

                return;
            }

            PixmapPacker packer = atlasPackers.computeIfAbsent(atlasJson.getString("name"), _ -> {
                int additionalSpace = 0;

                for (FileHandle metaFile : vcMetas) {
                    VoxelCraftClient.getInstance().assetManager.load(metaFile.path(), JSONObject.class);

                    JSONObject metaJson = new JSONObject(Gdx.files.internal(metaFile.path()).readString());

                    if (metaJson.has("frames")) {
                        additionalSpace += metaJson.getInt("frames");
                    }
                }

                int size = atlasJson.getInt("size");

                if (size == -1) {
                    size = getAtlasSize(new Texture(textures.getFirst()).getWidth(), textures.size() + additionalSpace);
                }

                return new PixmapPacker(size, size, Pixmap.Format.RGBA8888, 0, false);
            });

            for (FileHandle file : textures) {
                Pixmap pixmap = new Pixmap(file);

                packer.pack(file.nameWithoutExtension(), pixmap);

                pixmap.dispose();
            }
        }
    }

    /**
     * Load the atlases.
     *
     * @param atlasPackers Map of atlas packers.
     */
    public static void loadAtlases(Map<String, PixmapPacker> atlasPackers) {
        atlasPackers.forEach((atlasName, packer) -> {
            try {
                String atlasLoc = FileUtils.mergePaths(FileUtils.getVoxelCraftFolder(), "cache", "atlases", atlasName + ".atlas");

                PixmapPackerIO pixmapPackerIO = new PixmapPackerIO();

                pixmapPackerIO.save(Gdx.files.absolute(atlasLoc), packer);

                VoxelCraftClient.getInstance().assetManager.load(atlasLoc, TextureAtlas.class, false);

                packer.dispose();
            } catch (Exception e) {
                Gdx.app.error(Constants.errorLogTag, "Error while saving atlas: " + atlasName + ".atlas: " + e.getMessage());
            }
        });

        atlasPackers.clear();
    }

    /**
     * Get the texture of a mod.
     *
     * @param modId Mod ID.
     * @param subFolder Sub-folder of the texture.
     * @param name Name of the texture.
     *
     * @return The texture.
     */
    public static <T> T getTexture(String modId, String subFolder, String name, Class<T> type) {
        return VoxelCraftClient.getInstance().assetManager.get(FileUtils.mergePaths("assets", modId, "textures", subFolder, name + ".png"), type);
    }

    /**
     * Get the texture of a mod file.
     *
     * @param modId Mod ID.
     * @param subFolder Sub-folder of the texture.
     * @param name Name of the texture.
     *
     * @return The texture.
     */
    public static <T> T getObject(String modId, String subFolder, String name, Class<T> type) {
        FileHandle texturePath = Gdx.files.internal(FileUtils.mergePaths("assets", modId, "textures", subFolder, name + ".png"));
        FileHandle vcMetaPath = Gdx.files.internal(FileUtils.mergePaths("assets", modId, "textures", subFolder, name + ".png"));

        if (texturePath.exists()) {
            if (vcMetaPath.exists()) {
                return type.cast(loadObject(name, texturePath));
            } else {
                return type.cast(new Texture(texturePath));
            }
        }

        return null;
    }

    /**
     * Get the resources file of a mod.
     *
     * @param modId Mod ID.
     *
     * @return The resource file.
     */
    public static JSONObject getModResources(String modId) {
        return VoxelCraftClient.getInstance().assetManager.get(FileUtils.mergePaths("assets", modId, "resources.json"), JSONObject.class);
    }

    /**
     * Get the atlas from the name.
     *
     * @param atlasName Name of the atlas.
     *
     * @return The atlas.
     */
    public static TextureAtlas getAtlas(String atlasName) {
        return VoxelCraftClient.getInstance().assetManager.get(FileUtils.mergePaths(FileUtils.getVoxelCraftFolder(), "cache", "atlases", atlasName + ".atlas"), TextureAtlas.class);
    }

    /**
     * Get the texture index in the atlas.
     *
     * @param atlasName Name of the atlas.
     * @param name Name of the texture.
     * @return Index of the texture in the atlas.
     */
    public static int getBlockTextureIndex(String atlasName, String name) {
        TextureAtlas atlas = VoxelCraftClient.getInstance().assetManager.get(FileUtils.mergePaths(FileUtils.getVoxelCraftFolder(), "cache", "atlases", atlasName + ".atlas"), TextureAtlas.class);

        for (int i = 0, n = atlas.getRegions().size; i < n; i++) {
            if (atlas.getRegions().get(i).name.equals(name)) {
                return i;
            }
        }

        return getBlockTextureIndex(atlasName, "not_found");
    }
}
