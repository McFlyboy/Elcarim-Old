package com.nyhammer.p96.structure.patterns;

import java.util.List;

import com.nyhammer.p96.Main;
import com.nyhammer.p96.entities.Bullet;
import com.nyhammer.p96.structure.BulletPattern;
import com.nyhammer.p96.structure.ResourceStorage;
import com.nyhammer.p96.util.math.vector.Vector2f;
import com.nyhammer.p96.util.timing.TargetTimer;
import com.nyhammer.p96.util.timing.Timer;

public class TestPattern extends BulletPattern{
	private TargetTimer intervalTimer;
	public TestPattern(List<Bullet> sceneBullets, Timer baseTimer){
		super(sceneBullets);
		intervalTimer = new TargetTimer(baseTimer, 0.125f);
	}
	@Override
	protected void addBullet(Vector2f position, Vector2f direction){
		Bullet bullet = new Bullet(1, direction);
		bullet.position.x = position.x;
		bullet.position.y = position.y;
		bullet.model = ResourceStorage.getModel("square");
		bullet.texture = ResourceStorage.getTexture("bulletRedTex");
		bullets.add(bullet);
		sceneBullets.add(bullet);
	}
	@Override
	protected void startSpecifics(){
		intervalTimer.resume();
	}
	@Override
	protected void updateSpecifics(Vector2f position){
		if(intervalTimer.targetReached()){
			addBullet(position, new Vector2f(Main.getRandom().nextFloat() * 2f - 1f, Main.getRandom().nextFloat() * 2f - 1f));
		}
	}
}