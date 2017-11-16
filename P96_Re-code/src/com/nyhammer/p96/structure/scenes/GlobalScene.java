package com.nyhammer.p96.structure.scenes;

import com.nyhammer.p96.entities.TextField;
import com.nyhammer.p96.graphics.Models;
import com.nyhammer.p96.graphics.Render;
import com.nyhammer.p96.structure.ResourceStorage;
import com.nyhammer.p96.structure.SceneStruct;
import com.nyhammer.p96.structure.controlSchemes.GlobalControls;
import com.nyhammer.p96.ui.GameWindow;
import com.nyhammer.p96.util.math.vector.Vector2f;
import com.nyhammer.p96.util.timing.Time;

public class GlobalScene extends SceneStruct{
	private GlobalControls controls;
	private GameplayScene gameplayScene;
	private TextField fpsText;
	public GlobalScene(){
		super(null);
		fpsText = new TextField();
		fpsText.scale = new Vector2f(0.0015f, 0.0015f);
		fpsText.mainColor.blue = 0f;
		fpsText.visible = false;
		controls = new GlobalControls();
		ResourceStorage.add("square", Models.createSquare());
	}
	@Override
	protected void startSpecifics(){
		gameplayScene = new GameplayScene(this.sceneTimer);
		gameplayScene.start();
	}
	@Override
	protected void updateSpecifics(){
		updateControls();
		gameplayScene.update();
	}
	@Override
	protected void renderSpecifics(){
		gameplayScene.render();
		fpsText.setText("FPS: " + Time.getFPS());
		fpsText.position = new Vector2f(GameWindow.ASPECT_RATIO - fpsText.getWidth() / 2f, 0f);
		Render.addToQueue(fpsText);
	}
	@Override
	protected void stopSpecifics(){
		gameplayScene.stop();
	}
	@Override
	protected void disposeSpecifics(){
		gameplayScene.dispose();
		ResourceStorage.disposeModel("square");
	}
	public void updateControls(){
		if(controls.isPressed(controls.getPause())){
			GameWindow.close();
		}
		if(controls.isPressed(controls.getFPS())){
			fpsText.visible = !fpsText.visible;
		}
		if(controls.isPressed(controls.getFullscreen())){
			boolean fullscreen = GameWindow.isFullscreen();
			GameWindow.setFullscreen(!fullscreen);
		}
	}
}