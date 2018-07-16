package com.nyhammer.p96;

import java.util.Random;

import com.nyhammer.p96.audio.AudioSystem;
import com.nyhammer.p96.audio.Sound;
import com.nyhammer.p96.graphics.Render;
import com.nyhammer.p96.graphics.font.TextFont;
import com.nyhammer.p96.input.Gamepad;
import com.nyhammer.p96.input.Keyboard;
import com.nyhammer.p96.input.Mouse;
import com.nyhammer.p96.structure.ControlScheme;
import com.nyhammer.p96.structure.ResourceStorage;
import com.nyhammer.p96.structure.scenes.UniverseScene;
import com.nyhammer.p96.ui.GameWindow;
import com.nyhammer.p96.util.timing.DeltaTimer;
import com.nyhammer.p96.util.timing.Time;

public class Game {
	public static final int VERSION_MAJOR = 0;
	public static final int VERSION_MINOR = 3;
	public static final int VERSION_REVISION = 1;
	public static final String PRE_VERSION_SUFFIX = "a";
	public static final int VERSION_PATCH = 0;
	public static final String TITLE = "Project 1996: Elcarim";
	private DeltaTimer systemDelta;
	private static final Random RANDOM = new Random();
	private UniverseScene universeScene;
	public static String getVersion() {
		StringBuilder version = new StringBuilder();
		version.append(String.format("%d.%d.%d%s", VERSION_MAJOR, VERSION_MINOR, VERSION_REVISION, PRE_VERSION_SUFFIX));
		String patch = String.format("%02d", VERSION_PATCH);
		if(!patch.equals("00")) {
			version.append(" - patch: " + patch);
		}
		return version.toString();
	}
	public static Random getRandom() {
		return RANDOM;
	}
	private void start() {
		ErrorHandler.init();
		try {
			Settings.init();
			Settings.readFromFile();
			Settings.writeToFile();
			if(!Framework.init()) {
				stop();
			}
			boolean fullscreen = Settings.getValue("fullscreen").equals("true");
			boolean vsync = Settings.getValue("vsync").equals("true");
			GameWindow.create(fullscreen);
			GameWindow.setVSync(vsync);
			Keyboard.create();
			Mouse.setCursorState(Mouse.CURSOR_HIDDEN);
			ControlScheme.setActiveInput(Gamepad.isPresent() ? ControlScheme.ActiveInput.ACTIVE_GAMEPAD : ControlScheme.ActiveInput.ACTIVE_KEYBOARD);
			AudioSystem.init();
			ResourceStorage.add("font", new TextFont("consola.ttf", 28));
			Sound bootingSound = new Sound("system/booting.ogg");
			ResourceStorage.add("bootingSound", bootingSound);
			//bootingSound.play();
			Render.setAlphaBlend(true);
			Render.init();
			universeScene = new UniverseScene();
			Time.init();
			systemDelta = new DeltaTimer();
			universeScene.start();
		}
		catch(Exception e) {
			ErrorHandler.printError("Error in game-startup!", true);
			ErrorHandler.printError(e);
			stop();
		}
		try {
			run();
		}
		catch(Exception e) {
			ErrorHandler.printError("An error has occured!", true);
			ErrorHandler.printError(e);
			stop();
		}
	}
	private void run() {
		double targetFrameTime = 1.0 / GameWindow.getMonitorRefreshRate();
		double renderTimeRemaining = 0.0;
		while(!GameWindow.shouldClose()) {
			boolean renderReady = false;
			update();
			if(renderTimeRemaining <= 0.0) {
				renderReady = true;
				renderTimeRemaining += targetFrameTime;
			}
			renderTimeRemaining -= systemDelta.getTime();
			if(renderReady) {
				render();
			}
			else {
				try {
					Thread.sleep(1L);
				}
				catch(InterruptedException e) {
					ErrorHandler.printError("Error caused by thread being unable to sleep!", false);
					ErrorHandler.printError(e);
				}
			}
		}
		stop();
	}
	private void update() {
		Gamepad.update();
		universeScene.update();
	}
	private void render() {
		universeScene.render();
		Render.renderQueue();
		GameWindow.update();
		Time.updateFPS();
	}
	private void stop() {
		boolean shutdownMessagePrinted = false;
		try {
			universeScene.dispose();
		}
		catch(Exception e) {
			if(!shutdownMessagePrinted) {
				ErrorHandler.printError("\n------------------------ SHUTTING DOWN!!! ------------------------\n");
				shutdownMessagePrinted = true;
			}
			ErrorHandler.printError("Error while disposing the universe-scene!");
			ErrorHandler.printError(e);
		}
		
		ResourceStorage.disposeSound("bootingSound");
		ResourceStorage.disposeTextFont("font");
		ResourceStorage.disposeAll();
		
		try {
			Render.terminate();
		}
		catch(Exception e) {
			if(!shutdownMessagePrinted) {
				ErrorHandler.printError("\n------------------------ SHUTTING DOWN!!! ------------------------\n");
				shutdownMessagePrinted = true;
			}
			ErrorHandler.printError("Error while terminating the rendering-engine!");
			ErrorHandler.printError(e);
		}
		
		try {
			AudioSystem.terminate();
		}
		catch(Exception e) {
			if(!shutdownMessagePrinted) {
				ErrorHandler.printError("\n------------------------ SHUTTING DOWN!!! ------------------------\n");
				shutdownMessagePrinted = true;
			}
			ErrorHandler.printError("Error while terminating the audio-system!");
			ErrorHandler.printError(e);
		}
		
		try {
			Keyboard.destroy();
		}
		catch(Exception e) {
			if(!shutdownMessagePrinted) {
				ErrorHandler.printError("\n------------------------ SHUTTING DOWN!!! ------------------------\n");
				shutdownMessagePrinted = true;
			}
			ErrorHandler.printError("Error while disposing the keyboard-listener!");
			ErrorHandler.printError(e);
		}
		
		try {
			GameWindow.destroy();
		}
		catch(Exception e) {
			if(!shutdownMessagePrinted) {
				ErrorHandler.printError("\n------------------------ SHUTTING DOWN!!! ------------------------\n");
				shutdownMessagePrinted = true;
			}
			ErrorHandler.printError("Error while closing the window!");
			ErrorHandler.printError(e);
		}
		
		try {
			Framework.terminate();
		}
		catch(Exception e) {
			if(!shutdownMessagePrinted) {
				ErrorHandler.printError("\n------------------------ SHUTTING DOWN!!! ------------------------\n");
				shutdownMessagePrinted = true;
			}
			ErrorHandler.printError("Error while terminating GLFW!");
			ErrorHandler.printError(e);
		}
		ErrorHandler.terminate();
		System.exit(0);
	}
	public static void main(String[] args) {
		new Game().start();
	}
}
