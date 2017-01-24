package com.SinfulPixel.top;

import com.SinfulPixel.top.Models.TexturedModel;
import com.SinfulPixel.top.RenderEngine.DisplayManager;
import com.SinfulPixel.top.RenderEngine.Loader;
import com.SinfulPixel.top.Models.RawModel;
import com.SinfulPixel.top.RenderEngine.Renderer;
import com.SinfulPixel.top.Shaders.StaticShader;
import com.SinfulPixel.top.Textures.ModelTexture;
import org.lwjgl.opengl.Display;

public class Main {
    public Main() {
        DisplayManager.createDisplay();
        Loader loader = new Loader();
        Renderer renderer = new Renderer();
        StaticShader shader = new StaticShader();
        float[] vertices = {-0.5f,0.5f,0f,
                            -0.5f,-0.5f,0f,
                            0.5f,-0.5f,0f,
                            0.5f,0.5f,0f};
        int[] indices = {0,1,3,3,1,2};
        float[] textureCoords = {0,0,0,1,1,1,1,0};
        RawModel model = loader.loadToVAO(vertices,textureCoords,indices);
        ModelTexture texture = new ModelTexture(loader.loadTexture("flag"));
        TexturedModel texturedModel = new TexturedModel(model,texture);
        while(!Display.isCloseRequested()){
            renderer.prepare();
            shader.start();
            renderer.render(texturedModel);
            shader.stop();
            DisplayManager.updateDisplay();
        }
        shader.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }

    public static void main(String[] args) {
        new Main();
    }
}