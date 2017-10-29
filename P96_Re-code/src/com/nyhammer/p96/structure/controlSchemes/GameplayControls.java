package com.nyhammer.p96.structure.controlSchemes;

import com.nyhammer.p96.input.Gamepad;
import com.nyhammer.p96.input.Keyboard;
import com.nyhammer.p96.structure.ControlScheme;

public class GameplayControls extends ControlScheme{
	private int shootLeft;
	private int shootRight;
	private int hit;
	private int miracle;
	private int jump;
	private int moveLeft;
	private int moveRight;
	private int moveUp;
	private int moveDown;
	private int moveAxisX;
	private int moveAxisY;
	public GameplayControls(){
		/** key/button-inputs */
		super.inputs.add(new Input(Keyboard.KEY_Z, Gamepad.BUTTON_LB));
		shootLeft = super.assignInputIndex();
		super.inputs.add(new Input(Keyboard.KEY_X, Gamepad.BUTTON_RB));
		shootRight = super.assignInputIndex();
		super.inputs.add(new Input(Keyboard.KEY_C, Gamepad.BUTTON_X));
		hit = super.assignInputIndex();
		super.inputs.add(new Input(Keyboard.KEY_A, Gamepad.BUTTON_Y));
		miracle = super.assignInputIndex();
		super.inputs.add(new Input(Keyboard.KEY_SPACE, Gamepad.BUTTON_A));
		jump = super.assignInputIndex();
		super.inputs.add(new Input(Keyboard.KEY_LEFT));
		moveLeft = super.assignInputIndex();
		super.inputs.add(new Input(Keyboard.KEY_RIGHT));
		moveRight = super.assignInputIndex();
		super.inputs.add(new Input(Keyboard.KEY_UP));
		moveUp = super.assignInputIndex();
		super.inputs.add(new Input(Keyboard.KEY_DOWN));
		moveDown = super.assignInputIndex();
		
		/** axis-inputs */
		super.axisInputs.add(new AxisInput(Gamepad.AXIS_LX));
		moveAxisX = super.assignAxisInputIndex();
		super.axisInputs.add(new AxisInput(Gamepad.AXIS_LY));
		moveAxisY = super.assignAxisInputIndex();
	}
	public int getShootLeft(){
		return shootLeft;
	}
	public int getShootRight(){
		return shootRight;
	}
	public int getHit(){
		return hit;
	}
	public int getMiracle(){
		return miracle;
	}
	public int getJump(){
		return jump;
	}
	public int getMoveLeft(){
		return moveLeft;
	}
	public int getMoveRight(){
		return moveRight;
	}
	public int getMoveUp(){
		return moveUp;
	}
	public int getMoveDown(){
		return moveDown;
	}
	public int getMoveAxisX(){
		return moveAxisX;
	}
	public int getMoveAxisY(){
		return moveAxisY;
	}
}