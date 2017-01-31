package com.SinfulPixel.top.Shaders;

import com.SinfulPixel.top.Entities.Camera;
import com.SinfulPixel.top.Entities.Light;
import com.SinfulPixel.top.ToolBox.Maths;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by Vapor on 1/31/2017.
 */
public class TerrainShader extends ShaderProgram {
    private static final String VERTEX_FILE = "src/com/SinfulPixel/top/Shaders/TerrainVertexShader.txt";
    private static final String FRAGMENT_FILE = "src/com/SinfulPixel/top/Shaders/TerrainFragmentShader.txt";

    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int location_lightPosition;
    private int location_lightColor;
    private int location_reflectivity;
    private int location_shineDamper;
    private int location_skyColor;

    public TerrainShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void getAllUniformLocations() {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        location_lightPosition = super.getUniformLocation("lightPosition");
        location_lightColor = super.getUniformLocation("lightColor");
        location_reflectivity = super.getUniformLocation("reflectivity");
        location_shineDamper = super.getUniformLocation("shineDamper");
        location_skyColor = super.getUniformLocation("skyColor");
    }
    public void loadSkyColor(float r,float g,float b){
        super.loadVector(location_skyColor,new Vector3f(r,g,b));
    }
    public void loadShineVars(float damper,float reflect){
        super.loadFloat(location_shineDamper,damper);
        super.loadFloat(location_reflectivity,reflect);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1,"textureCoords");
        super.bindAttribute(2,"normal");
    }

    public void loadTransformationMatrix(Matrix4f matrix){
        super.loadMatrix(location_transformationMatrix,matrix);
    }

    public void loadLight(Light light){
        super.loadVector(location_lightPosition,light.getPosition());
        super.loadVector(location_lightColor,light.getColor());
    }

    public void loadViewMatrix(Camera camera){
        Matrix4f viewMatrix = Maths.createViewMatrix(camera);
        super.loadMatrix(location_viewMatrix,viewMatrix);
    }

    public void loadProjectionMatrix(Matrix4f projection){
        super.loadMatrix(location_projectionMatrix,projection);
    }

}