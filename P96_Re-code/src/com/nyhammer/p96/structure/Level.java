package com.nyhammer.p96.structure;

import java.util.ArrayList;
import java.util.List;

import com.nyhammer.p96.audio.Music;
import com.nyhammer.p96.entities.Bullet;
import com.nyhammer.p96.entities.Enemy;
import com.nyhammer.p96.entities.ModelEntity;
import com.nyhammer.p96.graphics.Render;
import com.nyhammer.p96.util.timing.Timer;

public abstract class Level{
	protected List<Bullet> bullets;
	protected List<List<Enemy>> enemyWaves;
	protected List<ModelEntity> backgrounds;
	protected List<Music> bgms;
	protected int waveIndex;
	protected int bgIndex;
	protected int bgmIndex;
	protected boolean completed;
	private int todderCount;
	private int luckTodderCloverCount;
	private int luckTodderSpadeCount;
	public Level(Timer baseTimer){
		bullets = new ArrayList<Bullet>();
		enemyWaves = new ArrayList<List<Enemy>>();
		waveIndex = 0;
		backgrounds = new ArrayList<ModelEntity>();
		bgIndex = 0;
		bgms = new ArrayList<Music>();
		bgmIndex = 0;
		completed = false;
		init(baseTimer);
		addWaves(baseTimer);
		setEnemyCounts();
	}
	public boolean isCompleted(){
		return completed;
	}
	public List<Bullet> getBullets(){
		return bullets;
	}
	protected String getBossString(){
		List<Enemy> currentWave = getCurrentWave();
		if(currentWave == null){
			return "";
		}
		if(currentWave.size() == 0){
			return "";
		}
		Enemy boss = currentWave.get(0);
		return boss.name + ": " + boss.lives;
	}
	protected String getEnemyCountString(){
		StringBuilder enemyCount = new StringBuilder();
		boolean firstLine = true;
		if(todderCount > 0){
			enemyCount.append("Todder x" + todderCount);
			firstLine = false;
		}
		if(luckTodderCloverCount > 0){
			if(!firstLine){
				enemyCount.append("\n");
			}
			enemyCount.append("Clover-Todder x" + luckTodderCloverCount);
			firstLine = false;
		}
		if(luckTodderSpadeCount > 0){
			if(!firstLine){
				enemyCount.append("\n");
			}
			enemyCount.append("Spade-Todder x" + luckTodderSpadeCount);
			firstLine = false;
		}
		return enemyCount.toString();
	}
	public List<Enemy> getCurrentWave(){
		if(completed){
			return null;
		}
		return enemyWaves.get(waveIndex);
	}
	public Music getCurrentMusic(){
		return bgms.get(bgmIndex);
	}
	protected abstract void init(Timer baseTimer);
	protected abstract void addWaves(Timer baseTimer);
	public void clearBullets(){
		for(Bullet bullet : bullets){
			bullet.hp = 0;
		}
	}
	protected void setEnemyCounts(){
		List<Enemy> currentWave = enemyWaves.get(waveIndex);
		for(Enemy enemy : currentWave){
			if(enemy.name.equals("Todder")){
				todderCount++;
			}
			if(enemy.name.equals("CloverTodder")){
				luckTodderCloverCount++;
			}
			if(enemy.name.equals("SpadeTodder")){
				luckTodderSpadeCount++;
			}
		}
	}
	public void subtractEnemyCount(String enemyType){
		if(enemyType.equals("Todder")){
			todderCount--;
		}
		if(enemyType.equals("CloverTodder")){
			luckTodderCloverCount--;
		}
		if(enemyType.equals("SpadeTodder")){
			luckTodderSpadeCount--;
		}
	}
	protected abstract void updateSpecifics();
	public void update(){
		if(bgmIndex < bgms.size()){
			bgms.get(bgmIndex).update();
		}
		if(completed){
			return;
		}
		if(enemyWaves.get(waveIndex).isEmpty()){
			waveIndex++;
			if(waveIndex < enemyWaves.size()){
				setEnemyCounts();
			}
		}
		if(waveIndex >= enemyWaves.size()){
			completed = true;
			return;
		}
		updateSpecifics();
	}
	public void renderBackground(){
		if(bgIndex < backgrounds.size()){
			Render.addToQueue(backgrounds.get(bgIndex));
		}
	}
	public void renderEnemies(){
		if(completed){
			return;
		}
		List<Enemy> enemyWave = enemyWaves.get(waveIndex);
		for(Enemy enemy : enemyWave){
			Render.addToQueue(enemy);
		}
	}
	public abstract void renderSpesifics();
	public abstract void dispose();
}