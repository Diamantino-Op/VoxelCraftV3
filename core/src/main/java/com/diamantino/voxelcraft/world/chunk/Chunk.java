package com.diamantino.voxelcraft.world.chunk;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.utils.MeshBuilder;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.diamantino.voxelcraft.blocks.Block;
import com.diamantino.voxelcraft.blocks.Blocks;
import com.diamantino.voxelcraft.client.rendering.RenderType;
import com.diamantino.voxelcraft.utils.AtlasManager;

public class Chunk {
    public static final int sizeX = 32;
    public static final int sizeY = 32;
    public static final int sizeZ = 32;
    public static final int blocksInChunk = sizeX * sizeY * sizeZ;
    private final ChunkData chunkData;

    public final int chunkX;
    public final int chunkY;
    public final int chunkZ;

    private Mesh chunkMesh;
    private final MeshBuilder builder;

    // TODO: Move renderer to it's own class
    private ShaderProgram coreShader;

    public Chunk(int chunkX, int chunkY, int chunkZ) {
        this.chunkX = chunkX;
        this.chunkY = chunkY;
        this.chunkZ = chunkZ;

        this.builder = new MeshBuilder();
        this.chunkData = new ChunkData(this);

        this.coreShader = new ShaderProgram(Gdx.files.internal("assets/voxelcraft/shaders/core.vert"), Gdx.files.internal("assets/voxelcraft/shaders/core.frag"));
    }

    public void render(Matrix4 projMatrix) {
        if (chunkMesh == null) return;

        Gdx.gl20.glEnable(GL20.GL_TEXTURE_2D);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        AtlasManager.blockAtlasTexture.bind();
        coreShader.bind();
        coreShader.setUniformMatrix("u_projTrans", projMatrix);
        coreShader.setUniformi("u_texture", 0);
        chunkMesh.render(coreShader, GL20.GL_TRIANGLES);
    }

    public void dispose() {
        chunkMesh.dispose();
    }

    public void setBlockAt(Block block, int localX, int localY, int localZ) {
        chunkData.setBlock(block, localX, localY, localZ);

        regenerateMesh();
    }

    public Block getBlockAt(int localX, int localY, int localZ) {
        return chunkData.getBlock(localX, localY, localZ);
    }

