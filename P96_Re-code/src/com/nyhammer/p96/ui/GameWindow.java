package com.nyhammer.p96.ui;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import com.nyhammer.p96.ErrorHandler;
import com.nyhammer.p96.Main;
import com.nyhammer.p96.Settings;
import com.nyhammer.p96.graphics.Render;

public class GameWindow{
	public static final float ASPECT_RATIO = 16f / 9f;
	private static long monitor;
	private static GLFWVidMode vidMode;
	private static long window;
	private static int windowWidth;
	private static int windowHeight;
	private static boolean vsync;
	public static long getWindow(){
		return window;
	}
	public static int getMonitorWidth(){
		return vidMode.width();
	}
	public static int getMonitorHeight(){
		return vidMode.height();
	}
	public static float getMonitorAspectRatio(){
		return (float)vidMode.width() / (float)vidMode.height();
	}
	public static int getMonitorRefreshRate(){
		return vidMode.refreshRate(); 
	}
	public static boolean shouldClose(){
		return glfwWindowShouldClose(window);
	}
	public static boolean isFullscreen(){
		return glfwGetWindowMonitor(window) != NULL;
	}
	public static void setFullscreen(boolean fullscreen){
		glfwSetWindowMonitor(window, fullscreen ? monitor : NULL, 0, 0, fullscreen ? getMonitorWidth() : 1280, fullscreen ? getMonitorHeight() : 720, fullscreen ? getMonitorRefreshRate() : GLFW_DONT_CARE);
		setViewPort();
		String value;
		if(!fullscreen){
			center();
			value = "false";
		}
		else{
			value = "true";
		}
		Settings.setValue("fullscreen", value);
		Settings.writeToFile();
	}
	public static boolean isVSync(){
		return vsync;
	}
	public static void setVSync(boolean vsync){
		GameWindow.vsync = vsync;
		glfwSwapInterval(vsync ? 1 : 0);
	}
	public static void center(){
		glfwSetWindowPos(window, (getMonitorWidth() - windowWidth) / 2, (getMonitorHeight() - windowHeight) / 2);
	}
	public static void setViewPort(){
		IntBuffer widthBuffer = BufferUtils.createIntBuffer(1);
		IntBuffer heightBuffer = BufferUtils.createIntBuffer(1);
		glfwGetWindowSize(window, widthBuffer, heightBuffer);
		int width = widthBuffer.get();
		int height = heightBuffer.get();
		float aspectRatio = (float)width / (float)height;
		int aspectFixX;
		int aspectFixY;
		int aspectFixWidth;
		int aspectFixHeight;
		if(aspectRatio == 16f / 9f){
			aspectFixX = 0;
			aspectFixY = 0;
			aspectFixWidth = width;
			aspectFixHeight = height;
		}
		else if(aspectRatio < 16f / 9f){
			int heightOffset = height / 2 - 9 * width / 32;
			aspectFixX = 0;
			aspectFixY = heightOffset;
			aspectFixWidth = width;
			aspectFixHeight = height - heightOffset * 2;
		}
		else{
			int widthOffset = width / 2 - 8 * height / 9;
			aspectFixX = widthOffset;
			aspectFixY = 0;
			aspectFixWidth = width - widthOffset * 2;
			aspectFixHeight = height;
		}
		GL11.glViewport(aspectFixX, aspectFixY, aspectFixWidth, aspectFixHeight);
	}
	public static void create(boolean fullscreen) throws Exception{
		windowWidth = 1280;
		windowHeight = 720;
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
		monitor = glfwGetPrimaryMonitor();
		vidMode = glfwGetVideoMode(monitor);
		if(fullscreen){
			window = glfwCreateWindow(vidMode.width(), vidMode.height(), Main.TITLE, monitor, NULL);
		}
		else{
			window = glfwCreateWindow(windowWidth, windowHeight, Main.TITLE, NULL, NULL);
		}
		if(window == NULL){
			ErrorHandler.printError("Could not create the window!", true);
			throw new RuntimeException();
		}
		if(!fullscreen){
			center();
		}
		glfwMakeContextCurrent(window);
		GL.createCapabilities();
		setViewPort();
		glfwShowWindow(window);
	}
	public static void update(){
		glfwSwapBuffers(window);
		glfwPollEvents();
		Render.clear();
	}
	public static void close(){
		glfwSetWindowShouldClose(window, true);
	}
	public static void destroy(){
		glfwDestroyWindow(window);
	}
}