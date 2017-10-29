package com.nyhammer.p96.structure.controlSchemes;

import com.nyhammer.p96.input.Gamepad;
import com.nyhammer.p96.input.Keyboard;
import com.nyhammer.p96.structure.ControlScheme;

public class GlobalControls extends ControlScheme{
	private int pause;
	public GlobalControls(){
		/** key/button-inputs */
		super.inputs.add(new Input(Keyboard.KEY_ESCAPE, Gamepad.BUTTON_START));
		pause = super.assignInputIndex();
	}
	public int getPause(){
		return pause;
	}
}