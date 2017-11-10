package com.nyhammer.p96.entities;

import com.nyhammer.p96.graphics.Model;
import com.nyhammer.p96.graphics.Texture;
import com.nyhammer.p96.util.math.collision.CC;
import com.nyhammer.p96.util.math.vector.Vector2f;

public class Bullet extends ModelEntity{
	public int hp;
	public CC cc;
	public Bullet(int hp){
		super();
		scale.x = 0.03f;
		scale.y = 0.03f;
		cc = new CC(position, 0.03f);
		this.hp = hp;
	}
	public Bullet(Model model, Texture texture, Vector2f position, Vector2f scale, float angle){
		super(model, texture, position, scale, angle);
	}
}