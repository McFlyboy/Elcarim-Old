package com.nyhammer.p96.structure.scenes;

import com.nyhammer.p96.graphics.Model;
import com.nyhammer.p96.graphics.Models;
import com.nyhammer.p96.graphics.Render;
import com.nyhammer.p96.graphics.Texture;
import com.nyhammer.p96.structure.SceneStruct;

public class GameplayScene extends SceneStruct{
	private Model square;
	private Texture bgTex;
	public GameplayScene(){
		super(null);
		square = Models.createSquare();
		bgTex = new Texture("background/Background.png", false);
	}
	@Override
	protected void startSpecifics(){
		
	}
	@Override
	protected void updateSpecifics(){
		
	}
	@Override
	protected void renderSpecifics(){
		Render.render(square, bgTex);
	}
	@Override
	protected void stopSpecifics(){
		
	}
	@Override
	protected void disposeSpecifics(){
		bgTex.dispose();
		square.dispose();
	}
}