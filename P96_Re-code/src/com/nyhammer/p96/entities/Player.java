package com.nyhammer.p96.entities;

import com.nyhammer.p96.structure.Animation;
import com.nyhammer.p96.ui.GameWindow;
import com.nyhammer.p96.util.math.collision.CC;
import com.nyhammer.p96.util.timing.TargetTimer;
import com.nyhammer.p96.util.timing.Timer;

public class Player extends ModelEntity{
	public Animation[] animations;
	public float walkingDistance;
	private TargetTimer hitTimer;
	public CC cc;
	public CC hitCC;
	public Player(Timer timer){
		super();
		cc = new CC(position, 0.035f);
		hitCC = new CC(position, 0.07f);
		animations = new Animation[]{
				new Animation(0, 0, 1),
				new Animation(0, 1, 2)
		};
		hitTimer = new TargetTimer(timer, 1.0);
	}
	public void update(){
		if(hitTimer.targetReached()){
			hitTimer.reset();
			animations[0].setTextureRow(0);
			animations[1].setTextureRow(0);
		}
	}
	public void walk(float movement){
		position.x += movement;
		if(Math.abs(position.x) > GameWindow.ASPECT_RATIO - scale.x){
			position.x *= (GameWindow.ASPECT_RATIO - scale.x) / Math.abs(position.x);
			movement = 0f;
		}
		if(movement != 0f){
			walkingDistance += movement;
			texture.setOffset(animations[1].getFrame((int)(walkingDistance * 10)), animations[1].getTextureRow());
		}
		else{
			walkingDistance = 0f;
			texture.setOffset(animations[0].getFrame(0), animations[0].getTextureRow());
		}
	}
	public void hit(){
		hitTimer.resume();
		animations[0].setTextureRow(1);
		animations[1].setTextureRow(1);
	}
}