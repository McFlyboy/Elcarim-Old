package com.nyhammer.p96.structure.scenes;

import com.nyhammer.p96.audio.Music;
import com.nyhammer.p96.graphics.Render;
import com.nyhammer.p96.graphics.Texture;
import com.nyhammer.p96.structure.ResourceStorage;
import com.nyhammer.p96.structure.SceneStruct;

public class GameplayScene extends SceneStruct{
	public GameplayScene(){
		super(null);
		ResourceStorage.add("bgTex", new Texture("background/Background.png"));
		ResourceStorage.add("bgm", new Music(new String[]{
				"P96_1_intro.ogg",
				"P96_1_main.ogg"
		}));
		ResourceStorage.getMusic("bgm").setPartLooping(0, false);
	}
	@Override
	protected void startSpecifics(){
		ResourceStorage.getMusic("bgm").play();
	}
	@Override
	protected void updateSpecifics(){
		ResourceStorage.getMusic("bgm").update();
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
		ResourceStorage.disposeMusic("bgm");
	}
}