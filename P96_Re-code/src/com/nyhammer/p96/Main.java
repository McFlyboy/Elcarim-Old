package com.nyhammer.p96;

import com.nyhammer.p96.input.Keyboard;
import com.nyhammer.p96.input.Mouse;
import com.nyhammer.p96.ui.GameWindow;
import com.nyhammer.p96.util.timing.Time;

public class Main{
	public static final int VERSION_MAJOR = 0;
	public static final int VERSION_MINOR = 2;
	public static final int VERSION_REVISION = 1;
	public static final int VERSION_PATCH = 0;
	public static final String PRE_VERSION_SUFFIX = "a";
	public static final String TITLE = "Project 1996";
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
			Keyboard.create();
			Mouse.setCursorState(Mouse.CURSOR_HIDDEN);
			Time.init();
			run();
		}
		catch(Exception e){
			ErrorHandler.printError("Error in game-startup!", true);
			ErrorHandler.printError(e);
			stop();
		}
	}
	private void run(){
		while(!GameWindow.shouldClose()){
			update();
			render();
		}
		stop();
	}
	private void update(){
		
	}
	private void render(){
		GameWindow.update();
		Time.updateFPS();
	}
	private void stop(){
		try{
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