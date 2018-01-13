package com.nyhammer.p96.structure.levels;

import java.util.ArrayList;
import java.util.List;

import com.nyhammer.p96.audio.Music;
import com.nyhammer.p96.entities.Bullet;
import com.nyhammer.p96.entities.Enemy;
import com.nyhammer.p96.entities.ModelEntity;
import com.nyhammer.p96.entities.TextField;
import com.nyhammer.p96.entities.Todder;
import com.nyhammer.p96.graphics.Render;
import com.nyhammer.p96.graphics.Texture;
import com.nyhammer.p96.structure.BulletPattern;
import com.nyhammer.p96.structure.Level;
import com.nyhammer.p96.structure.ResourceStorage;
import com.nyhammer.p96.structure.attacks.SimpleAttack;
import com.nyhammer.p96.structure.patterns.CircleBeamPattern;
import com.nyhammer.p96.structure.patterns.StaticAimStreamPattern;
import com.nyhammer.p96.ui.GameWindow;
import com.nyhammer.p96.util.math.vector.Vector2f;
import com.nyhammer.p96.util.timing.Timer;

public class Level1 extends Level{
	private TextField enemyInfo;
	public Level1(List<Bullet> sceneBullets, Timer baseTimer){
		super(sceneBullets, baseTimer);
	}
	@Override
	protected void init(){
		Texture bgTex = new Texture("background/background.png");
		ResourceStorage.add("bgTex", bgTex);
		super.backgrounds.add(new ModelEntity(ResourceStorage.getModel("square"), bgTex, new Vector2f(), new Vector2f(GameWindow.ASPECT_RATIO, 1f), 0f));
		Music bgm = new Music(new String[]{
				"P96_1_intro.ogg",
				"P96_1_main.ogg",
				"P96_1_Miracle_intro.ogg",
				"P96_1_Miracle_main.ogg"
		});
		bgm.setPartLooping(0, false);
		bgm.setPartLooping(2, false);
		bgm.setNextPart(1, 4);
		ResourceStorage.add("bgm", bgm);
		super.bgms.add(bgm);
		Texture todderTex = new Texture("enemy/todder.png");
		ResourceStorage.add("todderTex", todderTex);
		enemyInfo = new TextField();
		enemyInfo.mainColor.mul(1f);
	}
	@Override
	protected void addWaves(List<Bullet> sceneBullets, Timer baseTimer){
		Todder todder = new Todder(baseTimer);
		todder.position.y = 1.1f;
		todder.position.x = 0.9f;
		todder.properPosition.y = 0.5f;
		todder.properPosition.x = 0.6f;
		todder.attacks.add(new SimpleAttack());
		todder.attacks.get(0).patterns = new BulletPattern[]{
				new StaticAimStreamPattern(sceneBullets, 2.5f, 0.2f, baseTimer, 1f, 0.025f, 5)
		};
		Todder todder2 = new Todder(baseTimer);
		todder2.position.y = 1.1f;
		todder2.position.x = -0.9f;
		todder2.properPosition.y = 0.5f;
		todder2.properPosition.x = -0.6f;
		todder2.attacks.add(new SimpleAttack());
		todder2.attacks.get(0).patterns = new BulletPattern[]{
				new CircleBeamPattern(sceneBullets, 0.75f, 0.5f, baseTimer, 0.1f, 26f),
				new CircleBeamPattern(sceneBullets, 1f, 0.25f, baseTimer, 0.1f, -52f)
		};
		List<Enemy> enemyWave = new ArrayList<Enemy>();
		enemyWave.add(todder);
		enemyWave.add(todder2);
		super.enemyWaves.add(enemyWave);
	}
	@Override
	protected void updateSpecifics(){
		
	}
	@Override
	public void renderSpesifics(){
		if(waveIndex < super.enemyWaves.size()){
			enemyInfo.setText(super.getEnemyCountString());
			enemyInfo.position.x = -GameWindow.ASPECT_RATIO + enemyInfo.getWidth() / 2f;
			enemyInfo.position.y = 1f - enemyInfo.getHeight() / 2f;
			Render.addToQueue(enemyInfo);
		}
	}
	@Override
	public void dispose(){
		ResourceStorage.disposeTexture("bgTex");
		ResourceStorage.disposeMusic("bgm");
		ResourceStorage.disposeTexture("todderTex");
	}
}