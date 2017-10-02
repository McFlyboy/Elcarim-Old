package com.nyhammer.p96.util.io;

import static org.lwjgl.BufferUtils.createByteBuffer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.lwjgl.BufferUtils;

public class ResourceLoader{
	private static ByteBuffer resizeBuffer(ByteBuffer buffer, int newCapacity){
        ByteBuffer newBuffer = BufferUtils.createByteBuffer(newCapacity);
        buffer.flip();
        newBuffer.put(buffer);
        return newBuffer;
    }
	public static ByteBuffer resourceToByteBuffer(String resource, int bufferSize) throws IOException{
		ByteBuffer buffer;
		Path path = Paths.get(resource);
		if(Files.isReadable(path)){
			try(SeekableByteChannel fileChannel = Files.newByteChannel(path)){
				buffer = BufferUtils.createByteBuffer((int)fileChannel.size() + 1);
				while(true){
					int bytes = fileChannel.read(buffer);
					if(bytes == -1){
						break;
					}
				}
			}
		}
		else{
			try(
					InputStream source = Class.class.getClassLoader().getResourceAsStream(resource);
					ReadableByteChannel rbc = Channels.newChannel(source)
			){
				buffer = createByteBuffer(bufferSize);
				while(true){
					int bytes = rbc.read(buffer);
					if(bytes == -1){
                        break;
                    }
					if(buffer.remaining() == 0){
						buffer = resizeBuffer(buffer, buffer.capacity() * 2);
					}
				}
			}
		}
		buffer.flip();
		return buffer;
	}
}