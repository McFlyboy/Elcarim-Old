package com.nyhammer.p96.structure;

import java.util.ArrayList;
import java.util.List;

import com.nyhammer.p96.entities.Bullet;
import com.nyhammer.p96.util.math.vector.Vector2f;
import com.nyhammer.p96.util.timing.Timer;

public abstract class BulletPattern{
	private boolean started;
	protected List<Bullet> bullets;
	protected List<Bullet> sceneBullets;
	protected Timer baseTimer;
	public BulletPattern(List<Bullet> sceneBullets){
		this.sceneBullets = sceneBullets;
		bullets = new ArrayList<Bullet>();
	}
	protected abstract void startSpecifics();
	protected abstract void updateSpecifics(Vector2f position);
	protected abstract void addBullet(Vector2f position, Vector2f direction);
	private void start(){
		startSpecifics();
		started = true;
	}
	public void update(Vector2f position){
		if(!started){
			start();
		}
		updateSpecifics(position);
		for(int i = 0; i < bullets.size(); i++){
			if(bullets.get(i).hp == 0){
				bullets.remove(i);
				i--;
			}
		}
	}
}