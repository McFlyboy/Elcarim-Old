package com.nyhammer.p96.entities;

import java.util.List;

import com.nyhammer.p96.Main;
import com.nyhammer.p96.structure.Animation;
import com.nyhammer.p96.structure.ResourceStorage;
import com.nyhammer.p96.ui.GameWindow;
import com.nyhammer.p96.util.math.collision.CC;
import com.nyhammer.p96.util.math.vector.Vector2f;
import com.nyhammer.p96.util.timing.TargetTimer;
import com.nyhammer.p96.util.timing.Timer;

public class Player extends ModelEntity{
	public Animation[] animations;
	public float walkingDistance;
	private TargetTimer hitTimer;
	public CC cc;
	public CC hitCC;
	public boolean jumping;
	public boolean alive;
	public boolean holdJumping;
	public Vector2f direction;
	public float lastXDirection;
	public boolean hitting;
	public int lives;
	public boolean invinsible;
	private TargetTimer invinsibilityTimer;
	private TargetTimer visibilityTimer;
	public Player(Timer timer){
		super();
		scale = new Vector2f(0.05f, 0.05f);
		position.y = -1f + scale.y;
		direction = new Vector2f();
		lastXDirection = 1f;
		cc = new CC(position, 0.035f);
		hitCC = new CC(position, 0.07f);
		animations = new Animation[]{
				new Animation(0, 0, 1),
				new Animation(0, 1, 2)
		};
		hitTimer = new TargetTimer(timer, 1.0);
		invinsibilityTimer = new TargetTimer(timer, 2.0);
		visibilityTimer = new TargetTimer(timer, 0.1 / 3.0);
		alive = true;
		lives = 4;
	}
	public void update(){
		if(!alive){
			direction.y -= 0.005f * Main.getDeltaTime();
			if(position.y < -2f){
				if(lives > 0){
					lives--;
					direction.y = 0f;
					position.x = 0f;
					position.y = -1f + scale.y;
					texture.setOffset(animations[0].getFrame(0), animations[0].getTextureRow());
					alive = true;
					invinsible = true;
					invinsibilityTimer.resume();
					visibilityTimer.resume();
				}
				else{
					GameWindow.close();
				}
			}
		}
		if(visibilityTimer.targetReached()){
			visible = !visible;
		}
		if(invinsibilityTimer.targetReached()){
			if(invinsibilityTimer.getTargetTime() == 2.0){
				invinsibilityTimer.setTargetTime(1.0);
				visibilityTimer.setTargetTime(0.2 / 3.0);
			}
			else{
				invinsible = false;
				visible = true;
				invinsibilityTimer.reset();
				invinsibilityTimer.setTargetTime(2.0);
				visibilityTimer.reset();
				visibilityTimer.setTargetTime(0.1 / 3.0);
			}
		}
		if(hitTimer.targetReached()){
			hitTimer.reset();
			hitting = false;
			animations[0].setTextureRow(0);
			animations[1].setTextureRow(0);
		}
		if(jumping){
			if(holdJumping){
				direction.y -= 0.008f * Main.getDeltaTime();
			}
			else{
				direction.y -= 0.015f * Main.getDeltaTime();
			}
		}
		position.add(direction);
		if(direction.x != 0f){
			lastXDirection = direction.x;
		}
		if(alive){
			if(Math.abs(position.x) > GameWindow.ASPECT_RATIO - scale.x){
				position.x *= (GameWindow.ASPECT_RATIO - scale.x) / Math.abs(position.x);
				direction.x = 0f;
			}
			float xMovement = direction.x;
			if(jumping){
				xMovement = 0f;
			}
			if(xMovement != 0f){
				walkingDistance += xMovement;
				texture.setOffset(animations[1].getFrame((int)(walkingDistance * 10)), animations[1].getTextureRow());
			}
			else{
				walkingDistance = 0f;
				texture.setOffset(animations[0].getFrame(0), animations[0].getTextureRow());
			}
			if(jumping && position.y < -1f + scale.y){
				position.y = -1f + scale.y;
				direction.y = 0f;
				jumping = false;
			}
		}
	}
	public void walk(float movement){
		direction.x = movement * Main.getDeltaTime() * 1.1f;
	}
	public void jump(){
		if(jumping){
			return;
		}
		jumping = true;
		direction.y = 0.0023f;
	}
	public void hit(){
		hitting = true;
		animations[0].setTextureRow(1);
		animations[1].setTextureRow(1);
		hitTimer.resume();
	}
	public void shoot(List<Shot> shots){
		Shot shot = new Shot();
		shot.model = ResourceStorage.getModel("square");
		shot.texture = ResourceStorage.getTexture("shotTex");
		shot.position.x = position.x;
		shot.position.y = position.y;
		shots.add(shot);
	}
	public void die(){
		if(!alive || invinsible){
			return;
		}
		alive = false;
		jumping = false;
		hitting = false;
		hitTimer.reset();
		animations[0].setTextureRow(0);
		texture.setOffset(animations[0].getFrame(0), 2);
		direction.y = 0.002f;
		direction.x = 0f;
		ResourceStorage.getSound("deathSound").play();
	}
}