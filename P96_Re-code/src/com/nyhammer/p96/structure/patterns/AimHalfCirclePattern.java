package com.nyhammer.p96.structure.patterns;

import java.util.List;

import com.nyhammer.p96.entities.Bullet;
import com.nyhammer.p96.graphics.Texture;
import com.nyhammer.p96.structure.BulletPattern;
import com.nyhammer.p96.structure.ResourceStorage;
import com.nyhammer.p96.util.math.vector.Vector2f;
import com.nyhammer.p96.util.timing.TargetTimer;
import com.nyhammer.p96.util.timing.Timer;

public class AimHalfCirclePattern extends BulletPattern{
	private TargetTimer intervalTimer;
	private int bulletsPerHalfCircle;
	private boolean greenPresent;
	private float angleDifference;
	private Texture yellowTex;
	private Texture greenTex;
	public AimHalfCirclePattern(List<Bullet> sceneBullets, float speed, float size, Timer baseTimer, float interval, int bulletsPerHalfCircle){
		super(sceneBullets, speed, size);
		intervalTimer = new TargetTimer(baseTimer, interval);
		this.bulletsPerHalfCircle = bulletsPerHalfCircle;
		greenPresent = bulletsPerHalfCircle % 2 == 1;
		angleDifference = 180f / (float)(bulletsPerHalfCircle - 1);
		yellowTex = ResourceStorage.getTexture("bulletYellowTex");
		greenTex = ResourceStorage.getTexture("bulletGreenTex");
	}
	@Override
	protected void startSpecifics(){
		intervalTimer.resume();
	}
	@Override
	protected void updateSpecifics(float deltaTime, Vector2f sourcePosition, Vector2f targetPosition, float speed){
		if(intervalTimer.targetReached()){
			Vector2f targetDirection = targetPosition.getSub(sourcePosition).getNormalize().getMul(speed);
			for(int i = 0; i < bulletsPerHalfCircle; i++){
				addBullet(sourcePosition, targetDirection.getAdd(targetDirection.getRotate(90f + angleDifference * (float)i).getMul(0.33f)), (greenPresent && i == bulletsPerHalfCircle / 2) ? greenTex : yellowTex);
			}
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