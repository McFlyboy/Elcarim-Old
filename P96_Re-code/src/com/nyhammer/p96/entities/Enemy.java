package com.nyhammer.p96.entities;

import java.util.ArrayList;
import java.util.List;

import com.nyhammer.p96.structure.Attack;
import com.nyhammer.p96.structure.ResourceStorage;
import com.nyhammer.p96.util.math.collision.CC;
import com.nyhammer.p96.util.math.vector.Vector2f;
import com.nyhammer.p96.util.timing.TargetTimer;
import com.nyhammer.p96.util.timing.Timer;

public abstract class Enemy extends ModelEntity{
	public CC cc;
	public int lives;
	public TargetTimer hitTimer;
	public boolean hit;
	public List<Attack> attacks;
	public int attackIndex;
	public String name;
	public Vector2f properPosition;
	protected double positionedTime;
	protected boolean positioned;
	public int state;
	public Enemy(Timer baseTimer, int lives){
		super();
		cc = new CC(position, 1f);
		this.lives = lives;
		hitTimer = new TargetTimer(baseTimer, ResourceStorage.getSound("hitSound").getLength());
		attacks = new ArrayList<Attack>();
		attackIndex = 0;
		name = "Enemy";
		properPosition = new Vector2f();
		positionedTime = 0.0;
		positioned = false;
		state = 0;
	}
	public void addAttack(Attack attack){
		attacks.add(attack);
	}
	public abstract void update(double time, float deltaTime, Vector2f playerPosition);
}