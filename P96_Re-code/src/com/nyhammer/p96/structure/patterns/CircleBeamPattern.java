package com.nyhammer.p96.structure.patterns;

import java.util.List;

import com.nyhammer.p96.entities.Bullet;
import com.nyhammer.p96.graphics.Texture;
import com.nyhammer.p96.structure.BulletPattern;
import com.nyhammer.p96.structure.ResourceStorage;
import com.nyhammer.p96.util.math.vector.Vector2f;
import com.nyhammer.p96.util.timing.TargetTimer;
import com.nyhammer.p96.util.timing.Timer;

public class CircleBeamPattern extends BulletPattern{
	private TargetTimer intervalTimer;
	private float rotationPerBullet;
	private float currentAngle;
	public CircleBeamPattern(List<Bullet> sceneBullets, float speed, float size, Timer baseTimer, float interval, float rotationPerBullet){
		super(sceneBullets, speed, size);
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
	@Override
	protected void addBullet(Vector2f sourcePosition, Vector2f direction, Texture bulletTex){
		Bullet bullet = new Bullet(1, direction);
		bullet.position.x = sourcePosition.x;
		bullet.position.y = sourcePosition.y;
		bullet.scale.x = size;
		bullet.scale.y = size;
		bullet.cc.radius = size;
		bullet.texture = bulletTex;
		bullets.add(bullet);
		sceneBullets.add(bullet);
	}
}