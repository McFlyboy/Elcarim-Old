package com.nyhammer.p96.structure.attacks;

import com.nyhammer.p96.structure.Attack;
import com.nyhammer.p96.structure.BulletPattern;
import com.nyhammer.p96.util.math.vector.Vector2f;

public class SimpleAttack extends Attack{
	@Override
	public void update(float deltaTime, Vector2f sourcePosition, Vector2f targetPosition){
		for(BulletPattern pattern : patterns){
			pattern.update(deltaTime, sourcePosition, targetPosition);
		}
	}
}