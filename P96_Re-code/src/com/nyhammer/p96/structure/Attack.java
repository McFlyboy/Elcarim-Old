package com.nyhammer.p96.structure;

import com.nyhammer.p96.util.math.vector.Vector2f;

public abstract class Attack{
	protected BulletPattern[] patterns;
	protected abstract void updateSpesifics(Vector2f position);
	public void update(Vector2f position){
		updateSpesifics(position);
		for(BulletPattern pattern : patterns){
			pattern.update(position);
		}
	}
}