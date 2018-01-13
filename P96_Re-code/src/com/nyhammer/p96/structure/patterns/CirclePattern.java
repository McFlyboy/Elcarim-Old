package com.nyhammer.p96.structure.patterns;

import java.util.List;

import com.nyhammer.p96.entities.Bullet;
import com.nyhammer.p96.structure.BulletPattern;
import com.nyhammer.p96.structure.ResourceStorage;
import com.nyhammer.p96.util.math.vector.Vector2f;
import com.nyhammer.p96.util.timing.TargetTimer;
import com.nyhammer.p96.util.timing.Timer;

public class CirclePattern extends BulletPattern{
	private TargetTimer intervalTimer;
	private int bulletsPerCircle;
	public CirclePattern(List<Bullet> sceneBullets, float speed, float size, Timer baseTimer, float interval, int bulletsPerCircle){
		super(sceneBullets, speed, size);
		intervalTimer = new TargetTimer(baseTimer, interval);
		this.bulletsPerCircle = bulletsPerCircle;
	}
	@Override
	protected void startSpecifics(){
		intervalTimer.resume();
	}
	@Override
	protected void updateSpecifics(Vector2f sourcePosition, Vector2f targetPosition, float speed){
		if(intervalTimer.targetReached()){
			double angleDifference = 360.0 / (double)bulletsPerCircle;
			for(int i = 0; i < bulletsPerCircle; i++){
				double angle = 270.0 + angleDifference * i;
				double rad = Math.toRadians(angle);
				float x = (float)Math.cos(rad);
				float y = (float)Math.sin(rad);
				addBullet(sourcePosition, new Vector2f(x, y).getMul(speed));
			}
		}
	}
	@Override
	protected void addBullet(Vector2f sourcePosition, Vector2f direction){
		Bullet bullet = new Bullet(1, direction);
		bullet.position.x = sourcePosition.x;
		bullet.position.y = sourcePosition.y;
		bullet.scale.x = size;
		bullet.scale.y = size;
		bullet.cc.radius = size;
		bullet.texture = ResourceStorage.getTexture("bulletYellowTex");
		bullets.add(bullet);
		sceneBullets.add(bullet);
	}
}