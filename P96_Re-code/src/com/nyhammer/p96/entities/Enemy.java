package com.nyhammer.p96.entities;

import com.nyhammer.p96.structure.Attack;
import com.nyhammer.p96.structure.ResourceStorage;
import com.nyhammer.p96.util.math.collision.CC;
import com.nyhammer.p96.util.timing.TargetTimer;
import com.nyhammer.p96.util.timing.Timer;

public abstract class Enemy extends ModelEntity{
	public CC cc;
	public int lives;
	public TargetTimer hitTimer;
	public boolean hit;
	public Attack[] attacks;
	public Enemy(Timer baseTimer){
		super();
		cc = new CC(position, 1f);
		lives = 1;
		hitTimer = new TargetTimer(baseTimer, ResourceStorage.getSound("hitSound").getLength());
	}
	public abstract void update(double time);
}