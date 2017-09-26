package com.nyhammer.p96.structure.scenes;

import com.nyhammer.p96.graphics.Model;
import com.nyhammer.p96.graphics.Models;
import com.nyhammer.p96.graphics.Render;
import com.nyhammer.p96.structure.SceneStruct;

public class GameplayScene extends SceneStruct{
	private Model model;
	public GameplayScene(){
		super(null);
		model = Models.createSquare();
	}
	@Override
	protected void startSpecifics(){
		
	}
	@Override
	protected void updateSpecifics(){
		
	}
	@Override
	protected void renderSpecifics(){
		Render.render(model);
	}
	@Override
	protected void stopSpecifics(){
		
	}
	@Override
	protected void disposeSpecifics(){
		model.dispose();
	}
}