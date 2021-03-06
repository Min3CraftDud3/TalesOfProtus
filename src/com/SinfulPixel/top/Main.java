package com.SinfulPixel.top;

import com.SinfulPixel.top.Entities.Camera;
import com.SinfulPixel.top.Entities.Entity;
import com.SinfulPixel.top.Entities.Light;
import com.SinfulPixel.top.Entities.Player;
import com.SinfulPixel.top.Models.TexturedModel;
import com.SinfulPixel.top.OBJConverter.ModelData;
import com.SinfulPixel.top.OBJConverter.OBJFileLoader;
import com.SinfulPixel.top.RenderEngine.*;
import com.SinfulPixel.top.Models.RawModel;
import com.SinfulPixel.top.Terrains.Terrain;
import com.SinfulPixel.top.Textures.ModelTexture;
import com.SinfulPixel.top.Textures.TerrainTexture;
import com.SinfulPixel.top.Textures.TerrainTexturePack;
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
        //Terrain Texture
        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("dirt"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("pinkFlowers"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));
        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture,rTexture,gTexture,bTexture);
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
        //---------------
        //Model Stuff
        //Tree
        ModelData treeData = OBJFileLoader.loadOBJ("tree");
        RawModel treeModel = loader.loadToVAO(treeData.getVertices(),treeData.getTextureCoords(),treeData.getNormals(),treeData.getIndices());
        TexturedModel tree = new TexturedModel(treeModel,new ModelTexture(loader.loadTexture("tree")));
        //Grass
        ModelData grassData = OBJFileLoader.loadOBJ("grassModel");
        RawModel grassModel = loader.loadToVAO(grassData.getVertices(),grassData.getTextureCoords(),grassData.getNormals(),grassData.getIndices());
        TexturedModel grass = new TexturedModel(grassModel,new ModelTexture(loader.loadTexture("grassTexture")));
        grass.getTexture().setHasTransparency(true);
        grass.getTexture().setUseFakeLighting(true);
        //Flowers
        ModelData flowerData = OBJFileLoader.loadOBJ("grassModel");
        RawModel flowerModel = loader.loadToVAO(flowerData.getVertices(),flowerData.getTextureCoords(),flowerData.getNormals(),flowerData.getIndices());
        TexturedModel flower = new TexturedModel(flowerModel,new ModelTexture(loader.loadTexture("flower")));
        flower.getTexture().setHasTransparency(true);
        flower.getTexture().setUseFakeLighting(true);
        //Ferns
        ModelData fernData = OBJFileLoader.loadOBJ("fern");
        RawModel fernModel = loader.loadToVAO(fernData.getVertices(),fernData.getTextureCoords(),fernData.getNormals(),fernData.getIndices());
        TexturedModel fern = new TexturedModel(fernModel,new ModelTexture(loader.loadTexture("fern")));
        fern.getTexture().setHasTransparency(true);
        //Player
        ModelData playerData = OBJFileLoader.loadOBJ("person");
        RawModel playerModel = loader.loadToVAO(playerData.getVertices(),playerData.getTextureCoords(),playerData.getNormals(),playerData.getIndices());
        TexturedModel playerM = new TexturedModel(playerModel,new ModelTexture(loader.loadTexture("playerTexture")));
        Player player = new Player(playerM,new Vector3f(0,0,-50),0,0,0,0.4f);
        //---------------
        Terrain terrain1 = new Terrain(0,-1,loader,texturePack,blendMap,"heightMap");
        Terrain terrain2 = new Terrain(-1,-1,loader,texturePack,blendMap,"heightMap");
        Terrain terrain3 = new Terrain(0,0,loader,texturePack,blendMap,"heightMap");
        Terrain terrain4 = new Terrain(1,1,loader,texturePack,blendMap,"heightMap");

        Terrain[][] terrain;
        terrain = new Terrain[2][2];
        terrain[0][0] = terrain1;
        terrain[1][0] = terrain2;
        terrain[0][1] = terrain3;
        terrain[1][1] = terrain4;
        Camera camera = new Camera(player);
        List<Entity> entities = new ArrayList<Entity>();
        Random random = new Random();
        int gridX = 0;
        int gridZ = 0;
        for(int j=0;j<terrain.length;j++){
            for(int k=0;k<terrain.length;k++) {
                for (int i = 0; i < 500; i++) {
                    if (i % 20 == 0) {
                        float x = random.nextFloat() * 800 - 400;
                        float z = random.nextFloat() * -600;
                        float y = terrain[j][k].getHeightOfTerrain(x, z);
                        entities.add(new Entity(tree, new Vector3f(x, y, z), 0, random.nextFloat() * 360, 0, 3));
                    }
                    if (i % 5 == 0) {
                        float x = random.nextFloat() * 800 - 400;
                        float z = random.nextFloat() * -600;
                        float y = terrain[j][k].getHeightOfTerrain(x, z);
                        entities.add(new Entity(flower, new Vector3f(x, y, z), 0, random.nextFloat() * 360, 0, 1));
                        entities.add(new Entity(fern, new Vector3f(x, y, z), 0, random.nextFloat() * 360, 0, 0.6f));
                    }
                    if (i % 7 == 0) {
                        float x = random.nextFloat() * 800 - 400;
                        float z = random.nextFloat() * -600;
                        float y = terrain[j][k].getHeightOfTerrain(x, z);
                        entities.add(new Entity(grass, new Vector3f(x, y, z), 0, random.nextFloat() * 360, 0, 1));
                    }
                    if (i % 15 == 0) {
                        float x = random.nextFloat() * 800 - 400;
                        float z = random.nextFloat() * -600;
                        float y = terrain[j][k].getHeightOfTerrain(x, z);
                        entities.add(new Entity(flower, new Vector3f(x, y, z), 0, random.nextFloat() * 360, 0, 1));
                    }
                }
            }
        }
        Light light = new Light(new Vector3f(20000,40000,20000),new Vector3f(1,1,1));
       MasterRenderer renderer = new MasterRenderer();
        while(!Display.isCloseRequested()){
            //Terrain currentTerrain = terrain[(int)(worldX/Terrain.SIZE)][(int)(worldZ/Terrain.SIZE)];﻿
            //entity.increasePosition(0,0,-0.05f);
            //entity.increaseRotation(0,1,0);
            camera.move();
            //player.move(terrain);
            //if(player.getPosition().x > 800 || player.getPosition().z > 800){
            //    player.move(terrain2);
            //}else{
            //    player.move(terrain);
            //}
            gridX = (int) (player.getPosition().x / Terrain.SIZE );
            gridZ = (int) (player.getPosition().z / Terrain.SIZE );
            player.move(terrain[gridX][gridZ]);
            renderer.processEntity(player);
            for(int j=0;j<terrain.length;j++){
                for(int k=0;k<terrain.length;k++){
                    renderer.processTerrain(terrain[j][k]);
                }
            }
            //renderer.processTerrain(terrain);
            //renderer.processTerrain(terrain1);
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
    /*
    Terrain terrains[][]=new Terrain[2][2];
  terrains[0][0]=new Terrain(0,-1,loadshaderdata, texturePack, blendMap, "heightmap.png");
  terrains[0][1]=new Terrain(1,-1,loadshaderdata, texturePack, blendMap, "heightmap2.png");
  terrains[1][0]=new Terrain(0,0,loadshaderdata, texturePack, blendMap, "heightmap.png");
  terrains[1][1]=new Terrain(1,0,loadshaderdata, texturePack, blendMap, "heightmap2.png");

<blah blah>

while (!Display.isCloseRequested()) {
   camera.move();

   int i=0,j=0;
   if (Math.floor(player.getPosition().x / terrains[0][0].getSIZE())==0) {
    i=0;
   } else if (Math.floor(player.getPosition().x / terrains[0][0].getSIZE())==1) {
    i=1;
   }
   if (Math.floor(player.getPosition().z /terrains[0][0].getSIZE())==-1) {
    j=0;
   } else if (Math.floor(player.getPosition().z / terrains[0][0].getSIZE())==0) {
    j=1;
   }
   System.out.println("Player.move(" + i + "," + j +")");
   player.move(terrains[j][i]);﻿
    * */
}