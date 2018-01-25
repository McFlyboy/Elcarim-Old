package com.nyhammer.p96.structure.patterns;

import java.util.List;

import com.nyhammer.p96.entities.Bullet;
import com.nyhammer.p96.structure.BulletPattern;
import com.nyhammer.p96.structure.ResourceStorage;
import com.nyhammer.p96.ui.GameWindow;
import com.nyhammer.p96.util.math.vector.Vector2f;

public class JumpPattern extends BulletPattern{
	private int direction;
	public JumpPattern(List<Bullet> levelBullets, float speed, float size){
		super(levelBullets, speed, size);
		direction = 1;
	}
	@Override
	protected void startSpecifics(){
		
	}
	@Override
	protected void updateSpecifics(float deltaTime, Vector2f sourcePosition, Vector2f targetPosition, float speed){
		if(super.localBullets.size() < 1){
			addBullet(new Vector2f((-GameWindow.ASPECT_RATIO - size * 0.9f) * direction, -0.93f), new Vector2f(1f, 0f).getMul(speed * direction), ResourceStorage.getTexture("bulletPurpleTex"));
			direction *= -1;
		}
		for(Bullet bullet : super.localBullets){
			bullet.angle += 250f * deltaTime;
		}
	}
}