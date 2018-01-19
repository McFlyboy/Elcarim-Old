package com.nyhammer.p96.structure.levels;

import java.util.ArrayList;
import java.util.List;

import com.nyhammer.p96.Main;
import com.nyhammer.p96.audio.Music;
import com.nyhammer.p96.entities.Bullet;
import com.nyhammer.p96.entities.Enemy;
import com.nyhammer.p96.entities.LuckTodder;
import com.nyhammer.p96.entities.ModelEntity;
import com.nyhammer.p96.entities.TextField;
import com.nyhammer.p96.entities.Todder;
import com.nyhammer.p96.graphics.Render;
import com.nyhammer.p96.graphics.Texture;
import com.nyhammer.p96.structure.BulletPattern;
import com.nyhammer.p96.structure.Level;
import com.nyhammer.p96.structure.ResourceStorage;
import com.nyhammer.p96.structure.attacks.SimpleAttack;
import com.nyhammer.p96.structure.patterns.AimHalfCirclePattern;
import com.nyhammer.p96.structure.patterns.AimPattern;
import com.nyhammer.p96.structure.patterns.CircleBeamPattern;
import com.nyhammer.p96.structure.patterns.CirclePattern;
import com.nyhammer.p96.structure.patterns.DelayAimPattern;
import com.nyhammer.p96.structure.patterns.HomingRandomPattern;
import com.nyhammer.p96.structure.patterns.RandomPattern;
import com.nyhammer.p96.structure.patterns.StaticAimStreamPattern;
import com.nyhammer.p96.ui.GameWindow;
import com.nyhammer.p96.util.math.vector.Vector2f;
import com.nyhammer.p96.util.timing.Timer;

