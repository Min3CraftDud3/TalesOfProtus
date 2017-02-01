package com.SinfulPixel.top.Entities;

import com.SinfulPixel.top.Models.TexturedModel;
import com.SinfulPixel.top.RenderEngine.DisplayManager;
import com.SinfulPixel.top.Terrains.Terrain;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by Vapor on 1/31/2017.
 */
public class Player extends Entity {
    private static final float RUN_SPEED = 20;
    private static final float TURN_SPEED = 160;
    private static final float GRAVITY = -50;
    private static final float JUMP_POWER = 30;
    public static float TERRAIN_HEIGHT = 0;
    private float currentSpeed = 0;
    private float currentTurnSpeed = 0;
    private float upwardsSpeed = 0;
    private boolean isInAir = false;
    public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
    }
    public void move(Terrain terrain){
        checkInputs();
        super.increaseRotation(0,currentTurnSpeed* DisplayManager.getFrameTimeSeconds(),0);
        float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
        float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
        float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
        increasePosition(dx,0,dz);
        upwardsSpeed += GRAVITY * DisplayManager.getFrameTimeSeconds();
        super.increasePosition(0,upwardsSpeed*DisplayManager.getFrameTimeSeconds(),0);
        float terrainHeight = terrain.getHeightOfTerrain(super.getPosition().x,super.getPosition().z);
        TERRAIN_HEIGHT = terrainHeight;
        if(super.getPosition().y<terrainHeight){
            upwardsSpeed = 0;
            isInAir = false;
            super.getPosition().y = terrainHeight;
        }
    }
    private void jump(){
        if(!isInAir) {
            this.upwardsSpeed = JUMP_POWER;
            isInAir = true;
        }
    }
    private void checkInputs(){
        //Forward and Reverse Movement (W & S)
        if(Keyboard.isKeyDown(Keyboard.KEY_W)){
            this.currentSpeed = RUN_SPEED;
        }else if(Keyboard.isKeyDown(Keyboard.KEY_S)){
            this.currentSpeed = -RUN_SPEED;
        }else{
            currentSpeed = 0;
        }
        //Turning Movement (A & D)
        if(Keyboard.isKeyDown(Keyboard.KEY_D)){
            this.currentTurnSpeed = -TURN_SPEED;
        }else if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
            this.currentTurnSpeed = TURN_SPEED;
        }else{
            this.currentTurnSpeed = 0;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
            jump();
        }
        /*
        To have the player move in the direction the camera is facing when you hold the mouse button and have the camera returns smoothly behind the player  when you move w/o holding the mouse button:

if(Mouse.isButtonDown(1)){
			if(!Mouse.isGrabbed())
				Mouse.setGrabbed(true);
			calculateAngleAroundPlayer();
			calculatePitch();
			if(Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_S)){
				player.increaseRotation(0, angleAroundPlayer, 0);
				angleAroundPlayer = 0;
			}
		}else if(!Mouse.isButtonDown(1)){
			if(Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_S)){
				angleAroundPlayer /= 1.2f;
				if(angleAroundPlayer >= -0.5f && angleAroundPlayer <= 0.5f)
					angleAroundPlayer = 0;
			}
		}
		if(!Mouse.isButtonDown(1) && Mouse.isGrabbed())
			Mouse.setGrabbed(false);﻿
        */
    }
}
