package com.SinfulPixel.top.Entities;

import com.SinfulPixel.top.Terrains.Terrain;
import com.SinfulPixel.top.ToolBox.Maths;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by Vapor on 1/25/2017.
 */
public class Camera {
    private Vector3f position = new Vector3f(0,10,0);
    private float pitch;
    private float yaw;
    private float roll;
    private float distanceFromPlayer = 50;//ZOOM
    private float angleAroundPlayer = 0;
    private Player player;

    public Camera(Player player){
        this.player=player;
    }

    public void move(){
        calculateZoom();
        calculatePitch();
        calculateAngleAroundPlayer();
        float horizontalDistance = calculateHorizontalDistance();
        float verticleDistance = calculateVerticalDistance();
        calculateCameraPos(horizontalDistance,verticleDistance);
        this.yaw = 180 - (player.getRotY()+angleAroundPlayer);
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getRoll() {
        return roll;
    }
    private float calculateHorizontalDistance(){
        float hDistance = (float)(distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
        if(hDistance<0){
            hDistance=0;
        }
        return hDistance;
    }
    private void calculateCameraPos(float hDistance, float vDistance){
        float theta = player.getRotY() + angleAroundPlayer;
        float offsetX = (float) (hDistance * Math.sin(Math.toRadians(theta)));
        float offsetZ = (float) (hDistance * Math.cos(Math.toRadians(theta)));
        position.x = player.getPosition().x - offsetX;
        position.z = player.getPosition().z - offsetZ;
        position.y = player.getPosition().y + vDistance;
        if(position.y < 0.08726643f){
            position.y = 0.08726643f;
        }
    }
    private float calculateVerticalDistance(){
        float vDistance = (float)(distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
        if(vDistance<0){
            vDistance=0;
        }
        return vDistance;
    }
    private void calculateZoom(){
        float zoomLevel = Mouse.getDWheel() * 0.1f;
        if (distanceFromPlayer - zoomLevel > 10f && distanceFromPlayer - zoomLevel < 50f)
            distanceFromPlayer -= zoomLevel;
    }
    private void calculatePitch(){
        if(Mouse.isButtonDown(1)){
            float pitchChange = Mouse.getDY()*0.1f;
            pitch -= pitchChange;
            if(pitch<0){
                pitch=0;
            }else if(pitch>90){
                pitch=90;
            }
        }
    }
    private void calculateAngleAroundPlayer(){
        if(Mouse.isButtonDown(0)){
            float angleChange = Mouse.getDX()*0.3f;
            angleAroundPlayer -= angleChange;
        }
    }
}
