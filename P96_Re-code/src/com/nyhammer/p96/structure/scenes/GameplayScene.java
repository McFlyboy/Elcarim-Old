package com.nyhammer.p96.structure.scenes;

import com.nyhammer.p96.Main;
import com.nyhammer.p96.audio.Music;
import com.nyhammer.p96.audio.Sound;
import com.nyhammer.p96.entities.Ball;
import com.nyhammer.p96.entities.ModelEntity;
import com.nyhammer.p96.entities.Player;
import com.nyhammer.p96.graphics.Render;
import com.nyhammer.p96.graphics.Texture;
import com.nyhammer.p96.structure.ResourceStorage;
import com.nyhammer.p96.structure.SceneStruct;
import com.nyhammer.p96.structure.controlSchemes.GameplayControls;
import com.nyhammer.p96.ui.GameWindow;
import com.nyhammer.p96.util.math.collision.CC;
import com.nyhammer.p96.util.math.vector.Vector2f;
import com.nyhammer.p96.util.timing.Timer;

public class GameplayScene extends SceneStruct{
	private ModelEntity background;
	private GameplayControls controls;
	private Player player;
	private Ball ball;
	public GameplayScene(Timer timer){
		super(timer);
		controls = new GameplayControls();
		Texture bgTex = new Texture("background/Background.png");
		ResourceStorage.add("bgTex", bgTex);
		background = new ModelEntity(ResourceStorage.getModel("square"), bgTex, new Vector2f(), new Vector2f(GameWindow.ASPECT_RATIO, 1f), 0f);
		Music bgm = new Music(new String[]{
				"P96_1_intro.ogg",
				"P96_1_main.ogg"
		});
		bgm.setPartLooping(0, false);
		ResourceStorage.add("bgm", bgm);
		Sound deathSound = new Sound("player_death.ogg");
		ResourceStorage.add("deathSound", deathSound);
		Texture playerTex = new Texture("char/player.png", 3, 3);
		player = new Player(this.sceneTimer);
		player.model = ResourceStorage.getModel("square");
		player.texture = playerTex;
		player.scale = new Vector2f(0.05f, 0.05f);
		player.position.y = -1f + player.scale.y;
		ResourceStorage.add("playerTex", playerTex);
		Texture bulletTex = new Texture("bullet/bullet.png");
		ResourceStorage.add("bulletTex", bulletTex);
		ball = new Ball();
		ball.model = ResourceStorage.getModel("square");
		Texture ballTex = new Texture("ball/ball.png");
		ball.texture = ballTex;
		ResourceStorage.add("ballTex", ballTex);
	}
	@Override
	protected void startSpecifics(){
		//ResourceStorage.getMusic("bgm").play();
	}
	@Override
	protected void updateSpecifics(){
		ResourceStorage.getMusic("bgm").update();
		if(player.alive){
			updateControls();
		}
		player.update();
		ball.update();
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
	}
	@Override
	protected void renderSpecifics(){
		Render.addToQueue(background);
		Render.addToQueue(player);
		Render.addToQueue(ball);
	}
	@Override
	protected void stopSpecifics(){
		
	}
	@Override
	protected void disposeSpecifics(){
		ResourceStorage.disposeTexture("bgTex");
		ResourceStorage.disposeTexture("playerTex");
		ResourceStorage.disposeTexture("bulletTex");
		ResourceStorage.disposeTexture("ballTex");
		ResourceStorage.disposeMusic("bgm");
		ResourceStorage.disposeSound("deathSound");
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
		player.walk(walkDistance * Main.getDeltaTime());
	}
}