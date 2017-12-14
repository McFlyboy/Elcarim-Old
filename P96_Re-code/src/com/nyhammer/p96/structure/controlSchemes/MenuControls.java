package com.nyhammer.p96.structure.controlSchemes;

import com.nyhammer.p96.input.Gamepad;
import com.nyhammer.p96.input.Keyboard;
import com.nyhammer.p96.structure.ControlScheme;

public class MenuControls extends ControlScheme{
	private int confirm;
	private int cancel;
	private int left;
	private int right;
	private int up;
	private int down;
	public MenuControls(){
		/** key/button-inputs */
		super.inputs.add(new Input(Keyboard.KEY_Z, Gamepad.BUTTON_A));
		confirm = super.assignInputIndex();
		super.inputs.add(new Input(Keyboard.KEY_X, Gamepad.BUTTON_B));
		cancel = super.assignInputIndex();
		super.inputs.add(new Input(Keyboard.KEY_LEFT, Gamepad.BUTTON_DPAD_LEFT));
		left = super.assignInputIndex();
		super.inputs.add(new Input(Keyboard.KEY_RIGHT, Gamepad.BUTTON_DPAD_RIGHT));
		right = super.assignInputIndex();
		super.inputs.add(new Input(Keyboard.KEY_UP, Gamepad.BUTTON_DPAD_UP));
		up = super.assignInputIndex();
		super.inputs.add(new Input(Keyboard.KEY_DOWN, Gamepad.BUTTON_DPAD_DOWN));
		down = super.assignInputIndex();
	}
	public int getConfirm(){
		return confirm;
	}
	public int getCancel(){
		return cancel;
	}
	public int getLeft(){
		return left;
	}
	public int getRight(){
		return right;
	}
	public int getUp(){
		return up;
	}
	public int getDown(){
		return down;
	}
}