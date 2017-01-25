package com.SinfulPixel.top;

import com.SinfulPixel.top.Entities.Camera;
import com.SinfulPixel.top.Entities.Entity;
import com.SinfulPixel.top.Entities.Light;
import com.SinfulPixel.top.Models.TexturedModel;
import com.SinfulPixel.top.RenderEngine.*;
import com.SinfulPixel.top.Models.RawModel;
import com.SinfulPixel.top.Shaders.StaticShader;
import com.SinfulPixel.top.Textures.ModelTexture;
import com.sun.prism.MaskTextureGraphics;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

public class Main {
    public Main() {
        DisplayManager.createDisplay();
        Loader loader = new Loader();

        RawModel model = OBJLoader.loadObjModel("dragon",loader);
        TexturedModel staticModel = new TexturedModel(model,new ModelTexture(loader.loadTexture("white")));
        ModelTexture texture = staticModel.getTexture();
        texture.setShineDamper(10);
        texture.setReflectivity(1);
        Camera camera = new Camera();
        Entity entity = new Entity(staticModel,new Vector3f(0,0,-50),0,0,0,1);
        Light light = new Light(new Vector3f(0,0,-20),new Vector3f(1,1,1));
        MasterRenderer renderer = new MasterRenderer();
        while(!Display.isCloseRequested()){
            //entity.increasePosition(0,0,-0.05f);
            //entity.increaseRotation(0,1,0);
            camera.move();
            
            renderer.render(light,camera);
            DisplayManager.updateDisplay();
        }
        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }

    public static void main(String[] args) {
        new Main();
    }
}