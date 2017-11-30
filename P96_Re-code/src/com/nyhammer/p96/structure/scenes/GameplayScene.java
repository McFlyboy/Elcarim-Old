package com.nyhammer.p96.structure.scenes;

import java.util.ArrayList;
import java.util.List;

import com.nyhammer.p96.audio.Music;
import com.nyhammer.p96.audio.Sound;
import com.nyhammer.p96.entities.Ball;
import com.nyhammer.p96.entities.Bullet;
import com.nyhammer.p96.entities.Enemy;
import com.nyhammer.p96.entities.ModelEntity;
import com.nyhammer.p96.entities.Player;
import com.nyhammer.p96.entities.Shot;
import com.nyhammer.p96.entities.TextField;
import com.nyhammer.p96.entities.Todder;
import com.nyhammer.p96.entities.World;
import com.nyhammer.p96.graphics.Render;
import com.nyhammer.p96.graphics.Texture;
import com.nyhammer.p96.structure.ResourceStorage;
import com.nyhammer.p96.structure.SceneStruct;
import com.nyhammer.p96.structure.controlSchemes.GameplayControls;
import com.nyhammer.p96.ui.GameWindow;
import com.nyhammer.p96.util.math.collision.CC;
import com.nyhammer.p96.util.math.vector.Vector2f;
import com.nyhammer.p96.util.timing.DeltaTimer;
import com.nyhammer.p96.util.timing.Timer;

