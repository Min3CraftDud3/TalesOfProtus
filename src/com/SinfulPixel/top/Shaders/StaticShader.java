package com.SinfulPixel.top.Shaders;

/**
 * Created by Vapor on 1/24/2017.
 */
public class StaticShader extends ShaderProgram{

    private static final String VERTEX_FILE = "src/com/SinfulPixel/top/Shaders/vertexShader.txt";
    private static final String FRAGMENT_FILE = "src/com/SinfulPixel/top/Shaders/fragmentShader.txt";

    public StaticShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1,"textureCoords");
    }



}
