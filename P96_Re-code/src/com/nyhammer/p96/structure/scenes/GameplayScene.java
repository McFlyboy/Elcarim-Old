package com.nyhammer.p96.structure.scenes;

import com.nyhammer.p96.graphics.Render;
import com.nyhammer.p96.graphics.Texture;
import com.nyhammer.p96.structure.ResourceStorage;
import com.nyhammer.p96.structure.SceneStruct;

public class GameplayScene extends SceneStruct{
	public GameplayScene(){
		super(null);
		ResourceStorage.add("bgTex", new Texture("background/Background.png"));
	}
	@Override
	protected void startSpecifics(){
		
	}
	@Override
	protected void updateSpecifics(){
		
	}
	@Override
	protected void renderSpecifics(){
		Render.render(ResourceStorage.getModel("square"), ResourceStorage.getTexture("bgTex"));
	}
	@Override
	protected void stopSpecifics(){
		
	}
	@Override
	protected void disposeSpecifics(){
		ResourceStorage.disposeTexture("bgTex");
	}
}