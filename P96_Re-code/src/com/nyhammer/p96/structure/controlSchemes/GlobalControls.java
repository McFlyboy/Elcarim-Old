package com.nyhammer.p96.structure.controlSchemes;

import com.nyhammer.p96.input.Gamepad;
import com.nyhammer.p96.input.Keyboard;
import com.nyhammer.p96.structure.ControlScheme;

public class GlobalControls extends ControlScheme{
	private int pause;
	private int fullscreen;
	public GlobalControls(){
		/** key/button-inputs */
		super.inputs.add(new Input(Keyboard.KEY_ESCAPE, Gamepad.BUTTON_START));
		pause = super.assignInputIndex();
		super.inputs.add(new Input(Keyboard.KEY_F11, Gamepad.BUTTON_BACK));
		fullscreen = super.assignInputIndex();
	}
	public int getPause(){
		return pause;
	}
	public int getFullscreen(){
		return fullscreen;
	}
}