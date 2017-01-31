package com.SinfulPixel.top.RenderEngine;

import com.SinfulPixel.top.Entities.Entity;
import com.SinfulPixel.top.Models.RawModel;
import com.SinfulPixel.top.Models.TexturedModel;
import com.SinfulPixel.top.Shaders.StaticShader;
import com.SinfulPixel.top.Textures.ModelTexture;
import com.SinfulPixel.top.ToolBox.Maths;
import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Matrix4f;

import java.util.List;
import java.util.Map;

/**
 * Created by Vapor on 1/24/2017.
 */
public class EntityRenderer {

    private static final float FOV = 70;
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 1000;
    private Matrix4f projectionMatrix;
    private StaticShader shader;

    public EntityRenderer(StaticShader shader, Matrix4f projectionMatrix){
        this.shader = shader;
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }


    public void render(Map<TexturedModel,List<Entity>> entities){
        for(TexturedModel model:entities.keySet()){
            prepareTexturedModel(model);
            List<Entity> batch = entities.get(model);
            for(Entity entity:batch){
                prepareInstance(entity);
                GL11.glDrawElements(GL11.GL_TRIANGLES,model.getRawModel().getVertexCount(),GL11.GL_UNSIGNED_INT,0);
            }
            unbindTexturedModel();
        }
    }
    private void prepareTexturedModel(TexturedModel model){
        RawModel rawModel = model.getRawModel();
        GL30.glBindVertexArray(rawModel.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        ModelTexture texture = model.getTexture();
        shader.loadShineVars(texture.getShineDamper(),texture.getReflectivity());
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D,model.getTexture().getID());
    }
    private void unbindTexturedModel(){
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }
    private void prepareInstance(Entity entity){
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(),entity.getRotX(),entity.getRotY(),entity.getRotZ(),entity.getScale());
        shader.loadTransformationMatrix(transformationMatrix);
    }

    /*
    public void render(Entity entity, StaticShader shader){
        TexturedModel texturedModel = entity.getModel();
        RawModel rawModel = texturedModel.getRawModel();
        GL30.glBindVertexArray(rawModel.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(),entity.getRotX(),entity.getRotY(),entity.getRotZ(),entity.getScale());
        shader.loadTransformationMatrix(transformationMatrix);
        ModelTexture texture = texturedModel.getTexture();
        shader.loadShineVars(texture.getShineDamper(),texture.getReflectivity());
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D,texturedModel.getTexture().getID());
        GL11.glDrawElements(GL11.GL_TRIANGLES,rawModel.getVertexCount(),GL11.GL_UNSIGNED_INT,0);
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }
    */

}
