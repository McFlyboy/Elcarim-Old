package com.nyhammer.p96.entities;

import com.nyhammer.p96.Main;
import com.nyhammer.p96.ui.GameWindow;
import com.nyhammer.p96.util.math.collision.CC;
import com.nyhammer.p96.util.math.vector.Vector2f;

public class Ball extends ModelEntity{
	public CC cc;
	public Vector2f direction;
	public Ball(){
		super();
		position.x = 0.8f * GameWindow.ASPECT_RATIO;
		position.y = -0.2f;
		scale.x = 0.07f;
		scale.y = 0.07f;
		cc = new CC(position, 0.07f);
		direction = new Vector2f(-7f, 5f);
		color.red = 0f;
		color.green = 0f;
		color.blue = 0f;
	}
	public void update(){
		direction.x *= 0.99998f;
		direction.y -= 0.012f;
		if(Math.abs(position.y) > 1f - scale.y){
			position.y *= (1f - scale.y) / Math.abs(position.y);
			direction.y *= -0.9f;
			direction.x *= 0.992f;
		}
		if(Math.abs(position.x) > GameWindow.ASPECT_RATIO - scale.x){
			position.x *= (GameWindow.ASPECT_RATIO - scale.x) / Math.abs(position.x);
			direction.x *= -0.9f;
			direction.y *= 0.992f;
		}
		Vector2f deltaBallPosition = direction.getMul(0.2f * Main.getDeltaTime());
		position.add(deltaBallPosition);
		angle -= 360.0 * deltaBallPosition.x / 2.0 * Math.PI * scale.x;
	}
}