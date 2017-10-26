package com.nyhammer.p96.entities;

import com.nyhammer.p96.util.math.vector.Vector2f;

public class Entity{
	protected Vector2f position;
	protected Vector2f scale;
	protected float angle;
	public Entity(){
		this(new Vector2f(), new Vector2f(1f,  1f), 0f);
	}
	public Entity(Vector2f position, Vector2f scale, float angle){
		this.position = position;
		this.scale = scale;
		this.angle = angle;
	}
	public Vector2f getPosition(){
		return position;
	}
	public void setPosition(Vector2f position){
		this.position = position;
	}
	public Vector2f getScale(){
		return scale;
	}
	public void setScale(Vector2f scale){
		this.scale = scale;
	}
	public float getAngle(){
		return angle;
	}
	public void setAngle(float angle){
		this.angle = angle;
	}
}