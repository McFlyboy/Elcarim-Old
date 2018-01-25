package com.nyhammer.p96.structure.patterns;

import java.util.List;

import com.nyhammer.p96.entities.Bullet;
import com.nyhammer.p96.structure.BulletPattern;
import com.nyhammer.p96.structure.ResourceStorage;
import com.nyhammer.p96.util.math.vector.Vector2f;
import com.nyhammer.p96.util.timing.TargetTimer;
import com.nyhammer.p96.util.timing.Timer;

public class AimPattern extends BulletPattern{
	private TargetTimer intervalTimer;
	public AimPattern(List<Bullet> levelBullets, float speed, float size, Timer baseTimer, float interval){
		super(levelBullets, speed, size);
		intervalTimer = new TargetTimer(baseTimer, interval);
	}
	@Override
	protected void startSpecifics(){
		intervalTimer.resume();
	}
	@Override
	protected void updateSpecifics(float deltaTime, Vector2f sourcePosition, Vector2f targetPosition, float speed){
		if(intervalTimer.targetReached()){
			addBullet(sourcePosition, targetPosition.getSub(sourcePosition).getNormalize().getMul(speed), ResourceStorage.getTexture("bulletGreenTex"));
		}
	}
}