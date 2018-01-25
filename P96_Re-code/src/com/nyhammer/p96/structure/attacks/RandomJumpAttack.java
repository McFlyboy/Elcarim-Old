package com.nyhammer.p96.structure.attacks;

import java.util.List;

import com.nyhammer.p96.entities.Bullet;
import com.nyhammer.p96.structure.Attack;
import com.nyhammer.p96.structure.BulletPattern;
import com.nyhammer.p96.structure.patterns.JumpPattern;
import com.nyhammer.p96.structure.patterns.RandomPattern;
import com.nyhammer.p96.util.math.vector.Vector2f;
import com.nyhammer.p96.util.timing.TargetTimer;
import com.nyhammer.p96.util.timing.Timer;

public class RandomJumpAttack extends Attack{
	private TargetTimer delayTimer;
	private boolean delayOver;
	private boolean started;
	public RandomJumpAttack(List<Bullet> sceneBullets, Timer baseTimer){
		super.patterns = new BulletPattern[]{
				new JumpPattern(sceneBullets, 0.8f, 0.4f),
				new RandomPattern(sceneBullets, 0.8f, 0.25f, baseTimer, 0.05f, false)
		};
		delayTimer = new TargetTimer(baseTimer, 2f);
		delayOver = false;
		started = false;
	}
	@Override
	public void update(float deltaTime, Vector2f sourcePosition, Vector2f targetPosition){
		if(!started){
			delayTimer.resume();
			started = true;
		}
		patterns[0].update(deltaTime, sourcePosition, targetPosition);
		if(delayTimer.targetReached()){
			delayOver = true;
			delayTimer.reset();
		}
		if(delayOver){
			patterns[1].update(deltaTime, sourcePosition, targetPosition);
		}
	}
}