package com.nyhammer.p96.structure.patterns;

import java.util.List;

import com.nyhammer.p96.entities.Bullet;
import com.nyhammer.p96.structure.BulletPattern;
import com.nyhammer.p96.structure.ResourceStorage;
import com.nyhammer.p96.util.math.vector.Vector2f;
import com.nyhammer.p96.util.timing.TargetTimer;
import com.nyhammer.p96.util.timing.Timer;

public class DelayAimPattern extends BulletPattern{
	private TargetTimer intervalTimer;
	private float acceleration;
	public DelayAimPattern(List<Bullet> sceneBullets, float speed, float size, float acceleration, Timer baseTimer, float interval){
		super(sceneBullets, speed, size);
		intervalTimer = new TargetTimer(baseTimer, interval);
		this.acceleration = acceleration;
	}
	@Override
	protected void startSpecifics(){
		intervalTimer.resume();
	}
	@Override
	protected void updateSpecifics(Vector2f sourcePosition, Vector2f targetPosition, float speed){
		for(Bullet bullet : bullets){
			float length = bullet.direction.getLength();
			if(length < speed){
				bullet.direction.mul(acceleration);
			}
			else if(length > speed){
				bullet.direction.mul(speed / length);
			}
		}
		if(intervalTimer.targetReached()){
			addBullet(sourcePosition, targetPosition.getSub(sourcePosition).getNormalize().getMul(speed * 0.001f));
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
		bullet.texture = ResourceStorage.getTexture("bulletGreenTex");
		bullets.add(bullet);
		sceneBullets.add(bullet);
	}
}