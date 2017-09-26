package com.nyhammer.p96;

import com.nyhammer.p96.graphics.Render;
import com.nyhammer.p96.input.Keyboard;
import com.nyhammer.p96.input.Mouse;
import com.nyhammer.p96.structure.scenes.GlobalScene;
import com.nyhammer.p96.ui.GameWindow;
import com.nyhammer.p96.util.timing.DeltaTimer;
import com.nyhammer.p96.util.timing.Time;

public class Main{
	public static final int VERSION_MAJOR = 0;
	public static final int VERSION_MINOR = 2;
	public static final int VERSION_REVISION = 1;
	public static final int VERSION_PATCH = 0;
	public static final String PRE_VERSION_SUFFIX = "a";
	public static final String TITLE = "Project 1996";
	private DeltaTimer systemDelta;
	private GlobalScene globalScene;
	public static String getVersion(){
		StringBuilder version = new StringBuilder();
		version.append(String.format("%d.%d.%d", VERSION_MAJOR, VERSION_MINOR, VERSION_REVISION));
		String patch = String.format("%02d", VERSION_PATCH);
		if(!patch.equals("00")){
			version.append("_" + patch);
		}
		version.append(PRE_VERSION_SUFFIX);
		return version.toString();
	}
	private void start(){
		ErrorHandler.init();
		try{
			if(!Framework.init()){
				stop();
			}
			GameWindow.create(false);
			GameWindow.setVSync(false);
			Keyboard.create();
			Mouse.setCursorState(Mouse.CURSOR_HIDDEN);
			Render.setClearColor(0.3f, 0.3f, 0.4f);
			Render.setAlphaBlend(true);
			globalScene = new GlobalScene();
			Time.init();
			systemDelta = new DeltaTimer();
			globalScene.start();
			run();
		}
		catch(Exception e){
			ErrorHandler.printError("Error in game-startup!", true);
			ErrorHandler.printError(e);
			stop();
		}
	}
	private void run(){
		double targetFrameTime = 1.0 / GameWindow.getMonitorRefreshRate();
		double renderTimeRemaining = 0.0;
		while(!GameWindow.shouldClose()){
			boolean renderReady = false;
			update();
			if(renderTimeRemaining <= 0.0){
				renderReady = true;
				renderTimeRemaining += targetFrameTime;
			}
			renderTimeRemaining -= systemDelta.getTime();
			if(renderReady){
				render();
			}
			else{
				try{
					Thread.sleep(1L);
				}
				catch(InterruptedException e){
					ErrorHandler.printError("Error caused by thread being unable to sleep!", false);
					ErrorHandler.printError(e);
				}
			}
		}
		stop();
	}
	private void update(){
		globalScene.update();
	}
	private void render(){
		globalScene.render();
		GameWindow.update();
		Time.updateFPS();
	}
	private void stop(){
		try{
			globalScene.dispose();
			Keyboard.destroy();
			GameWindow.destroy();
			Framework.terminate();
		}
		catch(Exception e){
			ErrorHandler.printError("\n------------------------ SHUTTING DOWN!!! ------------------------\n");
			ErrorHandler.printError("Error in game-shutdown!");
			ErrorHandler.printError(e);
		}
		ErrorHandler.terminate();
		System.exit(0);
	}
	public static void main(String[] args){
		new Main().start();
	}
}