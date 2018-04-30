package com.nyhammer.p96.structure.patterns;

import java.util.List;

import com.nyhammer.p96.entities.Bullet;
import com.nyhammer.p96.structure.BulletPattern;
import com.nyhammer.p96.structure.ResourceStorage;
import com.nyhammer.p96.util.math.vector.Vector2f;
import com.nyhammer.p96.util.timing.TargetTimer;
import com.nyhammer.p96.util.timing.Timer;

public class StaticAimStreamPattern extends BulletPattern {
	private TargetTimer streamIntervalTimer;
	private TargetTimer bulletIntervalTimer;
	private final int bulletsPerStream;
	private int bulletCount;
	private boolean streaming;
	public StaticAimStreamPattern(List<Bullet> levelBullets, float speed, float size, Timer baseTimer, float streamInterval, float bulletInterval, int bulletsPerStream) {
		super(levelBullets, speed, size);
		streamIntervalTimer = new TargetTimer(baseTimer, streamInterval);
		bulletIntervalTimer = new TargetTimer(baseTimer, bulletInterval);
		this.bulletsPerStream = bulletsPerStream;
		bulletCount = 0;
		streaming = false;
	}
	@Override
	protected void startSpecifics() {
		streamIntervalTimer.resume();
	}
	@Override
	protected void updateSpecifics(float deltaTime, Vector2f sourcePosition, Vector2f targetPosition, float speed) {
		if(streaming) {
			if(bulletIntervalTimer.targetReached()) {
				addBullet(sourcePosition, targetPosition.getSub(sourcePosition).getNormalize().getMul(speed), ResourceStorage.getTexture("bulletGreenTex"));
				bulletCount++;
			}
			if(bulletCount == bulletsPerStream) {
				streaming = false;
				bulletCount = 0;
				bulletIntervalTimer.reset();
				streamIntervalTimer.reset();
				streamIntervalTimer.resume();
			}
		}
		else {
			if(streamIntervalTimer.targetReached()) {
				streaming = true;
				bulletIntervalTimer.resume();
			}
		}
	}
}
