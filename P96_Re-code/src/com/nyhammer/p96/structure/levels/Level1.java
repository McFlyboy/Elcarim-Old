package com.nyhammer.p96.structure.levels;

import java.util.ArrayList;
import java.util.List;

import com.nyhammer.p96.Game;
import com.nyhammer.p96.audio.Music;
import com.nyhammer.p96.entities.Enemy;
import com.nyhammer.p96.entities.LuckTodder;
import com.nyhammer.p96.entities.ModelEntity;
import com.nyhammer.p96.entities.TextField;
import com.nyhammer.p96.entities.Todder;
import com.nyhammer.p96.entities.YargKrad;
import com.nyhammer.p96.graphics.Render;
import com.nyhammer.p96.graphics.Texture;
import com.nyhammer.p96.structure.BulletPattern;
import com.nyhammer.p96.structure.Level;
import com.nyhammer.p96.structure.ResourceStorage;
import com.nyhammer.p96.structure.attacks.RandomJumpAttack;
import com.nyhammer.p96.structure.attacks.SimpleAttack;
import com.nyhammer.p96.structure.patterns.AimHalfCirclePattern;
import com.nyhammer.p96.structure.patterns.AimPattern;
import com.nyhammer.p96.structure.patterns.CircleBeamPattern;
import com.nyhammer.p96.structure.patterns.CirclePattern;
import com.nyhammer.p96.structure.patterns.DelayAimPattern;
import com.nyhammer.p96.structure.patterns.HomingRandomPattern;
import com.nyhammer.p96.structure.patterns.RandomPattern;
import com.nyhammer.p96.structure.patterns.SpinPattern;
import com.nyhammer.p96.structure.patterns.StaticAimStreamPattern;
import com.nyhammer.p96.ui.GameWindow;
import com.nyhammer.p96.util.Color3f;
import com.nyhammer.p96.util.math.vector.Vector2f;
import com.nyhammer.p96.util.timing.TargetTimer;
import com.nyhammer.p96.util.timing.Timer;

