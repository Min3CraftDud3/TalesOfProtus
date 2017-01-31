package com.SinfulPixel.top.RenderEngine;

import com.SinfulPixel.top.Entities.Entity;
import com.SinfulPixel.top.Models.RawModel;
import com.SinfulPixel.top.Models.TexturedModel;
import com.SinfulPixel.top.Shaders.TerrainShader;
import com.SinfulPixel.top.Terrains.Terrain;
import com.SinfulPixel.top.Textures.ModelTexture;
import com.SinfulPixel.top.ToolBox.Maths;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import java.util.List;

/**
 * Created by Vapor on 1/31/2017.
 */
public class TerrrainRenderer {
    private TerrainShader shader;
    public TerrrainRenderer(TerrainShader shader,Matrix4f projectionMatrix){
        this.shader=shader;
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }
    public void render(List<Terrain> terrains){
        for(Terrain terrain:terrains){
            prepareTerrain(terrain);
            loadModelMatrix(terrain);
            GL11.glDrawElements(GL11.GL_TRIANGLES,terrain.getModel().getVertexCount(),GL11.GL_UNSIGNED_INT,0);
            unbindTexturedModel();
        }
    }
    private void prepareTerrain(Terrain terrain){
        RawModel rawModel = terrain.getModel();
        GL30.glBindVertexArray(rawModel.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        ModelTexture texture = terrain.getTexture();
        shader.loadShineVars(texture.getShineDamper(),texture.getReflectivity());
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D,texture.getID());
    }
    private void unbindTexturedModel(){
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }
    private void loadModelMatrix(Terrain terrain){
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(new Vector3f(terrain.getX(),0,terrain.getZ()),0,0,0,1);
        shader.loadTransformationMatrix(transformationMatrix);
    }
}
