package com.nyhammer.p96.util.timing;

import org.lwjgl.glfw.GLFW;

public class Time {
	private static int fps;
	private static int fpsCount;
	public static TargetTimer fpsTimer;
	public static void init() {
		fpsTimer = new TargetTimer(1.0);
		fpsTimer.start();
	}
	public static double getTime() {
		return GLFW.glfwGetTime();
	}
	public static int getFPS() {
		return fps;
	}
	public static void updateFPS() {
		if(fpsTimer.targetReached()) {
			fps = fpsCount;
			fpsCount = 0;
		}
		fpsCount++;
	}
}
