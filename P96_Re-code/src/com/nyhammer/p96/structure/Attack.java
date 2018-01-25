package com.nyhammer.p96.structure;

import com.nyhammer.p96.util.math.vector.Vector2f;

public abstract class Attack{
	public BulletPattern[] patterns;
	public abstract void update(float deltaTime, Vector2f sourcePosition, Vector2f targetPosition);
}