package com.SinfulPixel.top;

import com.SinfulPixel.top.Entities.Camera;
import com.SinfulPixel.top.Entities.Entity;
import com.SinfulPixel.top.Entities.Light;
import com.SinfulPixel.top.Models.TexturedModel;
import com.SinfulPixel.top.RenderEngine.*;
import com.SinfulPixel.top.Models.RawModel;
import com.SinfulPixel.top.Terrains.Terrain;
import com.SinfulPixel.top.Textures.ModelTexture;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public Main() {
        DisplayManager.createDisplay();
        Loader loader = new Loader();
        /*
        -=={ New OBJ Loader Usage }==-
         ModelData data = OBJFileLoader.loadOBJ("modelName");
         RawModel model = loader.loadToVAO(data.getVerticies(),data.getTextureCoords(),data.getNormals(),data.getIndices());
        */
        RawModel model = OBJLoader.loadObjModel("tree",loader);
        TexturedModel staticModel = new TexturedModel(model,new ModelTexture(loader.loadTexture("tree")));
        TexturedModel grass = new TexturedModel(OBJLoader.loadObjModel("grassModel",loader),new ModelTexture(loader.loadTexture("grassTexture")));
        grass.getTexture().setHasTransparency(true);
        grass.getTexture().setUseFakeLighting(true);
        TexturedModel fern = new TexturedModel(OBJLoader.loadObjModel("fern",loader), new ModelTexture(loader.loadTexture("fern")));
        fern.getTexture().setHasTransparency(true);
        Camera camera = new Camera();
        List<Entity> entities = new ArrayList<Entity>();
        Random random = new Random();
        for(int i=0;i<500;i++){
            entities.add(new Entity(staticModel,new Vector3f(random.nextFloat()*800-400,0,random.nextFloat()*-600),0,0,0,3));
            entities.add(new Entity(grass,new Vector3f(random.nextFloat()*800-400,0,random.nextFloat()*-600),0,0,0,1));
            entities.add(new Entity(fern,new Vector3f(random.nextFloat()*800-400,0,random.nextFloat()*-600),0,0,0,0.6f));
        }
        Light light = new Light(new Vector3f(0,0,-20),new Vector3f(1,1,1));
        Terrain terrain = new Terrain(-1,-1,loader,new ModelTexture(loader.loadTexture("grass")));
        Terrain terrain1 = new Terrain(0,-1,loader,new ModelTexture(loader.loadTexture("grass")));
        MasterRenderer renderer = new MasterRenderer();
        while(!Display.isCloseRequested()){
            //entity.increasePosition(0,0,-0.05f);
            //entity.increaseRotation(0,1,0);
            camera.move();
            renderer.processTerrain(terrain);
            renderer.processTerrain(terrain1);
            for(Entity e:entities){
                renderer.processEntity(e);
            }
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