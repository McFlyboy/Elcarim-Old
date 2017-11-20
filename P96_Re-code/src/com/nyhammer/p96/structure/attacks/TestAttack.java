package com.nyhammer.p96.structure.attacks;

import java.util.List;

import com.nyhammer.p96.entities.Bullet;
import com.nyhammer.p96.structure.Attack;
import com.nyhammer.p96.structure.BulletPattern;
import com.nyhammer.p96.structure.patterns.TestPattern;
import com.nyhammer.p96.util.math.vector.Vector2f;
import com.nyhammer.p96.util.timing.Timer;

public class TestAttack extends Attack{
	public TestAttack(List<Bullet> sceneBullets, Timer baseTimer){
		patterns = new BulletPattern[]{
				new TestPattern(sceneBullets, baseTimer)
		};
	}
	@Override
	protected void updateSpesifics(Vector2f position){
		
	}
}