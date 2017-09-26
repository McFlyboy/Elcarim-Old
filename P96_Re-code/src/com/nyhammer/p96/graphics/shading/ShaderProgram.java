package com.nyhammer.p96.graphics.shading;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import com.nyhammer.p96.ErrorHandler;
import com.nyhammer.p96.ui.GameWindow;
import com.nyhammer.p96.util.io.TextIO;
import com.nyhammer.p96.util.math.vector.Vector2f;

public abstract class ShaderProgram{
	private final int vertexShaderID;
	private final int fragmentShaderID;
	private final int programID;
	public ShaderProgram(String vshFilename, String fshFilename){
		vertexShaderID = loadShader(vshFilename, GL_VERTEX_SHADER);
		fragmentShaderID = loadShader(fshFilename, GL_FRAGMENT_SHADER);
		programID = glCreateProgram();
		glAttachShader(programID, vertexShaderID);
		glAttachShader(programID, fragmentShaderID);
		bindAttribs();
		glLinkProgram(programID);
		glValidateProgram(programID);
		getUniformLocations();
	}
	public void start(){
		glUseProgram(programID);
	}
	public static void stop(){
		glUseProgram(0);
	}
	protected void loadInt(int location, int i){
		glUniform1i(location, i);
	}
	protected void loadFloat(int location, float f){
		glUniform1f(location, f);
	}
	protected void loadBoolean(int location, boolean b){
		glUniform1i(location, b ? GL_TRUE : GL_FALSE);
	}
	protected void loadVector2f(int location, Vector2f vec){
		glUniform2f(location, vec.x, vec.y);
	}
	protected void loadVector3f(int location, float x, float y, float z){
		glUniform3f(location, x, y, z);
	}
	protected abstract void getUniformLocations();
	protected int getUniformLocation(String varName){
		return glGetUniformLocation(programID, varName);
	}
	protected abstract void bindAttribs();
	protected void bindAttrib(int index, String varName){
		glBindAttribLocation(programID, index, varName);
	}
	public static int loadShader(String filename, int type){
		String shaderSource = new TextIO(TextIO.RESOURCE, filename).readTextResourceWhole();
		int shaderID = glCreateShader(type);
		glShaderSource(shaderID, shaderSource);
		glCompileShader(shaderID);
		if(glGetShaderi(shaderID, GL_COMPILE_STATUS) != GL_TRUE){
			ErrorHandler.printError("Error in shader-source: " + filename, true);
			ErrorHandler.printError(glGetShaderInfoLog(shaderID, 500));
			GameWindow.close();
		}
		return shaderID;
	}
	public void dispose(){
		glDetachShader(programID, vertexShaderID);
		glDetachShader(programID, fragmentShaderID);
		glDeleteShader(vertexShaderID);
		glDeleteShader(fragmentShaderID);
		glDeleteProgram(programID);
	}
}