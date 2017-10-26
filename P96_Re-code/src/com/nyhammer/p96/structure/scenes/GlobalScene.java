package com.nyhammer.p96.structure.scenes;

import com.nyhammer.p96.graphics.Models;
import com.nyhammer.p96.structure.ResourceStorage;
import com.nyhammer.p96.structure.SceneStruct;

public class GlobalScene extends SceneStruct{
	private GameplayScene gameplayScene;
	public GlobalScene(){
		super(null);
		ResourceStorage.add("square", Models.createSquare());
	}
	@Override
	protected void startSpecifics(){
		gameplayScene = new GameplayScene(this.sceneTimer);
		gameplayScene.start();
	}
	@Override
	protected void updateSpecifics(){
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
}