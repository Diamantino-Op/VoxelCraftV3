package com.diamantino.voxelcraft.client.screens;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.diamantino.voxelcraft.common.registration.Mods;
import com.diamantino.voxelcraft.common.utils.FileUtils;
import com.diamantino.voxelcraft.common.vdo.CompoundVDO;
import com.diamantino.voxelcraft.launchers.VoxelCraftClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LoadingScreen implements Screen {
    private final VoxelCraftClient game;

    private Stage stage;

    private final OrthographicCamera camera;
    private final ScreenViewport viewport;

    private ProgressBar loadingBar;

    /**
     *  Temporary atlas textures registry.
     *  Key: Texture location.
     *  Value: Atlas ID.
     */
    private Map<String, JSONObject> modResources;

    public LoadingScreen(VoxelCraftClient game) {
        this.game = game;

        camera = new OrthographicCamera();

        viewport = new ScreenViewport(camera);

        draw(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 1f);
    }

    private void startLoadingResources() {
        loadResourcesFile("voxelcraft");

        Mods.mods.forEach((modId, _) -> {
            loadResourcesFile(modId);
        });

        //------------------- Stage 1 -------------------
        modResources.forEach(this::loadTextures);

        //------------------- Stage 2 -------------------
        modResources.forEach(this::loadAtlases);

        //------------------- Stage 3 -------------------
    }

    private void loadResourcesFile(String modId) {
        JSONObject resourcesFile = new JSONObject(Gdx.files.internal("assets/" + modId + "/resources.json").readString());

        modResources.put(modId, resourcesFile);
    }

    private void loadTextures(String modId, JSONObject modResources) {
        JSONArray textureLocationsVDO = modResources.getJSONArray("textureLocations");

        for (int i = 0; i < textureLocationsVDO.length(); i++) {
            String textureLoc = "assets" + File.separator + modId + File.separator + textureLocationsVDO.getString(i);

            List<FileHandle> textures = FileUtils.getAllFilesInFolderInternal(Gdx.files.internal(textureLoc), "png");
            List<FileHandle> vcMetas = FileUtils.getAllFilesInFolderInternal(Gdx.files.internal(textureLoc), "vcmeta");
            List<String> vcMetasNames = vcMetas.stream().map(FileHandle::pathWithoutExtension).toList();

            textures.forEach(texture -> {
                String textureName = texture.pathWithoutExtension();

                if (vcMetasNames.contains(textureName)) {
                    String vcMetaPath = textureName + ".vcmeta";

                    CompoundVDO metaVDO = new CompoundVDO();
                    metaVDO.parseVDO(Gdx.files.internal(vcMetaPath).readString());

                    game.assetManager.load(vcMetaPath, JSONObject.class);

                    String typeVDO = metaVDO.getStringVDO("type");

                    if (typeVDO.equals("nine-patch")) {
                        CompoundVDO ninePatchVDO = metaVDO.getCompoundVDO("ninePatch");

                        int cutTop = ninePatchVDO.getIntVDO("cutTop");
                        int cutBottom = ninePatchVDO.getIntVDO("cutBottom");
                        int cutLeft = ninePatchVDO.getIntVDO("cutLeft");
                        int cutRight = ninePatchVDO.getIntVDO("cutRight");

                        NinePatch ninePatch = new NinePatch(new Texture(texture), cutLeft, cutRight, cutTop, cutBottom);

                        game.assetManager.load(ninePatch, NinePatch.class);
                    }
                } else {
                    game.assetManager.load(texture.path(), Texture.class);
                }
            });
        }
    }

    private void loadTextures() {
        List<FileHandle> textures = FileUtils.getAllFilesInFolderInternal(Gdx.files.internal("assets/voxelcraft/" + location), "png");
    }

    private void loadAtlases(String modId, JSONObject modResources) {
        JSONArray atlasesVDO = modResources.getJSONArray("atlases");

        for (int i = 0; i < atlasesVDO.length(); i++) {
            JSONObject atlasVDO = atlasesVDO.getJSONObject(i);

            initAtlas(atlasVDO.getString("name"), atlasVDO.getInt("size"), atlasVDO.getString("location"));
        }
    }

    public void draw(int width, int height, float guiScale) {
        if (stage != null) {
            stage.clear();
            stage.dispose();
        }

        camera.setToOrtho(false, width, height);

        viewport.update(width, height, true);

        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        loadingBar = new ProgressBar(0, 100, 1, false, );
    }

    /**
     * Called when this screen becomes the current screen for a {@link Game}.
     */
    @Override
    public void show() {
        ScreenUtils.clear(0, 0, 1, 1);

        camera.update();
    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 1, 1, true);

        stage.act(delta);
        stage.draw();
    }

    /**
     * @param width The width of the screen.
     * @param height The height of the screen.
     * @see ApplicationListener#resize(int, int)
     */
    @Override
    public void resize(int width, int height) {
        draw(width, height, 1f);
    }

    /**
     * @see ApplicationListener#pause()
     */
    @Override
    public void pause() {

    }

    /**
     * @see ApplicationListener#resume()
     */
    @Override
    public void resume() {

    }

    /**
     * Called when this screen is no longer the current screen for a {@link Game}.
     */
    @Override
    public void hide() {

    }

    /**
     * Called when this screen should release all resources.
     */
    @Override
    public void dispose() {
        stage.dispose();
    }
}
