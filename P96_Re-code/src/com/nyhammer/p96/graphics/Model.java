package com.nyhammer.p96.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;

public class Model{
	private int vao;
	private int faceVbo;
	private int indexCount;
	private List<Integer> vbos = new ArrayList<Integer>();
	public Model(){
		vao = glGenVertexArrays();
		bind();
	}
	public int getVAO(){
		return vao;
	}
	public int getIndexCount(){
		return indexCount;
	}
	public void bind(){
		glBindVertexArray(vao);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, faceVbo);
	}
	public static void unbind(){
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
	}
	public void setFaces(int[] data){
		faceVbo = glGenBuffers();
		IntBuffer buffer = createIntBuffer(data);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, faceVbo);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		indexCount = data.length;
	}
	public void addAttrib(int index, int size, float[] data){
		int vboID = glGenBuffers();
		vbos.add(vboID);
		FloatBuffer buffer = createFloatBuffer(data);
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		glVertexAttribPointer(index, size, GL_FLOAT, false, 0, 0L);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	private FloatBuffer createFloatBuffer(float[] data){
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	private IntBuffer createIntBuffer(int[] data){
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	public void dispose(){
		for(int vboID : vbos){
			glDeleteBuffers(vboID);
		}
		glDeleteBuffers(faceVbo);
		glDeleteVertexArrays(vao);
	}
}