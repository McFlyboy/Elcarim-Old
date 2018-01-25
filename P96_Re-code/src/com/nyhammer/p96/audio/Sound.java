package com.nyhammer.p96.audio;

import static org.lwjgl.openal.AL10.*;

import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBVorbis;
import org.lwjgl.stb.STBVorbisInfo;
import org.lwjgl.system.MemoryUtil;

import com.nyhammer.p96.ErrorHandler;
import com.nyhammer.p96.ui.GameWindow;

public class Sound{
	private int buffer;
	private int source;
	private float length;
	public Sound(String filename){
		STBVorbisInfo info = STBVorbisInfo.malloc();
		ShortBuffer pcm = readVorbis(filename, info);
		buffer = alGenBuffers();
		if(alGetError() != AL_NO_ERROR){
			ErrorHandler.printError("Return at bufferGen! Sound-file is: " + filename);
			return;
		}
		source = alGenSources();
		if(alGetError() != AL_NO_ERROR){
			ErrorHandler.printError("Return at sourceGen! Sound-file is: " + filename);
			return;
		}
		alBufferData(buffer, AL_FORMAT_STEREO16, pcm, info.sample_rate());
		if(alGetError() != AL_NO_ERROR){
			ErrorHandler.printError("Return at bufferData! Sound-file is: " + filename);
			return;
		}
		info.free();
		alSourcei(source, AL_BUFFER, buffer);
		if(alGetError() != AL_NO_ERROR){
			ErrorHandler.printError("Return at source-binding! Sound-file is: " + filename);
			return;
		}
	}
	public float getLength(){
		return length;
	}
	public float getVolume(){
		return alGetSourcef(source, AL_GAIN);
	}
	public void setVolume(float volume){
		alSourcef(source, AL_GAIN, volume);
	}
	public void setLooping(boolean looping){
		alSourcei(source, AL_LOOPING, looping ? AL_TRUE : AL_FALSE);
	}
	public void play(){
		alSourcePlay(source);
	}
	public void pause(){
		alSourcePause(source);
	}
	public void stop(){
		alSourceStop(source);
	}
	public void dispose(){
		stop();
		alDeleteSources(source);
		alDeleteBuffers(buffer);
	}
	private ShortBuffer readVorbis(String filename, STBVorbisInfo info){
		IntBuffer error = BufferUtils.createIntBuffer(1);
		long decoder = STBVorbis.stb_vorbis_open_filename("assets/audio/sfx/" + filename, error, null);
		if(decoder == MemoryUtil.NULL){
			ErrorHandler.printError("Could not load audio from file: assets/audio/sfx/" + filename + "\nError: " + error.get(), true);
			ErrorHandler.printError(new RuntimeException());
			GameWindow.close();
		}
		STBVorbis.stb_vorbis_get_info(decoder, info);
		int channels = info.channels();
		int lengthSamples = STBVorbis.stb_vorbis_stream_length_in_samples(decoder) * channels;
		length = STBVorbis.stb_vorbis_stream_length_in_seconds(decoder);
		ShortBuffer pcm = BufferUtils.createShortBuffer(lengthSamples);
		STBVorbis.stb_vorbis_get_samples_short_interleaved(decoder, channels, pcm);
		STBVorbis.stb_vorbis_close(decoder);
		return pcm;
	}
}