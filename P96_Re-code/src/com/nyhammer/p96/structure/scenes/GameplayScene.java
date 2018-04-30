package com.nyhammer.p96.structure.scenes;

import java.util.ArrayList;
import java.util.List;

import com.nyhammer.p96.audio.Music;
import com.nyhammer.p96.audio.Sound;
import com.nyhammer.p96.entities.Ball;
import com.nyhammer.p96.entities.Bullet;
import com.nyhammer.p96.entities.Enemy;
import com.nyhammer.p96.entities.Player;
import com.nyhammer.p96.entities.Shot;
import com.nyhammer.p96.entities.TextField;
import com.nyhammer.p96.graphics.Render;
import com.nyhammer.p96.graphics.Texture;
import com.nyhammer.p96.structure.ControlScheme;
import com.nyhammer.p96.structure.Level;
import com.nyhammer.p96.structure.ResourceStorage;
import com.nyhammer.p96.structure.Scene;
import com.nyhammer.p96.structure.controlSchemes.GameplayControls;
import com.nyhammer.p96.structure.levels.Level1;
import com.nyhammer.p96.ui.GameWindow;
import com.nyhammer.p96.util.math.collision.CC;
import com.nyhammer.p96.util.math.vector.Vector2f;
import com.nyhammer.p96.util.timing.DeltaTimer;
import com.nyhammer.p96.util.timing.Timer;

