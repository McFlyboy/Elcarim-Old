package com.nyhammer.p96.test.graphics.appearance;

public abstract class Appearance {
	private boolean visible;
	private boolean monochrome;
	public Appearance() {
		visible = true;
		monochrome = false;
	}
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	public boolean isMonochrome() {
		return monochrome;
	}
	public void setMonochrome(boolean monochrome) {
		this.monochrome = monochrome;
	}
	public abstract float getWidth();
	public abstract float getHeight();
}
