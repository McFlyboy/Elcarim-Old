package com.nyhammer.p96.structure.scenes;

import com.nyhammer.p96.graphics.Models;
import com.nyhammer.p96.structure.ResourceStorage;
import com.nyhammer.p96.structure.SceneStruct;
import com.nyhammer.p96.structure.controlSchemes.GlobalControls;
import com.nyhammer.p96.ui.GameWindow;

public class GlobalScene extends SceneStruct{
	private GlobalControls controls;
	private GameplayScene gameplayScene;
	public GlobalScene(){
		super(null);
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
	}
}