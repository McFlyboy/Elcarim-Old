package com.nyhammer.p96.structure.controlSchemes;

import com.nyhammer.p96.input.Gamepad;
import com.nyhammer.p96.input.Keyboard;
import com.nyhammer.p96.structure.ControlScheme;

public class GlobalControls extends ControlScheme {
	private int pause;
	private int fps;
	private int fullscreen;
	private int init;
	public GlobalControls() {
		/** key/button-inputs */
		super.inputs.add(new Input(Keyboard.KEY_ESCAPE, Gamepad.BUTTON_START));
		pause = super.assignInputIndex();
		super.inputs.add(new Input(Keyboard.KEY_F1, Gamepad.BUTTON_LS));
		fps = super.assignInputIndex();
		super.inputs.add(new Input(Keyboard.KEY_F11, Gamepad.BUTTON_BACK));
		fullscreen = super.assignInputIndex();
		super.inputs.add(new Input(Keyboard.KEY_Z, Gamepad.BUTTON_A));
		init = super.assignInputIndex();
	}
	public int getPause() {
		return pause;
	}
	public int getFPS() {
		return fps;
	}
	public int getFullscreen() {
		return fullscreen;
	}
	public int getInit() {
		return init;
	}
}
