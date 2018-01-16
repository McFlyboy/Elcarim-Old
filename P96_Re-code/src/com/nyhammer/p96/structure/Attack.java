package com.nyhammer.p96.structure;

import com.nyhammer.p96.util.math.vector.Vector2f;

public abstract class Attack{
	public BulletPattern[] patterns;
	protected abstract void updateSpesifics(float deltaTime, Vector2f sourcePosition, Vector2f targetPosition);
	public void update(float deltaTime, Vector2f sourcePosition, Vector2f targetPosition){
		updateSpesifics(deltaTime, sourcePosition, targetPosition);
		for(BulletPattern pattern : patterns){
			pattern.update(deltaTime, sourcePosition, targetPosition);
		}
	}
}