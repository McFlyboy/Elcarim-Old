package com.nyhammer.p96.structure.patterns;

import java.util.List;

import com.nyhammer.p96.entities.Bullet;
import com.nyhammer.p96.structure.BulletPattern;
import com.nyhammer.p96.structure.ResourceStorage;
import com.nyhammer.p96.util.math.vector.Vector2f;
import com.nyhammer.p96.util.timing.TargetTimer;
import com.nyhammer.p96.util.timing.Timer;

public class DelayAimPattern extends BulletPattern {
	private TargetTimer intervalTimer;
	private float acceleration;
	public DelayAimPattern(List<Bullet> levelBullets, float speed, float size, Timer baseTimer, float interval, float acceleration) {
		super(levelBullets, speed, size);
		intervalTimer = new TargetTimer(baseTimer, interval);
		this.acceleration = acceleration;
	}
	@Override
	protected void startSpecifics() {
		intervalTimer.resume();
	}
	@Override
	protected void updateSpecifics(float deltaTime, Vector2f sourcePosition, Vector2f targetPosition, float speed) {
		for(Bullet bullet : localBullets) {
			float length = bullet.direction.getLength();
			if(length < speed) {
				bullet.direction.mul(acceleration);
			}
			else if(length > speed) {
				bullet.direction.mul(speed / length);
			}
		}
		if(intervalTimer.targetReached()) {
			addBullet(sourcePosition, targetPosition.getSub(sourcePosition).getNormalize().getMul(speed * 0.001f), ResourceStorage.getTexture("bulletGreenTex"));
		}
	}
}
