package com.nyhammer.p96.structure;

import java.util.ArrayList;
import java.util.List;

import com.nyhammer.p96.input.Gamepad;
import com.nyhammer.p96.input.Keyboard;

public abstract class ControlScheme{
	private static ActiveInput activeInput;
	protected List<Input> inputs = new ArrayList<Input>();
	protected List<AxisInput> axisInputs = new ArrayList<AxisInput>();
	public static enum ActiveInput{
		ACTIVE_KEYBOARD, ACTIVE_GAMEPAD;
	}
	public static ActiveInput getActiveInput(){
		return activeInput;
	}
	public static void setActiveInput(ActiveInput activeInput){
		ControlScheme.activeInput = activeInput;
	}
	public boolean isPressed(int index){
		Input input = inputs.get(index);
		if(activeInput == ActiveInput.ACTIVE_GAMEPAD){
			return Gamepad.isButtonPressed(input.getButton());
		}
		else{
			return Keyboard.isKeyPressed(input.getKey());
		}
	}
	public boolean isDown(int inputIndex){
		Input input = inputs.get(inputIndex);
		if(activeInput == ActiveInput.ACTIVE_GAMEPAD){
			return Gamepad.isButtonDown(input.getButton());
		}
		else{
			return Keyboard.isKeyDown(input.getKey());
		}
	}
	public float getAxisState(int axisInputIndex){
		return Gamepad.getAxisState(axisInputs.get(axisInputIndex).getAxis());
	}
	protected int assignInputIndex(){
		return inputs.size() - 1;
	}
	protected int assignAxisInputIndex(){
		return axisInputs.size() - 1;
	}
	protected class Input{
		private int key;
		private int button;
		public Input(int key, int button){
			this.key = key;
			this.button = button;
		}
		public Input(int key){
			this.key = key;
		}
		public int getKey(){
			return key;
		}
		public int getButton(){
			return button;
		}
	}
	protected class AxisInput{
		private int axis;
		public AxisInput(int axis){
			this.axis = axis;
		}
		public int getAxis(){
			return axis;
		}
	}
}