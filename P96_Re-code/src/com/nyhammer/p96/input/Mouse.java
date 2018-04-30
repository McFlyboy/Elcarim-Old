package com.nyhammer.p96.input;

import static org.lwjgl.glfw.GLFW.*;

import com.nyhammer.p96.ui.GameWindow;

public class Mouse {
	/** Cursor state. */
	public static final int
		CURSOR_NORMAL   = 0x34001,
		CURSOR_HIDDEN   = 0x34002,
		CURSOR_DISABLED = 0x34003;
	
	public static void setCursorState(int cursorState) {
		glfwSetInputMode(GameWindow.getWindow(), GLFW_CURSOR, cursorState);
	}
}