public class Level1 extends Level {
	private TextField enemyInfo;
	private TextField enemyInfoTitle;
	private TextField timerText;
	private TextField timerTextInfo;
	private int random;
	private boolean ballInactive;
	private boolean bossActive;
	private int bossWaveIndex;
	private TargetTimer attackTimer;
	public Level1(Timer baseTimer) {
		super(baseTimer);
	}
	public int getRandomAttackIndex() {
		return random;
	}
	public boolean shouldBallBeInactive() {
		return ballInactive;
	}
	@Override
	protected void init(Timer baseTimer) {
		ballInactive = false;
		bossActive = false;
		bossWaveIndex = 7;
		attackTimer = new TargetTimer(baseTimer, 30.0);
		Texture bgTex = new Texture("background/background.png");
		ResourceStorage.add("bgTex", bgTex);
		super.backgrounds.add(new ModelEntity(ResourceStorage.getModel("square"), bgTex, new Vector2f(), new Vector2f(GameWindow.ASPECT_RATIO, 1f), 0f));
		Music bgm = new Music(new String[] {
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
		Music bossBGM = new Music(new String[] {
				"P96_2_intro.ogg",
				"P96_2_main.ogg",
				"P96_2_Miracle_intro.ogg",
				"P96_2_Miracle_main.ogg"
		});
		bossBGM.setPartLooping(0, false);
		bossBGM.setPartLooping(2, false);
		bossBGM.setNextPart(1, 4);
		ResourceStorage.add("bossBGM", bossBGM);
		super.bgms.add(bossBGM);
		Texture todderTex = new Texture("enemy/todder.png");
		ResourceStorage.add("todderTex", todderTex);
		Texture luckTodderCloverTex = new Texture("enemy/luckTodder_Clover.png");
		ResourceStorage.add("luckTodderCloverTex", luckTodderCloverTex);
		Texture luckTodderSpadeTex = new Texture("enemy/luckTodder_Spade.png");
		ResourceStorage.add("luckTodderSpadeTex", luckTodderSpadeTex);
		Texture yargKradTex = new Texture("enemy/boss/yargKrad.png");
		ResourceStorage.add("yargKradTex", yargKradTex);
		enemyInfo = new TextField();
		enemyInfoTitle = new TextField();
		enemyInfoTitle.scale.x = 0.00175f;
		enemyInfoTitle.scale.y = 0.00175f;
		timerText = new TextField();
		timerTextInfo = new TextField();
		timerTextInfo.setText("-Time remaining-");
		timerTextInfo.scale.x = 0.0018f;
		timerTextInfo.scale.y = 0.0018f;
		timerTextInfo.position.y = timerText.position.y + (timerText.getHeight() + timerTextInfo.getHeight()) / 2f;
	}
	@Override
	protected void addWaves(Timer baseTimer) {
		Todder todder1 = new Todder(baseTimer);
		todder1.position.y = 1.7f;
		todder1.position.x = 1.3f;
		todder1.properPosition.y = 0.5f;
		todder1.properPosition.x = 0.3f;
		todder1.attacks.add(new SimpleAttack());
		todder1.attacks.get(0).patterns = new BulletPattern[] {
				new RandomPattern(bullets, 0.5f, 0.3f, baseTimer, 0.5f, false)
		};
		List<Enemy> enemyWave1 = new ArrayList<Enemy>();
		enemyWave1.add(todder1);
		super.enemyWaves.add(enemyWave1);
		
		Todder todder2 = new Todder(baseTimer);
		todder2.position.y = 1.8f;
		todder2.position.x = -1.4f;
		todder2.properPosition.y = 0.5f;
		todder2.properPosition.x = -0.7f;
		todder2.attacks.add(new SimpleAttack());
		todder2.attacks.get(0).patterns = new BulletPattern[] {
				new RandomPattern(bullets, 0.5f, 0.3f, baseTimer, 0.8f, false),
				new AimPattern(bullets, 0.4f, 0.3f, baseTimer, 1.6f)
		};
		Todder todder3 = new Todder(baseTimer);
		todder3.position.y = 1.8f;
		todder3.position.x = 1.4f;
		todder3.properPosition.y = 0.5f;
		todder3.properPosition.x = 0.7f;
		todder3.attacks.add(new SimpleAttack());
		todder3.attacks.get(0).patterns = new BulletPattern[] {
				new RandomPattern(bullets, 0.5f, 0.3f, baseTimer, 0.8f, false),
				new CirclePattern(bullets, 0.4f, 0.4f, baseTimer, 1.5f, 10)
		};
		List<Enemy> enemyWave2 = new ArrayList<Enemy>();
		enemyWave2.add(todder2);
		enemyWave2.add(todder3);
		super.enemyWaves.add(enemyWave2);
		
		Todder todder4 = new Todder(baseTimer);
		todder4.position.y = 1.8f;
		todder4.position.x = 0.5f;
		todder4.properPosition.y = 0.5f;
		todder4.properPosition.x = 0f;
		todder4.attacks.add(new SimpleAttack());
		todder4.attacks.get(0).patterns = new BulletPattern[] {
				new HomingRandomPattern(bullets, 0.75f, 0.35f, baseTimer, 0.6f, 85f)
		};
		List<Enemy> enemyWave3 = new ArrayList<Enemy>();
		enemyWave3.add(todder4);
		super.enemyWaves.add(enemyWave3);
		
		Todder todder5 = new Todder(baseTimer);
		todder5.position.y = 1.8f;
		todder5.position.x = -1.4f;
		todder5.properPosition.y = 0.5f;
		todder5.properPosition.x = 0.7f;
		todder5.attacks.add(new SimpleAttack());
		todder5.attacks.get(0).patterns = new BulletPattern[] {
				new AimHalfCirclePattern(bullets, 0.7f, 0.35f, baseTimer, 1f, 4),
				new DelayAimPattern(bullets, 2f, 0.35f, baseTimer, 2f, 1.003f)
		};
		Todder todder6 = new Todder(baseTimer);
		todder6.position.y = 1.8f;
		todder6.position.x = 1.4f;
		todder6.properPosition.y = 0.5f;
		todder6.properPosition.x = -0.7f;
		todder6.attacks.add(new SimpleAttack());
		todder6.attacks.get(0).patterns = new BulletPattern[] {
				new AimHalfCirclePattern(bullets, 0.7f, 0.35f, baseTimer, 0.8f, 5)
		};
		List<Enemy> enemyWave4 = new ArrayList<Enemy>();
		enemyWave4.add(todder5);
		enemyWave4.add(todder6);
		super.enemyWaves.add(enemyWave4);
		
		Todder todder7 = new Todder(baseTimer);
		todder7.position.y = 1.3f;
		todder7.position.x = 1.8f;
		todder7.properPosition.y = 0.65f;
		todder7.properPosition.x = 0f;
		todder7.attacks.add(new SimpleAttack());
		todder7.attacks.get(0).patterns = new BulletPattern[] {
				new HomingRandomPattern(bullets, 0.6f, 0.3f, baseTimer, 1f, 60)
		};
		Todder todder8 = new Todder(baseTimer);
		todder8.position.y = -1f;
		todder8.position.x = -4f;
		todder8.properPosition.y = 0.5f;
		todder8.properPosition.x = -0.8f;
		todder8.attacks.add(new SimpleAttack());
		todder8.attacks.get(0).patterns = new BulletPattern[] {
				new StaticAimStreamPattern(bullets, 1.5f, 0.2f, baseTimer, 0.8f, 0.04f, 5)
		};
		Todder todder9 = new Todder(baseTimer);
		todder9.position.y = 0f;
		todder9.position.x = 4.5f;
		todder9.properPosition.y = 0.5f;
		todder9.properPosition.x = 0.8f;
		todder9.attacks.add(new SimpleAttack());
		todder9.attacks.get(0).patterns = new BulletPattern[] {
				new AimHalfCirclePattern(bullets, 1.2f, 0.3f, baseTimer, 1f, 3)
		};
		List<Enemy> enemyWave5 = new ArrayList<Enemy>();
		enemyWave5.add(todder7);
		enemyWave5.add(todder8);
		enemyWave5.add(todder9);
		super.enemyWaves.add(enemyWave5);
		
		Todder todder10 = new Todder(baseTimer);
		todder10.position.y = 2f;
		todder10.position.x = 0f;
		todder10.properPosition.y = 0.65f;
		todder10.properPosition.x = 0f;
		todder10.attacks.add(new SimpleAttack());
		todder10.attacks.get(0).patterns = new BulletPattern[] {
				new CircleBeamPattern(bullets, 1f, 0.4f, baseTimer, 0.05f, 27f),
				new CircleBeamPattern(bullets, 1f, 0.2f, baseTimer, 0.025f, -54f)
		};
		List<Enemy> enemyWave6 = new ArrayList<Enemy>();
		enemyWave6.add(todder10);
		super.enemyWaves.add(enemyWave6);
		
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
		random = Game.getRandom().nextInt(5);
		switch(random) {
		case 0:
			cloverTodder.attacks.get(0).patterns = new BulletPattern[] {
					new AimPattern(bullets, 1.3f, 0.3f, baseTimer, 0.5f)
			};
			spadeTodder.attacks.get(0).patterns = new BulletPattern[] {
					new DelayAimPattern(bullets, 1.3f, 0.3f, baseTimer, 0.5f, 1.005f)
			};
			break;
		case 1:
			cloverTodder.attacks.get(0).patterns = new BulletPattern[] {
					new AimHalfCirclePattern(bullets, 0.8f, 0.4f, baseTimer, 1f, 7)
			};
			spadeTodder.attacks.get(0).patterns = new BulletPattern[] {
					new CirclePattern(bullets, 1f, 0.4f, baseTimer, 0.6f, 15)
			};
			break;
		case 2:
			cloverTodder.attacks.get(0).patterns = new BulletPattern[] {
					new HomingRandomPattern(bullets, 1f, 0.225f, baseTimer, 0.8f, 100)
			};
			spadeTodder.attacks.get(0).patterns = new BulletPattern[] {
					new RandomPattern(bullets, 0.75f, 0.3f, baseTimer, 0.2f, false)
			};
			break;
		case 3:
			cloverTodder.attacks.get(0).patterns = new BulletPattern[] {
					new RandomPattern(bullets, 1f, 0.3f, baseTimer, 0.1f, false)
			};
			spadeTodder.attacks.get(0).patterns = new BulletPattern[] {
					new DelayAimPattern(bullets, 2.5f, 1.2f, baseTimer, 4f, 1.01f)
			};
			break;
		case 4:
			cloverTodder.attacks.get(0).patterns = new BulletPattern[] {
					new StaticAimStreamPattern(bullets, 1.5f, 0.3f, baseTimer, 0.5f, 0.05f, 5)
			};
			spadeTodder.attacks.get(0).patterns = new BulletPattern[] {
					new AimHalfCirclePattern(bullets, 1.5f, 0.3f, baseTimer, 0.5f, 2)
			};
			break;
		}
		List<Enemy> enemyWave7 = new ArrayList<Enemy>();
		enemyWave7.add(cloverTodder);
		enemyWave7.add(spadeTodder);
		super.enemyWaves.add(enemyWave7);
		
		YargKrad yargKrad = new YargKrad(baseTimer);
		yargKrad.position.y = 2f;
		yargKrad.position.x = 0f;
		yargKrad.properPosition.y = 0.65f;
		yargKrad.properPosition.x = 0f;
		yargKrad.attacks.add(new SimpleAttack());
		yargKrad.attacks.get(0).patterns = new BulletPattern[] {
				new CirclePattern(bullets, 1.2f, 0.8f, baseTimer, 2f, 15),
				new CircleBeamPattern(bullets, 1f, 0.2f, baseTimer, 0.05f, -54f)
		};
		yargKrad.attacks.add(new RandomJumpAttack(bullets, baseTimer));
		yargKrad.attacks.add(new SimpleAttack());
		yargKrad.attacks.get(2).patterns = new BulletPattern[] {
				new RandomPattern(bullets, 0.2f, 0.2f, baseTimer, 0.25f, true),
				new StaticAimStreamPattern(bullets, 1.2f, 0.25f, baseTimer, 1f, 0.05f, 60)
		};
		yargKrad.attacks.add(new SimpleAttack());
		yargKrad.attacks.get(3).patterns = new BulletPattern[] {
				new HomingRandomPattern(bullets, 1.2f, 0.25f, baseTimer, 0.4f, 80)
		};
		yargKrad.attacks.add(new SimpleAttack());
		yargKrad.attacks.get(4).patterns = new BulletPattern[] {
				new SpinPattern(bullets, 1f, 0.25f, baseTimer, 0.125f),
				new CircleBeamPattern(bullets, 0.5f, 0.25f, baseTimer, 0.1f, -26)
		};
		List<Enemy> bossWave = new ArrayList<Enemy>();
		bossWave.add(yargKrad);
		super.enemyWaves.add(bossWave);
	}
	@Override
	protected void updateSpecifics() {
		if(super.bgmIndex == 0 && super.waveIndex == bossWaveIndex) {
			super.getCurrentMusic().stop();
			super.bgmIndex++;
			super.clearBullets();
		}
		else if(!isCompleted() && !bossActive) {
			if(super.bgmIndex == 1 && getCurrentWave().get(0).position.y < 1f) {
				super.getCurrentMusic().play();
				bossActive = true;
			}
		}
		else if(getCurrentWave().get(0).state == 1) {
			if(!ballInactive){
				super.clearBullets();
			}
			ballInactive = true;
			attackTimer.start();
			if(attackTimer.targetReached()) {
				attackTimer.reset();
				super.clearBullets();
				getCurrentWave().get(0).state = 2;
			}
		}
		else if(getCurrentWave().get(0).state == 3) {
			ballInactive = false;
		}
	}
	@Override
	public void renderSpesifics() {
		if(!super.isCompleted()) {
			if(super.waveIndex == bossWaveIndex) {
				enemyInfo.setText(super.getBossString());
				enemyInfoTitle.setText("-Boss-");
			}
			else {
				enemyInfo.setText(super.getEnemyCountString());
				enemyInfoTitle.setText("-Enemies-");
			}
			enemyInfoTitle.position.y = 1f - enemyInfoTitle.getHeight() / 2f;
			enemyInfoTitle.position.x = -GameWindow.ASPECT_RATIO + enemyInfoTitle.getWidth() / 2f;
			Render.addToQueue(enemyInfoTitle);
			enemyInfo.position.y = 1f - enemyInfo.getHeight() / 2f - enemyInfoTitle.getHeight();
			enemyInfo.position.x = -GameWindow.ASPECT_RATIO + enemyInfo.getWidth() / 2f;
			Render.addToQueue(enemyInfo);
			if(!isCompleted()) {
				if(super.waveIndex == bossWaveIndex && getCurrentWave().get(0).state == 1) {
					double attackTime = 30.0 - attackTimer.getTime();
					if(attackTime < 3.0) {
						Color3f color = new Color3f(1f, 0f, 0f);
						timerText.mainColor = color;
						timerTextInfo.setColors(color);
					}
					else if(attackTime < 5.0) {
						Color3f color = new Color3f(1f, 0.5f, 0f);
						timerText.mainColor = color;
						timerTextInfo.setColors(color);
					}
					else if(attackTime < 10.0) {
						Color3f color = new Color3f(1f, 1f, 0f);
						timerText.mainColor = color;
						timerTextInfo.setColors(color);
					}
					timerText.setText(String.format("%.2f", attackTime));
					Render.addToQueue(timerText);
					Render.addToQueue(timerTextInfo);
				}
			}
		}
	}
	@Override
	public void dispose() {
		ResourceStorage.disposeTexture("bgTex");
		ResourceStorage.disposeMusic("bgm");
		ResourceStorage.disposeMusic("bossBGM");
		ResourceStorage.disposeTexture("todderTex");
		ResourceStorage.disposeTexture("luckTodderCloverTex");
		ResourceStorage.disposeTexture("luckTodderSpadeTex");
		ResourceStorage.disposeTexture("yargKradTex");
	}
}
