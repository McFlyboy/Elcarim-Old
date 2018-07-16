package com.nyhammer.p96.test.structure.scenes;

import com.nyhammer.p96.structure.Scene;
import com.nyhammer.p96.test.structure.UI.UI;
import com.nyhammer.p96.util.timing.Timer;

public class PauseScene extends Scene {
	private UI ui;
	public PauseScene(Timer timer) {
		super(timer);
	}
	@Override
	protected void startSpecifics() {
		
	}
	@Override
	protected void updateSpecifics(float deltaTime) {
		
	}
	@Override
	protected void renderSpecifics() {
		ui.render();
	}
	@Override
	protected void stopSpecifics() {
		
	}
	@Override
	protected void disposeSpecifics() {
		
	}
}
