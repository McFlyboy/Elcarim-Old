package com.nyhammer.p96.structure.patterns;

import java.util.List;

import com.nyhammer.p96.Game;
import com.nyhammer.p96.entities.Bullet;
import com.nyhammer.p96.graphics.Texture;
import com.nyhammer.p96.structure.BulletPattern;
import com.nyhammer.p96.structure.ResourceStorage;
import com.nyhammer.p96.util.math.vector.Vector2f;
import com.nyhammer.p96.util.timing.TargetTimer;
import com.nyhammer.p96.util.timing.Timer;

public class SpinPattern extends BulletPattern {
	private TargetTimer intervalTimer;
	private Texture bulletTex;
	public SpinPattern(List<Bullet> levelBullets, float speed, float size, Timer baseTimer, float interval) {
		super(levelBullets, speed, size);
		bulletTex = ResourceStorage.getTexture("bulletPurpleTex");
		intervalTimer = new TargetTimer(baseTimer, interval);
	}
	@Override
	protected void startSpecifics() {
		intervalTimer.resume();
	}
	@Override
	protected void updateSpecifics(float deltaTime, Vector2f sourcePosition, Vector2f targetPosition, float speed) {
		for(Bullet bullet : super.localBullets) {
			bullet.angle += 250f * deltaTime;
			bullet.direction.x = 0f;
			bullet.direction.y = 0f;
			Vector2f pointer = bullet.position.getSub(sourcePosition);
			Vector2f angle = pointer.getNormalize();
			Vector2f circleDirection = new Vector2f(angle.y, -angle.x);
			pointer.add(pointer.getNormalize().getMul(speed * 0.0005f));
			pointer.add(circleDirection.getMul(0.001f));
			bullet.position.x = pointer.x + sourcePosition.x;
			bullet.position.y = pointer.y + sourcePosition.y;
		}
		if(intervalTimer.targetReached()) {
			addBullet(sourcePosition, new Vector2f(Game.getRandom().nextFloat() * 2f - 1f, Game.getRandom().nextFloat() * 2f - 1f).getNormalize().getMul(speed), bulletTex);
		}
	}
}
