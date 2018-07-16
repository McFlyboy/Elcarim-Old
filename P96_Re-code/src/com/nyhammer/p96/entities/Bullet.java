package com.nyhammer.p96.entities;

import com.nyhammer.p96.structure.ResourceStorage;
import com.nyhammer.p96.util.math.collision.CC;
import com.nyhammer.p96.util.math.vector.Vector2f;
import com.nyhammer.p96.util.timing.TargetTimer;
import com.nyhammer.p96.util.timing.Timer;

public class Bullet extends ModelEntity {
	private static boolean init;
	private static Timer baseTimer;
	public int hp;
	public boolean intact;
	public TargetTimer intactTimer;
	public CC cc;
	public Vector2f direction;
	public Bullet(int hp, Vector2f direction) {
		super();
		scale.x = 0.03f;
		scale.y = 0.03f;
		cc = new CC(position, 0.03f);
		this.hp = hp;
		this.direction = direction;
		model = ResourceStorage.getModel("square");
		intact = true;
		intactTimer = new TargetTimer(baseTimer, 0.125f);
	}
	public void update() {
		if(!init) {
			return;
		}
		if(hp == 0) {
			intactTimer.start();
			super.colorActive = true;
			direction.x = 0f;
			direction.y = 0f;
		}
		if(intactTimer.targetReached()) {
			intact = false;
		}
	}
	public void updateMovement(float deltaTime) {
		position.add(direction.getMul(deltaTime));
	}
	public static void initBulletSystem(Timer baseTimer) {
		Bullet.baseTimer = baseTimer;
		init = true;
	}
}
