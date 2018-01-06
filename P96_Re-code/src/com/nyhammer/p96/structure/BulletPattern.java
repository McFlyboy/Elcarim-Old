package com.nyhammer.p96.structure;

import java.util.ArrayList;
import java.util.List;

import com.nyhammer.p96.entities.Bullet;
import com.nyhammer.p96.util.math.vector.Vector2f;

public abstract class BulletPattern{
	private boolean started;
	protected List<Bullet> bullets;
	protected List<Bullet> sceneBullets;
	protected float speed;
	public BulletPattern(List<Bullet> sceneBullets, float speed){
		this.sceneBullets = sceneBullets;
		this.speed = speed;
		bullets = new ArrayList<Bullet>();
	}
	protected abstract void startSpecifics();
	protected abstract void updateSpecifics(Vector2f sourcePosition, Vector2f targetPosition, float speed);
	protected abstract void addBullet(Vector2f sourcePosition, Vector2f direction);
	private void start(){
		startSpecifics();
		started = true;
	}
	public void update(Vector2f sourcePosition, Vector2f targetPosition){
		if(!started){
			start();
		}
		updateSpecifics(sourcePosition, targetPosition, speed);
		for(int i = 0; i < bullets.size(); i++){
			if(bullets.get(i).hp == 0){
				bullets.remove(i);
				i--;
			}
		}
	}
}