public class Level1 extends Level{
	private TextField enemyInfo;
	private int random;
	public Level1(List<Bullet> sceneBullets, Timer baseTimer){
		super(sceneBullets, baseTimer);
	}
	public int getRandomAttackIndex(){
		return random;
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
		Texture luckTodderCloverTex = new Texture("enemy/luckTodder_Clover.png");
		ResourceStorage.add("luckTodderCloverTex", luckTodderCloverTex);
		Texture luckTodderSpadeTex = new Texture("enemy/luckTodder_Spade.png");
		ResourceStorage.add("luckTodderSpadeTex", luckTodderSpadeTex);
		Texture yargKradTex = new Texture("enemy/boss/yargKrad.png");
		ResourceStorage.add("yargKradTex", yargKradTex);
		enemyInfo = new TextField();
		enemyInfo.mainColor.mul(1f);
	}
	@Override
	protected void addWaves(List<Bullet> sceneBullets, Timer baseTimer){
		Todder todder1 = new Todder(baseTimer);
		todder1.position.y = 1.7f;
		todder1.position.x = 1.3f;
		todder1.properPosition.y = 0.5f;
		todder1.properPosition.x = 0.3f;
		todder1.attacks.add(new SimpleAttack());
		todder1.attacks.get(0).patterns = new BulletPattern[]{
				new RandomPattern(sceneBullets, 0.5f, 0.3f, baseTimer, 0.5f)
		};
		List<Enemy> enemyWave1 = new ArrayList<Enemy>();
		enemyWave1.add(todder1);
		//super.enemyWaves.add(enemyWave1);
		
		Todder todder2 = new Todder(baseTimer);
		todder2.position.y = 1.8f;
		todder2.position.x = -1.4f;
		todder2.properPosition.y = 0.5f;
		todder2.properPosition.x = -0.7f;
		todder2.attacks.add(new SimpleAttack());
		todder2.attacks.get(0).patterns = new BulletPattern[]{
				new RandomPattern(sceneBullets, 0.5f, 0.3f, baseTimer, 0.8f),
				new AimPattern(sceneBullets, 0.4f, 0.3f, baseTimer, 1.6f)
		};
		Todder todder3 = new Todder(baseTimer);
		todder3.position.y = 1.8f;
		todder3.position.x = 1.4f;
		todder3.properPosition.y = 0.5f;
		todder3.properPosition.x = 0.7f;
		todder3.attacks.add(new SimpleAttack());
		todder3.attacks.get(0).patterns = new BulletPattern[]{
				new RandomPattern(sceneBullets, 0.5f, 0.3f, baseTimer, 0.8f),
				new CirclePattern(sceneBullets, 0.4f, 0.4f, baseTimer, 1.5f, 10)
		};
		List<Enemy> enemyWave2 = new ArrayList<Enemy>();
		enemyWave2.add(todder2);
		enemyWave2.add(todder3);
		//super.enemyWaves.add(enemyWave2);
		
		Todder todder4 = new Todder(baseTimer);
		todder4.position.y = 1.8f;
		todder4.position.x = 0.5f;
		todder4.properPosition.y = 0.5f;
		todder4.properPosition.x = 0f;
		todder4.attacks.add(new SimpleAttack());
		todder4.attacks.get(0).patterns = new BulletPattern[]{
				new HomingRandomPattern(sceneBullets, 0.75f, 0.35f, baseTimer, 0.6f, 85f)
		};
		List<Enemy> enemyWave3 = new ArrayList<Enemy>();
		enemyWave3.add(todder4);
		//super.enemyWaves.add(enemyWave3);
		
		Todder todder5 = new Todder(baseTimer);
		todder5.position.y = 1.8f;
		todder5.position.x = -1.4f;
		todder5.properPosition.y = 0.5f;
		todder5.properPosition.x = 0.7f;
		todder5.attacks.add(new SimpleAttack());
		todder5.attacks.get(0).patterns = new BulletPattern[]{
				new AimHalfCirclePattern(sceneBullets, 0.7f, 0.35f, baseTimer, 1f, 4),
				new DelayAimPattern(sceneBullets, 2f, 0.35f, baseTimer, 2f, 1.003f)
		};
		Todder todder6 = new Todder(baseTimer);
		todder6.position.y = 1.8f;
		todder6.position.x = 1.4f;
		todder6.properPosition.y = 0.5f;
		todder6.properPosition.x = -0.7f;
		todder6.attacks.add(new SimpleAttack());
		todder6.attacks.get(0).patterns = new BulletPattern[]{
				new AimHalfCirclePattern(sceneBullets, 0.7f, 0.35f, baseTimer, 0.8f, 5)
		};
		List<Enemy> enemyWave4 = new ArrayList<Enemy>();
		enemyWave4.add(todder5);
		enemyWave4.add(todder6);
		//super.enemyWaves.add(enemyWave4);
		
		Todder todder7 = new Todder(baseTimer);
		todder7.position.y = 1.3f;
		todder7.position.x = 1.8f;
		todder7.properPosition.y = 0.65f;
		todder7.properPosition.x = 0f;
		todder7.attacks.add(new SimpleAttack());
		todder7.attacks.get(0).patterns = new BulletPattern[]{
				new HomingRandomPattern(sceneBullets, 0.6f, 0.3f, baseTimer, 1f, 60)
		};
		Todder todder8 = new Todder(baseTimer);
		todder8.position.y = -1f;
		todder8.position.x = -4f;
		todder8.properPosition.y = 0.5f;
		todder8.properPosition.x = -0.8f;
		todder8.attacks.add(new SimpleAttack());
		todder8.attacks.get(0).patterns = new BulletPattern[]{
				new StaticAimStreamPattern(sceneBullets, 1.5f, 0.2f, baseTimer, 0.8f, 0.04f, 5)
		};
		Todder todder9 = new Todder(baseTimer);
		todder9.position.y = 0f;
		todder9.position.x = 4.5f;
		todder9.properPosition.y = 0.5f;
		todder9.properPosition.x = 0.8f;
		todder9.attacks.add(new SimpleAttack());
		todder9.attacks.get(0).patterns = new BulletPattern[]{
				new AimHalfCirclePattern(sceneBullets, 1.2f, 0.3f, baseTimer, 1f, 3)
		};
		List<Enemy> enemyWave5 = new ArrayList<Enemy>();
		enemyWave5.add(todder7);
		enemyWave5.add(todder8);
		enemyWave5.add(todder9);
		//super.enemyWaves.add(enemyWave5);
		
		Todder todder10 = new Todder(baseTimer);
		todder10.position.y = 2f;
		todder10.position.x = 0f;
		todder10.properPosition.y = 0.65f;
		todder10.properPosition.x = 0f;
		todder10.attacks.add(new SimpleAttack());
		todder10.attacks.get(0).patterns = new BulletPattern[]{
				new CircleBeamPattern(sceneBullets, 1f, 0.4f, baseTimer, 0.05f, 27f),
				new CircleBeamPattern(sceneBullets, 1f, 0.2f, baseTimer, 0.025f, -54f)
		};
		List<Enemy> enemyWave6 = new ArrayList<Enemy>();
		enemyWave6.add(todder10);
		//super.enemyWaves.add(enemyWave6);
		
		LuckTodder cloverTodder = new LuckTodder(baseTimer, "CloverTodder", ResourceStorage.getTexture("luckTodderCloverTex"));
		cloverTodder.position.y = 2f;
		cloverTodder.position.x = 0f;
		cloverTodder.properPosition.y = 0.55f;
		cloverTodder.properPosition.x = -0.75f;
		cloverTodder.attacks.add(new SimpleAttack());
		LuckTodder spadeTodder = new LuckTodder(baseTimer, "SpadeTodder", ResourceStorage.getTexture("luckTodderSpadeTex"));
		spadeTodder.position.y = 2.5f;
		spadeTodder.position.x = 0.5f;
		spadeTodder.properPosition.y = 0.55f;
		spadeTodder.properPosition.x = 0.75f;
		spadeTodder.attacks.add(new SimpleAttack());
		random = Main.getRandom().nextInt(5);
		switch(random){
		case 0:
			cloverTodder.attacks.get(0).patterns = new BulletPattern[]{
					new AimPattern(sceneBullets, 1.3f, 0.3f, baseTimer, 0.5f)
			};
			spadeTodder.attacks.get(0).patterns = new BulletPattern[]{
					new DelayAimPattern(sceneBullets, 1.3f, 0.3f, baseTimer, 0.5f, 1.005f)
			};
			break;
		case 1:
			cloverTodder.attacks.get(0).patterns = new BulletPattern[]{
					new AimHalfCirclePattern(sceneBullets, 0.8f, 0.4f, baseTimer, 1f, 7)
			};
			spadeTodder.attacks.get(0).patterns = new BulletPattern[]{
					new CirclePattern(sceneBullets, 1f, 0.4f, baseTimer, 0.6f, 15)
			};
			break;
		case 2:
			cloverTodder.attacks.get(0).patterns = new BulletPattern[]{
					new HomingRandomPattern(sceneBullets, 1f, 0.225f, baseTimer, 0.8f, 100)
			};
			spadeTodder.attacks.get(0).patterns = new BulletPattern[]{
					new RandomPattern(sceneBullets, 0.75f, 0.3f, baseTimer, 0.2f)
			};
			break;
		case 3:
			cloverTodder.attacks.get(0).patterns = new BulletPattern[]{
					new RandomPattern(sceneBullets, 1f, 0.3f, baseTimer, 0.1f)
			};
			spadeTodder.attacks.get(0).patterns = new BulletPattern[]{
					new DelayAimPattern(sceneBullets, 2.5f, 1.2f, baseTimer, 4f, 1.01f)
			};
			break;
		case 4:
			cloverTodder.attacks.get(0).patterns = new BulletPattern[]{
					new StaticAimStreamPattern(sceneBullets, 1.5f, 0.3f, baseTimer, 0.5f, 0.05f, 5)
			};
			spadeTodder.attacks.get(0).patterns = new BulletPattern[]{
					new AimHalfCirclePattern(sceneBullets, 1.5f, 0.3f, baseTimer, 0.5f, 2)
			};
			break;
		}
		List<Enemy> enemyWave7 = new ArrayList<Enemy>();
		enemyWave7.add(cloverTodder);
		enemyWave7.add(spadeTodder);
		super.enemyWaves.add(enemyWave7);
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
		ResourceStorage.disposeTexture("luckTodderCloverTex");
		ResourceStorage.disposeTexture("luckTodderSpadeTex");
		ResourceStorage.disposeTexture("yargKradTex");
	}
}