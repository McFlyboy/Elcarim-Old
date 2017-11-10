package com.nyhammer.p96.util.math.collision;

import com.nyhammer.p96.util.math.vector.Vector2f;

public class CC{
	public Vector2f position;
	public float radius;
	public CC(Vector2f position, float radius){
		this.position = position;
		this.radius = radius;
	}
	public static boolean checkCollision(CC cc0, CC cc1){
		float distance = new Vector2f(cc0.position.x - cc1.position.x, cc0.position.y - cc1.position.y).getLength();
		return distance < cc0.radius + cc1.radius;
	}
	public static boolean checkCollision(CC cc, Vector2f vec){
		float distance = new Vector2f(cc.position.x - vec.x, cc.position.y - vec.y).getLength();
		return distance < cc.radius;
	}
	@Override
	public String toString(){
		return String.format("Pos: %s, Rad: %f", position, radius);
	}
}