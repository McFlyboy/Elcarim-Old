package com.nyhammer.p96.structure.scenes;

import com.nyhammer.p96.Game;
import com.nyhammer.p96.entities.TextField;
import com.nyhammer.p96.graphics.Render;
import com.nyhammer.p96.structure.Menu;
import com.nyhammer.p96.structure.ResourceStorage;
import com.nyhammer.p96.structure.Scene;
import com.nyhammer.p96.structure.controlSchemes.MenuControls;
import com.nyhammer.p96.ui.GameWindow;
import com.nyhammer.p96.util.math.vector.Vector2f;
import com.nyhammer.p96.util.timing.Timer;

public class PauseScene extends Scene {
	private MenuControls controls;
	private TextField titleText;
	private TextField versionText;
	private TextField creatorText;
	private TextField keyboardControlsText;
	private TextField xboxControlsText;
	private Menu currentMenu;
	private Menu mainMenu;
	private Menu keyboardMenu;
	private Menu xboxMenu;
	private boolean exit;
	public PauseScene(Timer timer) {
		super(timer);
		controls = new MenuControls();
		titleText = new TextField();
		titleText.scale = new Vector2f(0.007f, 0.007f);
		titleText.setText("-Game paused-");
		titleText.position.y = 0.4f;
		versionText = new TextField();
		versionText.scale = new Vector2f(0.002f, 0.002f);
		versionText.mainColor.blue = 0f;
		versionText.setText("Version " + Game.getVersion());
		versionText.position.x = -GameWindow.ASPECT_RATIO + versionText.getWidth() / 2f;
		versionText.position.y = -1f + versionText.getHeight() / 2f;
		creatorText = new TextField();
		creatorText.scale = new Vector2f(0.002f, 0.002f);
		creatorText.mainColor.blue = 0f;
		creatorText.setText("Created by Henrik Nyhammer");
		creatorText.position.x = GameWindow.ASPECT_RATIO - creatorText.getWidth() / 2f;
		creatorText.position.y = -1f + creatorText.getHeight() / 2f;
		keyboardControlsText = new TextField();
		keyboardControlsText.scale = new Vector2f(0.003f, 0.003f);
		keyboardControlsText.setText("Move: Arrow-keys\nJump: Space\nShoot: Z-key/X-key\nHit: C-key\nUse Miracle: A-key\nToggle fullscreen: F11-key\nShow FPS-counter: F1-key");
		keyboardControlsText.position.y = -0.2f;
		xboxControlsText = new TextField();
		xboxControlsText.scale = new Vector2f(0.003f, 0.003f);
		xboxControlsText.setText("Move: Left stick\nJump: A-button\nShoot: LB-button/RB-button\nHit: X-button\nUse Miracle: Y-button\nToggle fullscreen: Back-button\nShow FPS-counter: LS-button");
		xboxControlsText.position.y = -0.2f;
		mainMenu = new Menu(this.timer, "Resume", "Retry", "Keyboard controls", "XBOX controls", "Exit");
		mainMenu.setPosition(new Vector2f(0f, -0.225f));
		keyboardMenu = new Menu(this.timer, "Back");
		keyboardMenu.setPosition(new Vector2f(0f, -0.8f));
		xboxMenu = new Menu(this.timer, "Back");
		xboxMenu.setPosition(new Vector2f(0f, -0.8f));
	}
	public boolean shouldExit() {
		boolean answer = exit;
		exit = false;
		return answer;
	}
	@Override
	protected void startSpecifics() {
		currentMenu = mainMenu;
	}
	@Override
	protected void updateSpecifics(float deltaTime) {
		updateControls();
	}
	@Override
	protected void renderSpecifics() {
		Render.addToQueue(versionText);
		Render.addToQueue(creatorText);
		Render.addToQueue(titleText);
		if(currentMenu == keyboardMenu) {
			Render.addToQueue(keyboardControlsText);
		}
		else if(currentMenu == xboxMenu) {
			Render.addToQueue(xboxControlsText);
		}
		currentMenu.render();
	}
	@Override
	protected void stopSpecifics() {
		xboxMenu.reset();
		keyboardMenu.reset();
		mainMenu.reset();
	}
	@Override
	protected void disposeSpecifics() {
		
	}
	private void updateControls() {
		if(currentMenu.isOptionSelected()) {
			return;
		}
		if(controls.isPressed(controls.getConfirm())) {
			currentMenu.setOptionSelected(true);
			ResourceStorage.getSound("confirmSound").play();
		}
		if(controls.isPressed(controls.getCancel())) {
			if(currentMenu == mainMenu) {
				exit = true;
			}
			else {
				currentMenu.reset();
				currentMenu = mainMenu;
				ResourceStorage.getSound("cancelSound").play();
			}
		}
		if(controls.isPressed(controls.getLeft())) {
			
		}
		if(controls.isPressed(controls.getRight())) {
			
		}
		if(controls.isPressed(controls.getUp())) {
			currentMenu.scrollUp();
		}
		if(controls.isPressed(controls.getDown())) {
			currentMenu.scrollDown();
		}
	}
	public int checkMenus() {
		if(currentMenu == mainMenu) {
			if(currentMenu.isOptionContinue()) {
				currentMenu.setOptionSelected(false);
				switch(mainMenu.getActiveOption()) {
				case 0:
					currentMenu.setOptionContinue(false);
					return 1;
				case 1:
					currentMenu.setOptionContinue(false);
					return 2;
				case 2:
					currentMenu.setOptionContinue(false);
					currentMenu = keyboardMenu;
					break;
				case 3:
					currentMenu.setOptionContinue(false);
					currentMenu = xboxMenu;
					break;
				default:
					GameWindow.close();
					break;
				}
			}
		}
		if(currentMenu == keyboardMenu) {
			if(currentMenu.isOptionContinue()) {
				currentMenu.setOptionSelected(false);
				switch(mainMenu.getActiveOption()) {
				default:
					currentMenu.setOptionContinue(false);
					currentMenu = mainMenu;
					break;
				}
			}
		}
		if(currentMenu == xboxMenu) {
			if(currentMenu.isOptionContinue()) {
				currentMenu.setOptionSelected(false);
				switch(mainMenu.getActiveOption()) {
				default:
					currentMenu.setOptionContinue(false);
					currentMenu = mainMenu;
					break;
				}
			}
		}
		return 0;
	}
}
