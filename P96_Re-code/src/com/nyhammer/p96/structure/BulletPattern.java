package com.nyhammer.p96.structure;

import java.util.ArrayList;
import java.util.List;

import com.nyhammer.p96.entities.Bullet;
import com.nyhammer.p96.graphics.Texture;
import com.nyhammer.p96.util.math.vector.Vector2f;

public abstract class BulletPattern{
	private boolean started;
	protected List<Bullet> localBullets;
	protected List<Bullet> levelBullets;
	private float speed;
	protected float size;
	public BulletPattern(List<Bullet> levelBullets, float speed, float size){
		this.levelBullets = levelBullets;
		this.speed = speed;
		this.size = size / 10f;
		localBullets = new ArrayList<Bullet>();
	}
	protected abstract void startSpecifics();
	protected abstract void updateSpecifics(float deltaTime, Vector2f sourcePosition, Vector2f targetPosition, float speed);
	private void start(){
		startSpecifics();
		started = true;
	}
	public void update(float deltaTime, Vector2f sourcePosition, Vector2f targetPosition){
		if(!started){
			start();
		}
		updateSpecifics(deltaTime, sourcePosition, targetPosition, speed);
		for(int i = 0; i < localBullets.size(); i++){
			if(!localBullets.get(i).intact){
				localBullets.remove(i);
				i--;
			}
		}
	}
	protected void addBullet(Vector2f sourcePosition, Vector2f direction, Texture bulletTex){
		Bullet bullet = new Bullet(1, direction);
		bullet.position.x = sourcePosition.x;
		bullet.position.y = sourcePosition.y;
		bullet.scale.x = size;
		bullet.scale.y = size;
		bullet.cc.radius = size;
		bullet.texture = bulletTex;
		localBullets.add(bullet);
		levelBullets.add(bullet);
	}
}