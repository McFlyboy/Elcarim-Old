package com.nyhammer.p96.structure.scenes;

import com.nyhammer.p96.audio.Sound;
import com.nyhammer.p96.entities.TextField;
import com.nyhammer.p96.graphics.Models;
import com.nyhammer.p96.graphics.Render;
import com.nyhammer.p96.structure.ControlScheme;
import com.nyhammer.p96.structure.ResourceStorage;
import com.nyhammer.p96.structure.Scene;
import com.nyhammer.p96.structure.controlSchemes.GlobalControls;
import com.nyhammer.p96.ui.GameWindow;
import com.nyhammer.p96.util.math.vector.Vector2f;
import com.nyhammer.p96.util.timing.Time;

public class GlobalScene extends Scene{
	private GlobalControls controls;
	private PauseScene pauseScene;
	private GameOverScene gameOverScene;
	private GameplayScene gameplayScene;
	private int gameState;
	private TextField fpsText;
	private TextField initText;
	private TextField congratzTitleText;
	private TextField congratzLineText;
	private TextField congratzText;
	private TextField continueText;
	public GlobalScene(){
		super(null);
		fpsText = new TextField();
		fpsText.scale = new Vector2f(0.0015f, 0.0015f);
		fpsText.mainColor.blue = 0f;
		fpsText.visible = false;
		initText = new TextField();
		initText.mainColor.blue = 0f;
		congratzTitleText = new TextField();
		congratzTitleText.setText("Congratulations!!");
		congratzTitleText.scale.x = 0.0075f;
		congratzTitleText.scale.y = 0.0075f;
		congratzTitleText.position.y = 0.4f;
		congratzLineText = new TextField();
		congratzLineText.setText("---------------------------------------------");
		congratzLineText.position.y = congratzTitleText.position.y - congratzTitleText.getHeight() / 2f - congratzLineText.getHeight() / 2f;
		congratzText = new TextField();
		congratzText.scale.x = 0.00225f;
		congratzText.scale.y = 0.00225f;
		congratzText.setText("Good job on clearing this demo!\nWhat you just played is not the final version of the game's first stage,\nbut hopefully it gives off an idea of how it will be in it's finished state."
				+ "\nI hope to see you again, playing future builds of this project, and I would be\nthrilled if you would give me some feedback on this build,\neither through Itch.io or the Discord-server."
				+ "\nAnyway, thank you for playing this game!\n\nSincerely, Henrik Nyhammer <3");
		congratzText.position.y = congratzLineText.position.y - congratzLineText.getHeight() / 2f - congratzText.getHeight() / 2f;
		continueText = new TextField();
		continueText.mainColor.blue = 0f;
		continueText.position.y = -0.8f;
		gameState = 3;
		controls = new GlobalControls();
		ResourceStorage.add("square", Models.createSquare());
		ResourceStorage.add("optionSound", new Sound("system/option.ogg"));
		ResourceStorage.add("confirmSound", new Sound("system/confirm.ogg"));
		ResourceStorage.add("cancelSound", new Sound("system/cancel.ogg"));
		pauseScene = new PauseScene(this.timer);
		gameOverScene = new GameOverScene(this.timer);
		gameplayScene = new GameplayScene(this.timer);
		gameplayScene.brightness = 0.5f;
	}
	@Override
	protected void startSpecifics(){
		
	}
	@Override
	protected void updateSpecifics(float deltaTime){
		updateControls();
		if(gameState == 0){
			gameplayScene.update();
			if(gameplayScene.isGameCompleted()){
				gameplayScene.stop();
				gameplayScene.brightness = 0f;
				gameState = 4;
			}
			if(gameplayScene.isGameOver()){
				gameplayScene.stop();
				gameState = 2;
				gameOverScene.start();
			}
		}
		else if(gameState == 1){
			pauseScene.update();
			int menuState = pauseScene.checkMenus();
			if(menuState == 1 || pauseScene.shouldExit()){
				gameState = 0;
				updateGameState();
			}
			else if(menuState == 2){
				gameState = 0;
				gameplayScene.dispose();
				gameplayScene = new GameplayScene(this.timer);
				updateGameState();
			}
		}
		else{
			gameOverScene.update();
			int menuState = gameOverScene.checkMenus();
			if(menuState == 1){
				gameState = 0;
				gameplayScene.dispose();
				gameplayScene = new GameplayScene(this.timer);
				gameOverScene.stop();
				gameplayScene.start();
			}
		}
	}
	@Override
	protected void renderSpecifics(){
		gameplayScene.render();
		if(gameState == 1){
			pauseScene.render();
		}
		else if(gameState == 2){
			gameOverScene.render();
		}
		Render.setScene(this);
		if(gameState == 3){
			initText.setText("Press [" + (ControlScheme.getActiveInput() == ControlScheme.ActiveInput.ACTIVE_GAMEPAD ? "A" : "Z") + "] to start!");
			Render.addToQueue(initText);
		}
		if(gameState == 4){
			Render.addToQueue(congratzTitleText);
			Render.addToQueue(congratzLineText);
			Render.addToQueue(congratzText);
			continueText.setText("Press [" + (ControlScheme.getActiveInput() == ControlScheme.ActiveInput.ACTIVE_GAMEPAD ? "A" : "Z") + "] to exit the game!");
			Render.addToQueue(continueText);
		}
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
		gameOverScene.dispose();
		ResourceStorage.disposeModel("square");
		ResourceStorage.disposeSound("optionSound");
		ResourceStorage.disposeSound("confirmSound");
		ResourceStorage.disposeSound("cancelSound");
	}
	private void updateControls(){
		if(controls.isPressed(controls.getPause()) && gameState < 2 && !(gameplayScene.getCurrentLevel().isCompleted() && gameplayScene.brightness < 0.5f)){
			gameState++;
			gameState %= 2;
			updateGameState();
			if(gameState == 1){
				ResourceStorage.getSound("confirmSound").play();
			}
		}
		if(controls.isPressed(controls.getFPS())){
			fpsText.visible = !fpsText.visible;
		}
		if(controls.isPressed(controls.getFullscreen())){
			boolean fullscreen = GameWindow.isFullscreen();
			GameWindow.setFullscreen(!fullscreen);
		}
		if(gameState == 3){
			if(controls.isPressed(controls.getInit())){
				gameState = 0;
				gameplayScene.start();
			}
		}
		if(gameState == 4){
			if(controls.isPressed(controls.getInit())){
				GameWindow.close();
			}
		}
	}
	private void updateGameState(){
		if(gameState == 0){
			pauseScene.stop();
			gameplayScene.start();
		}
		else{
			gameplayScene.stop();
			pauseScene.start();
		}
	}
	/*private void restartGameFully(){
		gameState = 3;
		pauseScene.dispose();
		gameOverScene.dispose();
		gameplayScene.dispose();
		pauseScene = new PauseScene(this.timer);
		gameOverScene = new GameOverScene(this.timer);
		gameplayScene = new GameplayScene(this.timer);
		gameplayScene.brightness = 0.5f;
	}*/
}