    public void regenerateMesh() {
        if (chunkMesh != null) chunkMesh.dispose();

        builder.begin(new VertexAttributes(
            new VertexAttribute(VertexAttributes.Usage.Position, 3, ShaderProgram.POSITION_ATTRIBUTE),
            new VertexAttribute(VertexAttributes.Usage.Normal, 3, ShaderProgram.NORMAL_ATTRIBUTE),
            new VertexAttribute(VertexAttributes.Usage.ColorPacked, 4, ShaderProgram.COLOR_ATTRIBUTE),
            new VertexAttribute(VertexAttributes.Usage.TextureCoordinates, 2, ShaderProgram.TEXCOORD_ATTRIBUTE + "0")
            ), GL20.GL_TRIANGLES);

        int currVertex = 0;

        // TODO: Use world to get block from nearby chunks
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                for (int z = 0; z < sizeZ; z++) {
                    Block currBlock = chunkData.getBlock(x, y, z);

                    if (currBlock != Blocks.air) {
                        // Front Face
                        if ((chunkData.getBlock(x, y, z - 1) == Blocks.air) || (currBlock.renderType == RenderType.OPAQUE && chunkData.getBlock(x, y, z - 1).renderType != RenderType.OPAQUE)) {
                            System.out.println(currBlock.name + " Front");
                            TextureRegion currFaceRegion = AtlasManager.blockAtlas.getRegions().get(currBlock.texture.getFrontTexIndex());
                            builder.vertex(new Vector3(x - 0.5f, y - 0.5f, z - 0.5f), null, null, new Vector2(currFaceRegion.getU(), currFaceRegion.getV2()));
                            builder.vertex(new Vector3(x - 0.5f, y + 0.5f, z - 0.5f), null, null, new Vector2(currFaceRegion.getU(), currFaceRegion.getV()));
                            builder.vertex(new Vector3(x + 0.5f, y + 0.5f, z - 0.5f), null, null, new Vector2(currFaceRegion.getU2(), currFaceRegion.getV()));
                            builder.vertex(new Vector3(x + 0.5f, y - 0.5f, z - 0.5f), null, null, new Vector2(currFaceRegion.getU2(), currFaceRegion.getV2()));
                            builder.index((short) currVertex, (short) (currVertex + 1), (short) (currVertex + 3));
                            builder.index((short) (currVertex + 1), (short) (currVertex + 2), (short) (currVertex + 3));

                            currVertex += 4;
                        }

                        // Right Face
                        if ((chunkData.getBlock(x + 1, y, z) == Blocks.air) || (currBlock.renderType == RenderType.OPAQUE && chunkData.getBlock(x + 1, y, z).renderType != RenderType.OPAQUE)) {
                            System.out.println(currBlock.name + " Right");
                            TextureRegion currFaceRegion = AtlasManager.blockAtlas.getRegions().get(currBlock.texture.getRightTexIndex());
                            builder.vertex(new Vector3(x + 0.5f, y - 0.5f, z - 0.5f), null, null, new Vector2(currFaceRegion.getU(), currFaceRegion.getV2()));
                            builder.vertex(new Vector3(x + 0.5f, y + 0.5f, z - 0.5f), null, null, new Vector2(currFaceRegion.getU(), currFaceRegion.getV()));
                            builder.vertex(new Vector3(x + 0.5f, y + 0.5f, z + 0.5f), null, null, new Vector2(currFaceRegion.getU2(), currFaceRegion.getV()));
                            builder.vertex(new Vector3(x + 0.5f, y - 0.5f, z + 0.5f), null, null, new Vector2(currFaceRegion.getU2(), currFaceRegion.getV2()));
                            builder.index((short) currVertex, (short) (currVertex + 1), (short) (currVertex + 3));
                            builder.index((short) (currVertex + 1), (short) (currVertex + 2), (short) (currVertex + 3));

                            currVertex += 4;
                        }

                        // Back Face
                        if ((chunkData.getBlock(x, y, z + 1) == Blocks.air) || (currBlock.renderType == RenderType.OPAQUE && chunkData.getBlock(x, y, z + 1).renderType != RenderType.OPAQUE)) {
                            System.out.println(currBlock.name + " Back");
                            TextureRegion currFaceRegion = AtlasManager.blockAtlas.getRegions().get(currBlock.texture.getBackTexIndex());
                            builder.vertex(new Vector3(x + 0.5f, y - 0.5f, z + 0.5f), null, null, new Vector2(currFaceRegion.getU(), currFaceRegion.getV2()));
                            builder.vertex(new Vector3(x + 0.5f, y + 0.5f, z + 0.5f), null, null, new Vector2(currFaceRegion.getU(), currFaceRegion.getV()));
                            builder.vertex(new Vector3(x - 0.5f, y + 0.5f, z + 0.5f), null, null, new Vector2(currFaceRegion.getU2(), currFaceRegion.getV()));
                            builder.vertex(new Vector3(x - 0.5f, y - 0.5f, z + 0.5f), null, null, new Vector2(currFaceRegion.getU2(), currFaceRegion.getV2()));
                            builder.index((short) currVertex, (short) (currVertex + 1), (short) (currVertex + 3));
                            builder.index((short) (currVertex + 1), (short) (currVertex + 2), (short) (currVertex + 3));

                            currVertex += 4;
                        }

                        // Left Face
                        if ((chunkData.getBlock(x - 1, y, z) == Blocks.air) || (currBlock.renderType == RenderType.OPAQUE && chunkData.getBlock(x - 1, y, z).renderType != RenderType.OPAQUE)) {
                            System.out.println(currBlock.name + " Left");
                            TextureRegion currFaceRegion = AtlasManager.blockAtlas.getRegions().get(currBlock.texture.getLeftTexIndex());
                            builder.vertex(new Vector3(x - 0.5f, y - 0.5f, z + 0.5f), null, null, new Vector2(currFaceRegion.getU(), currFaceRegion.getV2()));
                            builder.vertex(new Vector3(x - 0.5f, y + 0.5f, z + 0.5f), null, null, new Vector2(currFaceRegion.getU(), currFaceRegion.getV()));
                            builder.vertex(new Vector3(x - 0.5f, y + 0.5f, z - 0.5f), null, null, new Vector2(currFaceRegion.getU2(), currFaceRegion.getV()));
                            builder.vertex(new Vector3(x - 0.5f, y - 0.5f, z - 0.5f), null, null, new Vector2(currFaceRegion.getU2(), currFaceRegion.getV2()));
                            builder.index((short) currVertex, (short) (currVertex + 1), (short) (currVertex + 3));
                            builder.index((short) (currVertex + 1), (short) (currVertex + 2), (short) (currVertex + 3));

                            currVertex += 4;
                        }

                        // Top Face
                        if ((chunkData.getBlock(x, y + 1, z) == Blocks.air) || (currBlock.renderType == RenderType.OPAQUE && chunkData.getBlock(x, y + 1, z).renderType != RenderType.OPAQUE)) {
                            System.out.println(currBlock.name + " Top");
                            TextureRegion currFaceRegion = AtlasManager.blockAtlas.getRegions().get(currBlock.texture.getTopTexIndex());
                            builder.vertex(new Vector3(x - 0.5f, y + 0.5f, z - 0.5f), null, null, new Vector2(currFaceRegion.getU(), currFaceRegion.getV2()));
                            builder.vertex(new Vector3(x - 0.5f, y + 0.5f, z + 0.5f), null, null, new Vector2(currFaceRegion.getU(), currFaceRegion.getV()));
                            builder.vertex(new Vector3(x + 0.5f, y + 0.5f, z + 0.5f), null, null, new Vector2(currFaceRegion.getU2(), currFaceRegion.getV()));
                            builder.vertex(new Vector3(x + 0.5f, y + 0.5f, z - 0.5f), null, null, new Vector2(currFaceRegion.getU2(), currFaceRegion.getV2()));
                            builder.index((short) currVertex, (short) (currVertex + 1), (short) (currVertex + 3));
                            builder.index((short) (currVertex + 1), (short) (currVertex + 2), (short) (currVertex + 3));

                            currVertex += 4;
                        }

                        // Bottom Face
                        if ((chunkData.getBlock(x, y - 1, z) == Blocks.air) || (currBlock.renderType == RenderType.OPAQUE && chunkData.getBlock(x, y - 1, z).renderType != RenderType.OPAQUE)) {
                            System.out.println(currBlock.name + " Bottom");
                            TextureRegion currFaceRegion = AtlasManager.blockAtlas.getRegions().get(currBlock.texture.getBottomTexIndex());
                            builder.vertex(new Vector3(x - 0.5f, y - 0.5f, z - 0.5f), null, null, new Vector2(currFaceRegion.getU(), currFaceRegion.getV2()));
                            builder.vertex(new Vector3(x - 0.5f, y - 0.5f, z + 0.5f), null, null, new Vector2(currFaceRegion.getU(), currFaceRegion.getV()));
                            builder.vertex(new Vector3(x + 0.5f, y - 0.5f, z + 0.5f), null, null, new Vector2(currFaceRegion.getU2(), currFaceRegion.getV()));
                            builder.vertex(new Vector3(x + 0.5f, y - 0.5f, z - 0.5f), null, null, new Vector2(currFaceRegion.getU2(), currFaceRegion.getV2()));
                            builder.index((short) currVertex, (short) (currVertex + 1), (short) (currVertex + 3));
                            builder.index((short) (currVertex + 1), (short) (currVertex + 2), (short) (currVertex + 3));

                            currVertex += 4;
                        }
                    }
                }
            }
        }

        chunkMesh = builder.end();
    }

    public Mesh getChunkMesh() {
        return chunkMesh;
    }
}
