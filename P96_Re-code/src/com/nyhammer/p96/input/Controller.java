package com.nyhammer.p96.input;

import static org.lwjgl.glfw.GLFW.*;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

public class Controller{
	/** Controller buttons */
	public static final int
		BUTTON_A          = 0,
		BUTTON_B          = 1,
		BUTTON_X          = 2,
		BUTTON_Y          = 3,
		BUTTON_LB         = 4,
		BUTTON_RB         = 5,
		BUTTON_BACK       = 6,
		BUTTON_START      = 7,
		BUTTON_LS         = 8,
		BUTTON_RS         = 9,
		BUTTON_DPAD_UP    = 10,
		BUTTON_DPAD_RIGHT = 11,
		BUTTON_DPAD_DOWN  = 12,
		BUTTON_DPAD_LEFT  = 13;
	/** Controller axes */
	public static final int
		AXIS_LX = 0,
		AXIS_LY = 1,
		AXIS_RX = 2,
		AXIS_RY = 3,
		AXIS_LT = 4,
		AXIS_RT = 5;
	/** Button-states. */
	public static final int
		BUTTON_PRESSED                      = 3,
		BUTTON_RELEASED                     = 2,
		BUTTON_UNCHANGED_FROM_PRESS         = 1,
		BUTTON_UNCHANGED_FROM_RELEASE       = 0;
	private static int[] buttons = new int[14];
	private static float[] axes = new float[6];
	private static float innerThreshold = 0.2f;
	private static float outerThreshold = 0.8f;
	public static String getName(){
		return glfwGetJoystickName(GLFW_JOYSTICK_1);
	}
	public static boolean isPresent(){
		return glfwJoystickPresent(GLFW_JOYSTICK_1);
	}
	private static int getButtonState(int buttonID){
		return buttons[buttonID];
	}
	public static boolean isButtonPressed(int buttonID){
		return getButtonState(buttonID) == BUTTON_PRESSED;
	}
	public static boolean isButtonDown(int buttonID){
		int buttonState = getButtonState(buttonID);
		return buttonState == BUTTON_PRESSED | buttonState == BUTTON_UNCHANGED_FROM_PRESS;
	}
	public static float getAxisState(int axisID){
		return axes[axisID];
	}
	public static float getInnerThreshold(){
		return innerThreshold;
	}
	public static void setInnerThreshold(float innerThreshold){
		Controller.innerThreshold = innerThreshold;
	}
	public static float getOuterThreshold(){
		return outerThreshold;
	}
	public static void setOuterThreshold(float outerThreshold){
		Controller.outerThreshold = outerThreshold;
	}
	public static void updateButtonStates(){
		ByteBuffer buttonStates = glfwGetJoystickButtons(GLFW_JOYSTICK_1);
		if(buttonStates == null){
			return;
		}
		int i = 0;
		while(buttonStates.hasRemaining()){
			int state = buttonStates.get();
			if(state == GLFW_PRESS){
				if(buttons[i] != BUTTON_PRESSED && buttons[i] != BUTTON_UNCHANGED_FROM_PRESS){
					buttons[i] = BUTTON_PRESSED;
				}
				else{
					buttons[i] = BUTTON_UNCHANGED_FROM_PRESS;
				}
			}
			else{
				if(buttons[i] != BUTTON_RELEASED && buttons[i] != BUTTON_UNCHANGED_FROM_RELEASE){
					buttons[i] = BUTTON_RELEASED;
				}
				else{
					buttons[i] = BUTTON_UNCHANGED_FROM_RELEASE;
				}
			}
			i++;
		}
	}
	public static void updateAxisStates(){
		FloatBuffer axisStates = glfwGetJoystickAxes(GLFW_JOYSTICK_1);
		if(axisStates == null){
			return;
		}
		int i = 0;
		while(axisStates.hasRemaining()){
			float state = axisStates.get();
			if(i < 4){
				if(Math.abs(state) < innerThreshold){
					state = 0f;
				}
				if(state > outerThreshold){
					state = 1;
				}
				if(state < -outerThreshold){
					state = -1;
				}
			}
			axes[i] = state;
			i++;
		}
	}
	public static void reset(){
		for(int i = 0; i < buttons.length; i++){
			buttons[i] = BUTTON_UNCHANGED_FROM_RELEASE;
		}
	}
}