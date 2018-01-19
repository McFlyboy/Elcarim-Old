package com.nyhammer.p96.structure.scenes;

import com.nyhammer.p96.audio.Music;
import com.nyhammer.p96.entities.TextField;
import com.nyhammer.p96.graphics.Render;
import com.nyhammer.p96.structure.Menu;
import com.nyhammer.p96.structure.ResourceStorage;
import com.nyhammer.p96.structure.Scene;
import com.nyhammer.p96.structure.controlSchemes.MenuControls;
import com.nyhammer.p96.ui.GameWindow;
import com.nyhammer.p96.util.math.vector.Vector2f;
import com.nyhammer.p96.util.timing.Timer;

public class GameOverScene extends Scene{
	private MenuControls controls;
	private Menu menu;
	private TextField titleText;
	public GameOverScene(Timer timer){
		super(timer);
		controls = new MenuControls();
		Music bgm = new Music("P96_GAME_OVER.ogg");
		bgm.setVolume(0f);
		ResourceStorage.add("gameOverBGM", bgm);
		menu = new Menu(this.timer, "Retry", "Exit");
		menu.setPosition(new Vector2f(0f, -0.225f));
		titleText = new TextField();
		titleText.scale = new Vector2f(0.007f, 0.007f);
		titleText.setText("-GAME OVER-");
		titleText.position.y = 0.1f;
	}
	@Override
	protected void startSpecifics(){
		ResourceStorage.getMusic("gameOverBGM").play();
	}
	@Override
	protected void updateSpecifics(float deltaTime){
		Music bgm = ResourceStorage.getMusic("gameOverBGM");
		float volume = bgm.getVolume();
		if(volume < 1f){
			volume += 0.8f * deltaTime;
			if(volume > 1f){
				volume = 1f;
			}
			bgm.setVolume(volume);
		}
		bgm.update();
		updateControls();
	}
	@Override
	protected void renderSpecifics(){
		Render.addToQueue(titleText);
		menu.render();
	}
	@Override
	protected void stopSpecifics(){
		ResourceStorage.getMusic("gameOverBGM").stop();
		menu.reset();
	}
	@Override
	protected void disposeSpecifics(){
		ResourceStorage.disposeMusic("gameOverBGM");
	}
	private void updateControls(){
		if(menu.isOptionSelected()){
			return;
		}
		if(controls.isPressed(controls.getConfirm())){
			menu.setOptionSelected(true);
			ResourceStorage.getSound("confirmSound").play();
		}
		if(controls.isPressed(controls.getCancel())){
			
		}
		if(controls.isPressed(controls.getLeft())){
			
		}
		if(controls.isPressed(controls.getRight())){
			
		}
		if(controls.isPressed(controls.getUp())){
			menu.scrollUp();
		}
		if(controls.isPressed(controls.getDown())){
			menu.scrollDown();
		}
	}
	public int checkMenus(){
		if(menu.isOptionContinue()){
			ResourceStorage.getMusic("gameOverBGM").setVolume(0f);
			menu.setOptionSelected(false);
			switch(menu.getActiveOption()){
			case 0:
				menu.setOptionContinue(false);
				return 1;
			default:
				GameWindow.close();
				break;
			}
		}
		return 0;
	}
}