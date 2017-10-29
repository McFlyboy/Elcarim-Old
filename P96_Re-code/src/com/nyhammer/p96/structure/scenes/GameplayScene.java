package com.nyhammer.p96.structure.scenes;

import com.nyhammer.p96.audio.Music;
import com.nyhammer.p96.entities.ModelEntity;
import com.nyhammer.p96.entities.TextField;
import com.nyhammer.p96.graphics.Render;
import com.nyhammer.p96.graphics.Texture;
import com.nyhammer.p96.structure.ResourceStorage;
import com.nyhammer.p96.structure.SceneStruct;
import com.nyhammer.p96.structure.controlSchemes.GameplayControls;
import com.nyhammer.p96.ui.GameWindow;
import com.nyhammer.p96.util.math.vector.Vector2f;
import com.nyhammer.p96.util.timing.Timer;

public class GameplayScene extends SceneStruct{
	private ModelEntity background;
	private TextField text;
	private GameplayControls controls;
	public GameplayScene(Timer timer){
		super(timer);
		controls = new GameplayControls();
		Texture bgTexture = new Texture("background/Background.png");
		ResourceStorage.add("bgTex", bgTexture);
		background = new ModelEntity(ResourceStorage.getModel("square"), bgTexture, new Vector2f(), new Vector2f(GameWindow.ASPECT_RATIO, 1f), 0f);
		Music bgm = new Music(new String[]{
				"P96_1_intro.ogg",
				"P96_1_main.ogg"
		});
		bgm.setPartLooping(0, false);
		ResourceStorage.add("bgm", bgm);
		text = new TextField();
		text.setText("This is a test");
		text.setRed(1f);
		text.setGreen(1f);
	}
	@Override
	protected void startSpecifics(){
		ResourceStorage.getMusic("bgm").play();
		updateControls();
	}
	@Override
	protected void updateSpecifics(){
		ResourceStorage.getMusic("bgm").update();
	}
	@Override
	protected void renderSpecifics(){
		Render.addToQueue(background);
		Render.addToQueue(text);
	}
	@Override
	protected void stopSpecifics(){
		
	}
	@Override
	protected void disposeSpecifics(){
		ResourceStorage.disposeTexture("bgTex");
		ResourceStorage.disposeMusic("bgm");
	}
	public void updateControls(){
		
	}
}