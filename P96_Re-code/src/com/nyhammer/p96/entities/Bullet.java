package com.nyhammer.p96.entities;

import com.nyhammer.p96.ui.GameWindow;
import com.nyhammer.p96.util.math.collision.CC;
import com.nyhammer.p96.util.math.vector.Vector2f;

public class Bullet extends ModelEntity{
	public int hp;
	public CC cc;
	public Vector2f direction;
	public Bullet(int hp, Vector2f direction){
		super();
		scale.x = 0.03f;
		scale.y = 0.03f;
		cc = new CC(position, 0.03f);
		this.hp = hp;
		this.direction = direction;
	}
	public void update(float deltaTime){
		position.add(direction.getMul(deltaTime));
		if(Math.abs(position.x) > GameWindow.ASPECT_RATIO + scale.x || Math.abs(position.y) > 1f + scale.y){
			hp = 0;
		}
	}
}