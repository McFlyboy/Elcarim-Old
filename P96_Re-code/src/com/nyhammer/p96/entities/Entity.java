package com.nyhammer.p96.entities;

import com.nyhammer.p96.util.math.vector.Vector2f;

public class Entity {
	public Vector2f position;
	public Vector2f scale;
	public float angle;
	public Entity() {
		this(new Vector2f(), new Vector2f(1f,  1f), 0f);
	}
	public Entity(Vector2f position, Vector2f scale, float angle) {
		this.position = position;
		this.scale = scale;
		this.angle = angle;
	}
}
