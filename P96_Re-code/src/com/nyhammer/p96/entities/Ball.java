package com.nyhammer.p96.entities;

import com.nyhammer.p96.audio.Music;
import com.nyhammer.p96.structure.ResourceStorage;
import com.nyhammer.p96.ui.GameWindow;
import com.nyhammer.p96.util.math.collision.CC;
import com.nyhammer.p96.util.math.vector.Vector2f;
import com.nyhammer.p96.util.timing.TargetTimer;
import com.nyhammer.p96.util.timing.Timer;

public class Ball extends ModelEntity{
	public CC cc;
	public Vector2f direction;
	public TargetTimer miracleTimer;
	public boolean miracleActive;
	public boolean hit;
	public Ball(Timer baseTimer){
		super();
		position.x = 0.8f * GameWindow.ASPECT_RATIO;
		position.y = -0.2f;
		scale.x = 0.07f;
		scale.y = 0.07f;
		cc = new CC(position, 0.07f);
		direction = new Vector2f(-7f, 5f);
		color.red = 0f;
		color.green = 0f;
		color.blue = 0f;
		miracleTimer = new TargetTimer(baseTimer, ResourceStorage.getSound("miracleSound").getLength());
		hit = false;
	}
	public void update(float deltaTime, Timer timer){
		direction.x -= direction.x * 0.02f * deltaTime;
		direction.y -= 11.25f * deltaTime;
		if(Math.abs(position.y) > 1f - scale.y){
			position.y *= (1f - scale.y) / Math.abs(position.y);
			direction.y *= -0.9f;
			direction.x *= 0.992f;
		}
		if(Math.abs(position.x) > GameWindow.ASPECT_RATIO - scale.x){
			position.x *= (GameWindow.ASPECT_RATIO - scale.x) / Math.abs(position.x);
			direction.x *= -0.9f;
			direction.y *= 0.992f;
		}
		Vector2f deltaBallPosition = direction.getMul(0.2f * deltaTime);
		position.add(deltaBallPosition);
		angle -= 360f * deltaBallPosition.x / 2f * (float)Math.PI * scale.x;
		if(miracleTimer.targetReached()){
			deactivateMiracle();
			timer.resume();
		}
	}
	public void activateMiracle(){
		colorActive = true;
		miracleActive = true;
		miracleTimer.resume();
		ResourceStorage.getSound("miracleSound").play();
		Music bgm = ResourceStorage.getMusic("bgm");
		bgm.setActivePart(bgm.getActivePart() + 2, true);
	}
	public void deactivateMiracle(){
		colorActive = false;
		miracleActive = false;
		miracleTimer.reset();
		Music bgm = ResourceStorage.getMusic("bgm");
		bgm.setActivePart(bgm.getActivePart() - 2, true);
	}
}