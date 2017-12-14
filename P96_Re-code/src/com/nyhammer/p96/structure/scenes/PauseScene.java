package com.nyhammer.p96.structure.scenes;

import com.nyhammer.p96.Main;
import com.nyhammer.p96.entities.TextField;
import com.nyhammer.p96.graphics.Render;
import com.nyhammer.p96.structure.Menu;
import com.nyhammer.p96.structure.ResourceStorage;
import com.nyhammer.p96.structure.Scene;
import com.nyhammer.p96.structure.controlSchemes.MenuControls;
import com.nyhammer.p96.ui.GameWindow;
import com.nyhammer.p96.util.math.vector.Vector2f;
import com.nyhammer.p96.util.timing.Timer;

public class PauseScene extends Scene{
	private MenuControls controls;
	private TextField versionText;
	private TextField creatorText;
	private Menu mainMenu;
	public PauseScene(Timer timer){
		super(timer);
		controls = new MenuControls();
		versionText = new TextField();
		versionText.scale = new Vector2f(0.0025f, 0.0025f);
		versionText.mainColor.blue = 0f;
		versionText.setText("Version " + Main.getVersion());
		versionText.position.x = -GameWindow.ASPECT_RATIO + versionText.getWidth() / 2f;
		versionText.position.y = -1f + versionText.getHeight() / 2f;
		creatorText = new TextField();
		creatorText.scale = new Vector2f(0.0025f, 0.0025f);
		creatorText.mainColor.blue = 0f;
		creatorText.setText("Created by Henrik Nyhammer");
		creatorText.position.x = GameWindow.ASPECT_RATIO - creatorText.getWidth() / 2f;
		creatorText.position.y = -1f + creatorText.getHeight() / 2f;
		mainMenu = new Menu(this.timer, "Resume", "Exit");
	}
	@Override
	protected void startSpecifics(){
		
	}
	@Override
	protected void updateSpecifics(float deltaTime){
		updateControls();
	}
	@Override
	protected void renderSpecifics(){
		Render.addToQueue(versionText);
		Render.addToQueue(creatorText);
		mainMenu.render();
	}
	@Override
	protected void stopSpecifics(){
		mainMenu.reset();
	}
	@Override
	protected void disposeSpecifics(){
		
	}
	private void updateControls(){
		if(mainMenu.isOptionSelected()){
			return;
		}
		if(controls.isPressed(controls.getConfirm())){
			mainMenu.setOptionSelected(true);
			ResourceStorage.getSound("confirmSound").play();
		}
		if(controls.isPressed(controls.getCancel())){
			ResourceStorage.getSound("cancelSound").play();
		}
		if(controls.isPressed(controls.getLeft())){
			
		}
		if(controls.isPressed(controls.getRight())){
			
		}
		if(controls.isPressed(controls.getUp())){
			mainMenu.scrollUp();
		}
		if(controls.isPressed(controls.getDown())){
			mainMenu.scrollDown();
		}
	}
	public boolean checkMenus(){
		if(mainMenu.isOptionContinue()){
			mainMenu.setOptionSelected(false);
			if(mainMenu.getActiveOption() == 0){
				mainMenu.setOptionContinue(false);
				return true;
			}
			else{
				GameWindow.close();
			}
		}
		return false;
	}
}