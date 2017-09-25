package com.nyhammer.p96.ui;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import com.nyhammer.p96.ErrorHandler;
import com.nyhammer.p96.Main;

public class GameWindow{
	private static long monitor;
	private static GLFWVidMode vidMode;
	private static long window;
	private static int windowWidth;
	private static int windowHeight;
	private static int fullscreenAspectFixX;
	private static int fullscreenAspectFixY;
	private static int fullscreenAspectFixWidth;
	private static int fullscreenAspectFixHeight;
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
		glfwSetWindowMonitor(window, fullscreen ? monitor : NULL, 0, 0, getMonitorWidth(), getMonitorHeight(), fullscreen ? getMonitorRefreshRate() : GLFW_DONT_CARE);
		setViewPort(fullscreen);
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
	public static void setViewPort(boolean fullscreen){
		if(fullscreen){
			GL11.glViewport(fullscreenAspectFixX, fullscreenAspectFixY, fullscreenAspectFixWidth, fullscreenAspectFixHeight);
		}
		else{
			GL11.glViewport(0, 0, windowWidth, windowHeight);
		}
	}
	public static void create(boolean fullscreen) throws Exception{
		windowWidth = 1366;
		windowHeight = 768;
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
		monitor = glfwGetPrimaryMonitor();
		vidMode = glfwGetVideoMode(monitor);
		float monitorAspectRatio = getMonitorAspectRatio();
		if(monitorAspectRatio == 16f / 9f){
			fullscreenAspectFixX = 0;
			fullscreenAspectFixY = 0;
			fullscreenAspectFixWidth = getMonitorWidth();
			fullscreenAspectFixHeight = getMonitorHeight();
		}
		else if(monitorAspectRatio < 16f / 9f){
			int monitorWidth = getMonitorWidth();
			int monitorHeight = getMonitorHeight();
			int heightOffset = monitorHeight / 2 - 9 * monitorWidth / 32;
			fullscreenAspectFixX = 0;
			fullscreenAspectFixY = heightOffset;
			fullscreenAspectFixWidth = monitorWidth;
			fullscreenAspectFixHeight = monitorHeight - heightOffset;
		}
		else{
			int monitorWidth = getMonitorWidth();
			int monitorHeight = getMonitorHeight();
			int widthOffset = monitorWidth / 2 - 8 * monitorHeight / 9;
			fullscreenAspectFixX = widthOffset;
			fullscreenAspectFixY = 0;
			fullscreenAspectFixWidth = monitorWidth - widthOffset;
			fullscreenAspectFixHeight = monitorHeight;
		}
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
		setViewPort(fullscreen);
		glfwShowWindow(window);
	}
	public static void update(){
		glfwSwapBuffers(window);
		glfwPollEvents();
	}
	public static void close(){
		glfwSetWindowShouldClose(window, true);
	}
	public static void destroy(){
		glfwDestroyWindow(window);
	}
}