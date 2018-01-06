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
	protected List<List<Enemy>> enemyWaves;
	protected List<ModelEntity> backgrounds;
	protected List<Music> bgms;
	protected int waveIndex;
	protected int bgIndex;
	protected int bgmIndex;
	protected boolean completed;
	public Level(List<Bullet> sceneBullets, Timer baseTimer){
		enemyWaves = new ArrayList<List<Enemy>>();
		waveIndex = 0;
		backgrounds = new ArrayList<ModelEntity>();
		bgIndex = 0;
		bgms = new ArrayList<Music>();
		bgmIndex = 0;
		completed = false;
		init();
		addWaves(sceneBullets, baseTimer);
	}
	public boolean isCompleted(){
		return completed;
	}
	protected abstract void init();
	protected abstract void addWaves(List<Bullet> sceneBullets, Timer baseTimer);
	public List<Enemy> getCurrentWave(){
		if(completed){
			return null;
		}
		return enemyWaves.get(waveIndex);
	}
	public Music getCurrentMusic(){
		return bgms.get(bgmIndex);
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