public class GameplayScene extends SceneStruct{
	private World world;
	private ModelEntity background;
	private GameplayControls controls;
	private Player player;
	private Ball ball;
	private TextField livesText;
	private TextField miraclesText;
	private List<Shot> shots;
	private List<Enemy> enemies;
	private List<Bullet> bullets;
	private Timer normalTimer;
	private DeltaTimer normalDeltaTimer;
	public GameplayScene(Timer timer){
		super(timer);
		normalTimer = new Timer(sceneTimer, true);
		normalDeltaTimer = new DeltaTimer(normalTimer);
		controls = new GameplayControls();
		world = new World();
		Texture bgTex = new Texture("background/background.png");
		ResourceStorage.add("bgTex", bgTex);
		background = new ModelEntity(ResourceStorage.getModel("square"), bgTex, new Vector2f(), new Vector2f(GameWindow.ASPECT_RATIO, 1f), 0f);
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
		Sound deathSound = new Sound("player_death.ogg");
		ResourceStorage.add("deathSound", deathSound);
		Sound hitSound = new Sound("enemy_hit.ogg");
		ResourceStorage.add("hitSound", hitSound);
		Sound shotSound = new Sound("shot.ogg");
		ResourceStorage.add("shotSound", shotSound);
		Sound miracleSound = new Sound("miracle.ogg");
		ResourceStorage.add("miracleSound", miracleSound);
		Texture playerTex = new Texture("char/player.png", 3, 3);
		player = new Player(sceneTimer);
		player.model = ResourceStorage.getModel("square");
		player.texture = playerTex;
		ResourceStorage.add("playerTex", playerTex);
		Texture bulletRedTex = new Texture("bullet/bulletRed.png");
		ResourceStorage.add("bulletRedTex", bulletRedTex);
		ball = new Ball(sceneTimer);
		ball.model = ResourceStorage.getModel("square");
		Texture ballTex = new Texture("ball/ball.png");
		ball.texture = ballTex;
		ResourceStorage.add("ballTex", ballTex);
		livesText = new TextField();
		livesText.mainColor.green = 0.5f;
		livesText.mainColor.blue = 0.5f;
		miraclesText = new TextField();
		miraclesText.mainColor.red = 0.3f;
		miraclesText.mainColor.green = 0.8f;
		shots = new ArrayList<Shot>();
		Texture shotTex = new Texture("shot/shot.png");
		ResourceStorage.add("shotTex", shotTex);
		Texture todderTex = new Texture("enemy/todder.png");
		ResourceStorage.add("todderTex", todderTex);
		bullets = new ArrayList<Bullet>();
		enemies = new ArrayList<Enemy>();
		Todder todder = new Todder(bullets, normalTimer);
		todder.model = ResourceStorage.getModel("square");
		todder.texture = todderTex;
		todder.position.y = 0.3f;
		enemies.add(todder);
	}
	@Override
	protected void startSpecifics(){
		ResourceStorage.getMusic("bgm").play();
	}
	@Override
	protected void updateSpecifics(float deltaTime){
		float normaldeltaTime = (float)normalDeltaTimer.getTime();
		ResourceStorage.getMusic("bgm").update();
		if(player.alive){
			updateControls();
		}
		player.update(deltaTime);
		for(Enemy enemy : enemies){
			enemy.update();
		}
		ball.update(normaldeltaTime, normalTimer);
		if(player.hitting){
			if(CC.checkCollision(player.hitCC, ball.cc)){
				if(player.direction.x == 0f){
					ball.direction = new Vector2f(player.lastXDirection / Math.abs(player.lastXDirection), 2f).getNormalize().getMul(player.jumping ? 15f : 12f);
				}
				else{
					ball.direction = new Vector2f(player.direction.x / Math.abs(player.direction.x), 2f).getNormalize().getMul(15f);
				}
			}
		}
		else{
			if(CC.checkCollision(player.cc, ball.cc)){
				player.die();
			}
		}
		for(int i = 0; i < enemies.size(); i++){
			Enemy enemy = enemies.get(i);
			enemy.update();
			if(CC.checkCollision(enemy.cc, ball.cc)){
				if(!enemy.hit){
					enemy.hit = true;
					enemy.lives--;
					enemy.colorActive = true;
					ball.direction.x *= -0.6f;
					ball.direction.y *= 0.6f;
					ResourceStorage.getSound("hitSound").play();
					enemy.hitTimer.resume();
				}
			}
			else{
				enemy.hit = false;
			}
			if(enemy.hitTimer.targetReached()){
				if(enemy.lives > 0){
					enemy.colorActive = false;
					enemy.hitTimer.reset();
				}
				else{
					enemies.remove(i);
					i--;
				}
			}
		}
		for(int i = 0; i < bullets.size(); i++){
			Bullet bullet = bullets.get(i);
			if(!ball.miracleActive){
				bullet.update(normaldeltaTime);
			}
			else{
				bullet.position.add(ball.position.getSub(bullet.position).getNormalize().getMul(deltaTime * 0.3f));
				if(Math.abs(bullet.position.x) > GameWindow.ASPECT_RATIO + bullet.scale.x || Math.abs(bullet.position.y) > 1f + bullet.scale.y){
					bullet.hp = 0;
				}
			}
			if(CC.checkCollision(bullet.cc, player.cc)){
				player.die();
			}
			if(CC.checkCollision(bullet.cc, ball.cc)){
				bullet.hp = 0;
			}
			if(bullet.hp == 0){
				bullets.remove(i);
				i--;
			}
		}
		for(int i = 0; i < shots.size(); i++){
			Shot shot = shots.get(i);
			shot.update(deltaTime);
			if(CC.checkCollision(shot.cc, ball.cc)){
				if(!ball.miracleActive){
					Vector2f shotAngle = ball.position.getSub(shot.position).getNormalize();
					ball.direction.add(shotAngle.getMul(12f * shotAngle.y));
				}
				shot.intact = false;
			}
			for(int j = 0; j < bullets.size(); j++){
				Bullet bullet = bullets.get(j);
				if(CC.checkCollision(shot.cc, bullet.cc)){
					shot.intact = false;
					bullet.hp = 0;
					bullets.remove(j);
					j--;
				}
			}
			if(!shot.intact){
				shots.remove(i);
				i--;
			}
		}
		double miracleTime = ball.miracleTimer.getTime();
		double progress = miracleTime / ball.miracleTimer.getTargetTime();
		world.position.x = (float)(Math.sin(miracleTime * 1000.0 / 15.0) * 0.01f * progress);
		world.position.y = (float)(Math.sin(miracleTime * 1000.0 / 25.0) * 0.025f * progress);
	}
	@Override
	protected void renderSpecifics(){
		Render.setWorld(world);
		Render.addToQueue(background);
		Render.addToQueue(player);
		for(Enemy enemy : enemies){
			Render.addToQueue(enemy);
		}
		Render.addToQueue(ball);
		for(Bullet bullet : bullets){
			Render.addToQueue(bullet);
		}
		for(Shot shot : shots){
			Render.addToQueue(shot);
		}
		livesText.setText("Lives: " + player.lives);
		livesText.position.x = GameWindow.ASPECT_RATIO - livesText.getWidth() / 2f;
		livesText.position.y = 1f - livesText.getHeight() / 2f;
		Render.addToQueue(livesText);
		miraclesText.setText("Miracle: " + player.miracles);
		miraclesText.position.x = GameWindow.ASPECT_RATIO - miraclesText.getWidth() / 2f;
		miraclesText.position.y = 1f - livesText.getHeight() / 2f - miraclesText.getHeight();
		Render.addToQueue(miraclesText);
	}
	@Override
	protected void stopSpecifics(){
		
	}
	@Override
	protected void disposeSpecifics(){
		ResourceStorage.disposeTexture("bgTex");
		ResourceStorage.disposeTexture("playerTex");
		ResourceStorage.disposeTexture("bulletRedTex");
		ResourceStorage.disposeTexture("ballTex");
		ResourceStorage.disposeTexture("shotTex");
		ResourceStorage.disposeTexture("todderTex");
		ResourceStorage.disposeMusic("bgm");
		ResourceStorage.disposeSound("deathSound");
		ResourceStorage.disposeSound("hitSound");
		ResourceStorage.disposeSound("shotSound");
		ResourceStorage.disposeSound("miracleSound");
	}
	public void updateControls(){
		float walkDistance = 0;
		if(controls.isDown(controls.getMoveLeft())){
			walkDistance += -1f;
		}
		if(controls.isDown(controls.getMoveRight())){
			walkDistance += 1f;
		}
		if(controls.isPressed(controls.getJump())){
			player.jump();
		}
		player.holdJumping = controls.isDown(controls.getJump());
		if(controls.isPressed(controls.getHit())){
			player.hit();
		}
		if(controls.isPressed(controls.getShootLeft()) || controls.isPressed(controls.getShootRight())){
			player.shoot(shots);
		}
		if(controls.isPressed(controls.getMiracle())){
			if(player.miracles > 0 && ball.miracleActive == false){
				player.miracles--;
				normalTimer.pause();
				ball.activateMiracle();
			}
		}
		player.walk(walkDistance);
	}
}