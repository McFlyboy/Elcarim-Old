package com.nyhammer.p96.structure.scenes;

import com.nyhammer.p96.audio.Sound;
import com.nyhammer.p96.entities.TextField;
import com.nyhammer.p96.graphics.Models;
import com.nyhammer.p96.graphics.Render;
import com.nyhammer.p96.structure.ResourceStorage;
import com.nyhammer.p96.structure.Scene;
import com.nyhammer.p96.structure.controlSchemes.GlobalControls;
import com.nyhammer.p96.ui.GameWindow;
import com.nyhammer.p96.util.math.vector.Vector2f;
import com.nyhammer.p96.util.timing.Time;

public class GlobalScene extends Scene{
	private GlobalControls controls;
	private PauseScene pauseScene;
	private GameplayScene gameplayScene;
	private boolean gamePaused;
	private TextField fpsText;
	public GlobalScene(){
		super(null);
		fpsText = new TextField();
		fpsText.scale = new Vector2f(0.0015f, 0.0015f);
		fpsText.mainColor.blue = 0f;
		fpsText.visible = false;
		gamePaused = false;
		controls = new GlobalControls();
		ResourceStorage.add("square", Models.createSquare());
		ResourceStorage.add("optionSound", new Sound("system/option.ogg"));
		ResourceStorage.add("confirmSound", new Sound("system/confirm.ogg"));
		ResourceStorage.add("cancelSound", new Sound("system/cancel.ogg"));
	}
	@Override
	protected void startSpecifics(){
		pauseScene = new PauseScene(this.timer);
		gameplayScene = new GameplayScene(this.timer);
		gameplayScene.start();
	}
	@Override
	protected void updateSpecifics(float deltaTime){
		updateControls();
		if(!gamePaused){
			gameplayScene.update();
			if(gameplayScene.isGameOver()){
				gameplayScene.dispose();
				gameplayScene = new GameplayScene(this.timer);
				gameplayScene.start();
			}
		}
		else{
			pauseScene.update();
			int menuState = pauseScene.checkMenus();
			if(menuState == 1){
				gamePaused = false;
				updateGameState();
			}
			else if(menuState == 2){
				System.out.println("Test");
				gamePaused = false;
				gameplayScene.dispose();
				gameplayScene = new GameplayScene(this.timer);
				updateGameState();
			}
		}
	}
	@Override
	protected void renderSpecifics(){
		gameplayScene.render();
		if(gamePaused){
			pauseScene.render();
		}
		Render.setScene(this);
		fpsText.setText("FPS: " + Time.getFPS());
		fpsText.position = new Vector2f(GameWindow.ASPECT_RATIO - fpsText.getWidth() / 2f, 0f);
		Render.addToQueue(fpsText);
	}
	@Override
	protected void stopSpecifics(){
		gameplayScene.stop();
		pauseScene.stop();
	}
	@Override
	protected void disposeSpecifics(){
		gameplayScene.dispose();
		pauseScene.dispose();
		ResourceStorage.disposeModel("square");
		ResourceStorage.disposeSound("optionSound");
		ResourceStorage.disposeSound("confirmSound");
		ResourceStorage.disposeSound("cancelSound");
	}
	private void updateControls(){
		if(controls.isPressed(controls.getPause())){
			gamePaused = !gamePaused;
			updateGameState();
		}
		if(controls.isPressed(controls.getFPS())){
			fpsText.visible = !fpsText.visible;
		}
		if(controls.isPressed(controls.getFullscreen())){
			boolean fullscreen = GameWindow.isFullscreen();
			GameWindow.setFullscreen(!fullscreen);
		}
	}
	private void updateGameState(){
		if(!gamePaused){
			pauseScene.stop();
			gameplayScene.start();
		}
		else{
			gameplayScene.stop();
			pauseScene.start();
		}
	}
}