package com.nyhammer.p96.audio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBVorbisInfo;

import com.nyhammer.p96.ErrorHandler;
import com.nyhammer.p96.ui.GameWindow;
import com.nyhammer.p96.util.io.ResourceLoader;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.openal.SOFTDirectChannels.*;
import static org.lwjgl.stb.STBVorbis.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Music{
	private static final int BUFFER_SIZE = 1024 * 4;
	private IntBuffer buffers;
	private int source;
	private int channels;
	private int sampleRate;
	private int format;
	private final ShortBuffer pcm;
	public Music(String filename){
		this(new String[]{
				filename
		});
	}
	public Music(String[] filenames){
		buffers = BufferUtils.createIntBuffer(2);
		alGenBuffers(buffers);
		source = alGenSources();
		alSourcei(source, AL_DIRECT_CHANNELS_SOFT, AL_TRUE);
		MusicPart[] parts = new MusicPart[filenames.length];
		for(int i = 0; i < filenames.length; i++){
			parts[i] = new MusicPart(filenames[i]);
		}
		for(MusicPart part : parts){
			if(channels != 0){
				if(channels != part.channels){
					ErrorHandler.printError("Music-parts does not have same amount of channels!", true);
					GameWindow.close();
				}
			}
			else{
				channels = part.channels;
			}
			if(sampleRate != 0){
				if(sampleRate != part.sampleRate){
					ErrorHandler.printError("Music-parts does not have same sample-rate!", true);
					GameWindow.close();
				}
			}
			else{
				sampleRate = part.sampleRate;
			}
		}
		format = getFormat(channels);
		pcm = BufferUtils.createShortBuffer(BUFFER_SIZE);
	}
	private int getFormat(int channels){
		switch(channels){
			case 1:
				return AL_FORMAT_MONO16;
			case 2:
				return AL_FORMAT_STEREO16;
			default:
				ErrorHandler.printError("Unsupported number of channels: " + channels, true);
				GameWindow.close();
				return 0;
		}
	}
	public void dispose(){
		alDeleteBuffers(buffers);
        alDeleteSources(source);
	}
	private class MusicPart{
		private ByteBuffer vorbis;
		private final long decoder;
		private final int channels;
		private final int sampleRate;
		private final int lengthInSamples;
		private final float lengthInSeconds;
		private int samplesLeft;
		public MusicPart(String filename){
			try{
				vorbis = ResourceLoader.resourceToByteBuffer(filename, 256 * 1024);
			}
			catch(IOException e){
				ErrorHandler.printError("Could not load music-file: " + filename, true);
				ErrorHandler.printError(e);
				GameWindow.close();
			}
			IntBuffer error = BufferUtils.createIntBuffer(1);
			decoder = stb_vorbis_open_memory(vorbis, error, null);
			if(decoder == NULL){
				ErrorHandler.printError("Could not load music-file: " + filename, true);
				ErrorHandler.printError(new RuntimeException(String.format("%d", error.get(0))));
				GameWindow.close();
			}
			try(STBVorbisInfo info = STBVorbisInfo.malloc()){
				stb_vorbis_get_info(decoder, info);
				channels = info.channels();
				sampleRate = info.sample_rate();
			}
			lengthInSamples = stb_vorbis_stream_length_in_samples(decoder);
			lengthInSeconds = stb_vorbis_stream_length_in_seconds(decoder);
			samplesLeft = lengthInSamples;
		}
	}
}