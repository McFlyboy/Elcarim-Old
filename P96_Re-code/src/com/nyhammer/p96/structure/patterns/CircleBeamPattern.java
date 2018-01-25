package com.nyhammer.p96.structure.patterns;

import java.util.List;

import com.nyhammer.p96.entities.Bullet;
import com.nyhammer.p96.structure.BulletPattern;
import com.nyhammer.p96.structure.ResourceStorage;
import com.nyhammer.p96.util.math.vector.Vector2f;
import com.nyhammer.p96.util.timing.TargetTimer;
import com.nyhammer.p96.util.timing.Timer;

public class CircleBeamPattern extends BulletPattern{
	private TargetTimer intervalTimer;
	private float rotationPerBullet;
	private float currentAngle;
	public CircleBeamPattern(List<Bullet> levelBullets, float speed, float size, Timer baseTimer, float interval, float rotationPerBullet){
		super(levelBullets, speed, size);
		intervalTimer = new TargetTimer(baseTimer, interval);
		this.rotationPerBullet = rotationPerBullet;
		currentAngle = 270f;
	}
	@Override
	protected void startSpecifics(){
		intervalTimer.resume();
	}
	@Override
	protected void updateSpecifics(float deltaTime, Vector2f sourcePosition, Vector2f targetPosition, float speed){
		if(intervalTimer.targetReached()){
			double rad = Math.toRadians(currentAngle);
			float x = (float)Math.cos(rad);
			float y = (float)Math.sin(rad);
			addBullet(sourcePosition, new Vector2f(x, y).getMul(speed), ResourceStorage.getTexture("bulletYellowTex"));
			currentAngle += rotationPerBullet;
		}
	}
}