public class GameplayScene extends Scene {
	private GameplayControls controls;
	private Player player;
	private Ball ball;
	private int score;
	private TextField scoreText;
	private TextField livesText;
	private TextField miraclesText;
	private List<Shot> shots;
	private Level1 level1;
	private Timer normalTimer;
	private DeltaTimer normalDeltaTimer;
	private boolean gameOver;
	private int killStreak;
	private List<Bullet> bullets;
	private boolean gameCompleted;
	public GameplayScene(Timer timer) {
		super(timer);
		normalTimer = new Timer(this.timer, true);
		normalDeltaTimer = new DeltaTimer(normalTimer);
		controls = new GameplayControls();
		Sound deathSound = new Sound("player_death.ogg");
		ResourceStorage.add("deathSound", deathSound);
		Sound hitSound = new Sound("enemy_hit.ogg");
		ResourceStorage.add("hitSound", hitSound);
		Sound multiKillSound = new Sound("multi-kill.ogg");
		ResourceStorage.add("multiKillSound", multiKillSound);
		Sound shotSound = new Sound("shot.ogg");
		ResourceStorage.add("shotSound", shotSound);
		Sound miracleSound = new Sound("miracle.ogg");
		ResourceStorage.add("miracleSound", miracleSound);
		Texture playerTex = new Texture("char/player.png", 3, 3);
		player = new Player(this.timer);
		player.model = ResourceStorage.getModel("square");
		player.texture = playerTex;
		ResourceStorage.add("playerTex", playerTex);
		Texture bulletGreenTex = new Texture("bullet/bulletGreen.png");
		ResourceStorage.add("bulletGreenTex", bulletGreenTex);
		Texture bulletPurpleTex = new Texture("bullet/bulletPurple.png");
		ResourceStorage.add("bulletPurpleTex", bulletPurpleTex);
		Texture bulletRedTex = new Texture("bullet/bulletRed.png");
		ResourceStorage.add("bulletRedTex", bulletRedTex);
		Texture bulletYellowTex = new Texture("bullet/bulletYellow.png");
		ResourceStorage.add("bulletYellowTex", bulletYellowTex);
		Bullet.initBulletSystem(this.timer);
		ball = new Ball(this.timer);
		ball.model = ResourceStorage.getModel("square");
		Texture ballTex = new Texture("ball/ball.png");
		ball.texture = ballTex;
		ResourceStorage.add("ballTex", ballTex);
		scoreText = new TextField();
		scoreText.mainColor.blue = 0f;
		livesText = new TextField();
		livesText.mainColor.green = 0.5f;
		livesText.mainColor.blue = 0.5f;
		miraclesText = new TextField();
		miraclesText.mainColor.red = 0.3f;
		miraclesText.mainColor.green = 0.8f;
		shots = new ArrayList<Shot>();
		Texture shotTex = new Texture("shot/shot.png");
		ResourceStorage.add("shotTex", shotTex);
		level1 = new Level1(normalTimer);
		bullets = level1.getBullets();
		score = level1.getRandomAttackIndex();
		killStreak = 0;
		gameCompleted = false;
	}
	public boolean isGameCompleted() {
		return gameCompleted;
	}
	public boolean isGameOver() {
		return gameOver;
	}
	public Level getCurrentLevel() {
		return level1;
	}
	@Override
	protected void startSpecifics() {
		level1.getCurrentMusic().play();
		brightness = 1f;
	}
	@Override
	protected void updateSpecifics(float deltaTime) {
		float normaldeltaTime = (float)normalDeltaTimer.getTime();
		float normalTime = (float)normalTimer.getTime();
		level1.update();
		if(level1.isCompleted()) {
			level1.clearBullets();
			if(super.brightness <= 0f) {
				gameCompleted = true;
			}
			else {
				super.brightness -= 0.25f * deltaTime;
				Music currentMusic = level1.getCurrentMusic();
				currentMusic.setVolume(currentMusic.getVolume() - 0.25f * deltaTime);
			}
		}
		if(level1.shouldBallBeInactive() && ball.position.y < 0.4f) {
			ball.inactive = true;
			ball.monochrome = true;
			ball.direction.x = 0f;
			ball.direction.y = 0f;
		}
		else {
			ball.inactive = false;
			ball.monochrome = false;
		}
		if(player.alive) {
			updateControls();
		}
		gameOver = player.update(deltaTime);
		ball.update(normaldeltaTime, normalTimer);
		if(ball.shouldKillsReset()) {
			killStreak = 0;
		}
		if(ball.miracleTimer.targetReached()) {
			ball.deactivateMiracle();
			normalTimer.resume();
			Music bgm = level1.getCurrentMusic();
			bgm.setActivePart(bgm.getActivePart() - 2, true);
		}
		if(player.hitting) {
			if(CC.checkCollision(player.hitCC, ball.cc)) {
				if(!ball.inactive) {
					if(!ball.hit) {
						ball.hit = true;
						if(player.direction.x == 0f) {
							ball.direction = new Vector2f(player.lastXDirection / Math.abs(player.lastXDirection), 2f).getNormalize().getMul(player.jumping ? 15f : 12f);
							score += 20;
						}
						else {
							ball.direction = new Vector2f(player.direction.x / Math.abs(player.direction.x), 2f).getNormalize().getMul(15f);
							score += 30;
						}
					}
				}
			}
			else {
				ball.hit = false;
			}
		}
		else {
			if(CC.checkCollision(player.cc, ball.cc)) {
				player.die();
			}
		}
		if(!level1.isCompleted()) {
			List<Enemy> enemies = level1.getCurrentWave();
			for(int i = 0; i < enemies.size(); i++) {
				Enemy enemy = enemies.get(i);
				if(!ball.miracleActive) {
					enemy.update(normalTime, normaldeltaTime, player.position);
				}
				boolean collision = CC.checkCollision(enemy.cc, ball.cc);
				if(collision) {
					if(!enemy.hit) {
						enemy.hit = true;
						enemy.colorActive = true;
						ball.direction.x *= -0.6f;
						ball.direction.y *= 0.6f;
						score += 1000 * Math.pow(2, killStreak);
						if(killStreak > 0) {
							ResourceStorage.getSound("multiKillSound").play();
						}
						killStreak++;
						ResourceStorage.getSound("hitSound").play();
						enemy.hitTimer.resume();
					}
				}
				if(enemy.hitTimer.targetReached() && !collision) {
					enemy.lives--;
					if(enemy.lives >= 0) {
						enemy.colorActive = false;
						enemy.hit = false;
						enemy.hitTimer.reset();
					}
					else {
						enemies.remove(i);
						i--;
						level1.subtractEnemyCount(enemy.name);
					}
				}
			}
		}
		for(int i = 0; i < bullets.size(); i++) {
			Bullet bullet = bullets.get(i);
			bullet.update();
			if(!ball.miracleActive) {
				bullet.updateMovement(normaldeltaTime);
			}
			else {
				bullet.position.add(ball.position.getSub(bullet.position).getNormalize().getMul(deltaTime * 0.3f));
			}
			if(Math.abs(bullet.position.x) > GameWindow.ASPECT_RATIO + bullet.scale.x || Math.abs(bullet.position.y) > 1f + bullet.scale.y) {
				bullet.intact = false;
			}
			if(CC.checkCollision(bullet.cc, player.cc)) {
				player.die();
				bullet.hp = 0;
				bullet.color.green = 0f;
				bullet.color.blue = 0f;
			}
			if(CC.checkCollision(bullet.cc, ball.cc)) {
				bullet.intact = false;
				if(ball.miracleActive) {
					score += 150;
				}
				else {
					score += 50;
				}
			}
			if(!bullet.intact) {
				bullets.remove(i);
				i--;
			}
		}
		for(int i = 0; i < shots.size(); i++) {
			Shot shot = shots.get(i);
			shot.update(deltaTime);
			if(CC.checkCollision(shot.cc, ball.cc)) {
				if(!ball.miracleActive) {
					if(!ball.inactive) {
						Vector2f shotAngle = ball.position.getSub(shot.position).getNormalize();
						ball.direction.add(shotAngle.getMul(12f * shotAngle.y));
						score += 30;
					}
				}
				else {
					score += 100;
				}
				shot.intact = false;
			}
			for(int j = 0; j < bullets.size(); j++) {
				Bullet bullet = bullets.get(j);
				if(CC.checkCollision(shot.cc, bullet.cc)) {
					shot.intact = false;
					if(bullet.hp > 0) {
						bullet.hp--;
						score += 10;
					}
				}
				if(!bullet.intact) {
					bullets.remove(j);
					j--;
				}
			}
			if(!shot.intact) {
				shots.remove(i);
				i--;
			}
		}
		double miracleTime = ball.miracleTimer.getTime();
		double progress = miracleTime / ball.miracleTimer.getTargetTime();
		this.position.x = (float)(Math.sin(miracleTime * 1000.0 / 15.0) * 0.01f * progress);
		this.position.y = (float)(Math.sin(miracleTime * 1000.0 / 25.0) * 0.025f * progress);
	}
	@Override
	protected void renderSpecifics() {
		level1.renderBackground();
		Render.addToQueue(player);
		level1.renderEnemies();
		Render.addToQueue(ball);
		for(Bullet bullet : bullets) {
			Render.addToQueue(bullet);
		}
		for(Shot shot : shots) {
			Render.addToQueue(shot);
		}
		level1.renderSpesifics();
		scoreText.setText(String.format("Score: %08d", score));
		scoreText.position.x = GameWindow.ASPECT_RATIO - scoreText.getWidth() / 2f;
		scoreText.position.y = 1f - scoreText.getHeight() / 2f;
		Render.addToQueue(scoreText);
		livesText.setText("Lives: " + player.lives);
		livesText.position.x = GameWindow.ASPECT_RATIO - livesText.getWidth() / 2f;
		livesText.position.y = 1f - scoreText.getHeight() / 2f - livesText.getHeight();
		Render.addToQueue(livesText);
		if(ball.inactive) {
			miraclesText.monochrome = true;
		}
		else {
			miraclesText.monochrome = false;
		}
		miraclesText.setText("Miracle: " + player.miracles);
		miraclesText.position.x = GameWindow.ASPECT_RATIO - miraclesText.getWidth() / 2f;
		miraclesText.position.y = 1f - scoreText.getHeight() / 2f - livesText.getHeight() - miraclesText.getHeight();
		Render.addToQueue(miraclesText);
	}
	@Override
	protected void stopSpecifics() {
		level1.getCurrentMusic().pause();
		brightness = 0.5f;
	}
	@Override
	protected void disposeSpecifics() {
		ResourceStorage.disposeTexture("playerTex");
		ResourceStorage.disposeTexture("bulletGreenTex");
		ResourceStorage.disposeTexture("bulletPurpleTex");
		ResourceStorage.disposeTexture("bulletRedTex");
		ResourceStorage.disposeTexture("bulletYellowTex");
		ResourceStorage.disposeTexture("ballTex");
		ResourceStorage.disposeTexture("shotTex");
		ResourceStorage.disposeSound("deathSound");
		ResourceStorage.disposeSound("hitSound");
		ResourceStorage.disposeSound("multiKillSound");
		ResourceStorage.disposeSound("shotSound");
		ResourceStorage.disposeSound("miracleSound");
	}
	private void updateControls() {
		float walkDistance = 0;
		if(ControlScheme.getActiveInput() == ControlScheme.ActiveInput.ACTIVE_GAMEPAD) {
			walkDistance = controls.getAxisState(controls.getMoveAxisX());
		}
		else {
			if(controls.isDown(controls.getMoveLeft())) {
				walkDistance += -1f;
			}
			if(controls.isDown(controls.getMoveRight())) {
				walkDistance += 1f;
			}
		}
		if(controls.isPressed(controls.getJump())) {
			player.jump();
		}
		player.holdJumping = controls.isDown(controls.getJump());
		if(controls.isPressed(controls.getHit())) {
			player.hit();
		}
		if(controls.isPressed(controls.getShootLeft()) || controls.isPressed(controls.getShootRight())) {
			player.shoot(shots);
		}
		if(controls.isPressed(controls.getMiracle())) {
			if(player.miracles > 0 && ball.miracleActive == false && !ball.inactive) {
				player.miracles--;
				normalTimer.pause();
				ball.activateMiracle();
				Music bgm = level1.getCurrentMusic();
				bgm.setActivePart(bgm.getActivePart() + 2, true);
			}
		}
		player.walk(walkDistance);
	}
}
