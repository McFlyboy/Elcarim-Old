package com.nyhammer.p96.audio;

import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;

import com.nyhammer.p96.ErrorHandler;
import com.nyhammer.p96.ui.GameWindow;

public class AudioSystem{
	private static long device;
	private static long context;
	public static void init(){
		device = alcOpenDevice((ByteBuffer)null);
		if(device == NULL){
			ErrorHandler.printError("Could not open the default sound-device!", true);
			ErrorHandler.printError(new IllegalStateException());
			GameWindow.close();
		}
		ALCCapabilities deviceCaps = ALC.createCapabilities(device);
		context = alcCreateContext(device, (IntBuffer)null);
		if(context == NULL){
			ErrorHandler.printError("Could not create an OpenAL-context!", true);
			ErrorHandler.printError(new IllegalStateException());
			GameWindow.close();
		}
		alcMakeContextCurrent(context);
		AL.createCapabilities(deviceCaps);
	}
	public static void terminate(){
		if(device == NULL){
			return;
		}
		alcDestroyContext(context);
		alcCloseDevice(device);
	}
}