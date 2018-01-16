package com.nyhammer.p96.structure.patterns;

import java.util.List;

import com.nyhammer.p96.Main;
import com.nyhammer.p96.entities.Bullet;
import com.nyhammer.p96.graphics.Texture;
import com.nyhammer.p96.structure.BulletPattern;
import com.nyhammer.p96.structure.ResourceStorage;
import com.nyhammer.p96.util.math.vector.Vector2f;
import com.nyhammer.p96.util.timing.TargetTimer;
import com.nyhammer.p96.util.timing.Timer;

public class HomingRandomPattern extends BulletPattern{
	private TargetTimer intervalTimer;
	private Texture bulletTex;
	private float roationSpeed;
	public HomingRandomPattern(List<Bullet> sceneBullets, float speed, float size, Timer baseTimer, float interval, float roationSpeed){
		super(sceneBullets, speed, size);
		bulletTex = ResourceStorage.getTexture("bulletPurpleTex");
		intervalTimer = new TargetTimer(baseTimer, interval);
		this.roationSpeed = roationSpeed;
	}
	@Override
	protected void startSpecifics(){
		intervalTimer.resume();
	}
	@Override
	protected void updateSpecifics(float deltaTime, Vector2f sourcePosition, Vector2f targetPosition, float speed){
		if(intervalTimer.targetReached()){
			addBullet(sourcePosition, new Vector2f(Main.getRandom().nextFloat() * 2f - 1f, Main.getRandom().nextFloat() * 2f - 1f).getNormalize().getMul(speed), bulletTex);
		}
		for(Bullet bullet : super.bullets){
			bullet.angle += 250f * deltaTime;
			float currentAngle = bullet.direction.getAngle();
			float targetAngle = targetPosition.getSub(bullet.position).getAngle();
			float angleDifference = Math.abs(targetAngle - currentAngle);
			if(angleDifference == 0f){
				continue;
			}
			float deltaAngle = targetAngle - currentAngle;
			if(angleDifference < 180f){
				deltaAngle /= angleDifference;
			}
			else{
				deltaAngle /= -angleDifference;
			}
			deltaAngle *= roationSpeed * deltaTime;
			bullet.direction.rotate(deltaAngle);
		}
	}
	@Override
	protected void addBullet(Vector2f sourcePosition, Vector2f direction, Texture bulletTex){
		Bullet bullet = new Bullet(1, direction);
		bullet.position.x = sourcePosition.x;
		bullet.position.y = sourcePosition.y;
		bullet.scale.x = size;
		bullet.scale.y = size;
		bullet.cc.radius = size;
		bullet.texture = bulletTex;
		bullets.add(bullet);
		sceneBullets.add(bullet);
	}
}