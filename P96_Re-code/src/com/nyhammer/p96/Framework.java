package com.nyhammer.p96;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;

import static org.lwjgl.glfw.GLFW.*;

public class Framework{
	public static String getLWJGLVersion(){
		return Version.getVersion();
	}
	public static boolean init(){
		GLFWErrorCallback.createPrint(ErrorHandler.getStream()).set();
		if(!glfwInit()){
			ErrorHandler.printError("Could not initialize GLFW!", true);
			ErrorHandler.printError(new IllegalStateException());
			return false;
		}
		return true;
	}
	public static void terminate(){
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}
}