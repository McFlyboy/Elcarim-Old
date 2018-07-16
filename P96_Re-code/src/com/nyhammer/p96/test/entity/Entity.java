package com.nyhammer.p96.test.entity;

import com.nyhammer.p96.util.math.vector.Vector2f;

public abstract class Entity {
	private Vector2f position;
	private Vector2f scale;
	private float angle;
	public Entity() {
		position = new Vector2f();
		scale = new Vector2f(1f, 1f);
		angle = 0f;
	}
	public Vector2f getPosition() {
		return position;
	}
	public void setPosition(Vector2f position) {
		this.position = position;
	}
	public Vector2f getScale() {
		return scale;
	}
	public void setScale(Vector2f scale) {
		this.scale = scale;
	}
	public void setScale(float scale) {
		this.scale.x = scale;
		this.scale.y = scale;
	}
	public float getAngle() {
		return angle;
	}
	public void setAngle(float angle) {
		this.angle = angle;
	}
}
