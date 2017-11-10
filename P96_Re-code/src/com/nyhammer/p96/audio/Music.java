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
	private MusicPart[] parts;
	private int activePart;
	private IntBuffer buffers;
	private int source;
	private int channels;
	private int sampleRate;
	private int format;
	private final ShortBuffer pcm;
	private boolean playing;
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
		parts = new MusicPart[filenames.length];
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
		activePart = 0;
		pcm = BufferUtils.createShortBuffer(BUFFER_SIZE);
	}
	public int getPartLengthInSamples(int part){
		return parts[part].lengthInSamples;
	}
	public float getPartLengthInSeconds(int part){
		return parts[part].lengthInSeconds;
	}
	public boolean isPartLooping(int part){
		return parts[part].looping;
	}
	public void setPartLooping(int part, boolean loop){
		parts[part].looping = loop;
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
	public float getVolume(){
		return alGetSourcef(source, AL_GAIN);
	}
	public void setVolume(float volume){
		alSourcef(source, AL_GAIN, volume);
	}
	public void play(){
		if(playing){
			return;
		}
		for(int i = 0; i < buffers.limit(); i++){
			if(stream() == 0){
				ErrorHandler.printError("Error in music-stream!", true);
				GameWindow.close();
			}
			alBufferData(buffers.get(i), format, pcm, sampleRate);
		}
		alSourceQueueBuffers(source, buffers);
		alSourcePlay(source);
		playing = true;
	}
	public void pause(){
		if(!playing){
			return;
		}
		alSourcePause(source);
		playing = false;
	}
	public void stop(){
		if(!playing){
			return;
		}
		alSourceStop(source);
		int buffersQueued = alGetSourcei(source, AL_BUFFERS_QUEUED);
		for(int i = 0; i < buffersQueued; i++){
			alSourceUnqueueBuffers(source);
		}
		playing = false;
	}
	private int stream(){
		return stb_vorbis_get_samples_short_interleaved(parts[activePart].decoder, channels, pcm);
	}
	private void rewind(){
		stb_vorbis_seek_start(parts[activePart].decoder);
	}
	public void update(){
		if(!playing){
			return;
		}
		int processed = alGetSourcei(source, AL_BUFFERS_PROCESSED);
		for(int i = 0; i < processed; i++){
			int buffer = alSourceUnqueueBuffers(source);
			pcm.position(0);
			int samplesPerChannel;
			if((samplesPerChannel = stream()) != BUFFER_SIZE / channels){
				pcm.position(samplesPerChannel * channels);
				if(parts[activePart].looping){
					rewind();
					stream();
				}
				else{
					rewind();
					activePart++;
					if(activePart < parts.length){
						stream();
					}
					else{
						activePart = 0;
						playing = false;
						pcm.position(0);
						return;
					}
				}
			}
			pcm.position(0);
			alBufferData(buffer, format, pcm, sampleRate);
			alSourceQueueBuffers(source, buffer);
		}
		if(processed == 2){
			alSourcePlay(source);
		}
	}
	public void dispose(){
		stop();
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
		private boolean looping;
		public MusicPart(String filename){
			try{
				vorbis = ResourceLoader.resourceToByteBuffer("assets/audio/bgm/" + filename, 256 * 1024);
			}
			catch(IOException e){
				ErrorHandler.printError("Could not load music-file: assets/audio/bgm/" + filename, true);
				ErrorHandler.printError(e);
				GameWindow.close();
			}
			IntBuffer error = BufferUtils.createIntBuffer(1);
			decoder = stb_vorbis_open_memory(vorbis, error, null);
			if(decoder == NULL){
				ErrorHandler.printError("Could not load music-file: assets/audio/bgm/" + filename, true);
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
			looping = true;
		}
	}
}