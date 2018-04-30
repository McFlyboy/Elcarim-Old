package com.nyhammer.p96.graphics.shading;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import com.nyhammer.p96.ErrorHandler;
import com.nyhammer.p96.ui.GameWindow;
import com.nyhammer.p96.util.Color3f;
import com.nyhammer.p96.util.io.TextIO;
import com.nyhammer.p96.util.math.vector.Vector2f;

public abstract class ShaderProgram {
	private final int vertexShader;
	private final int fragmentShader;
	private final int program;
	public ShaderProgram(String vshFilename, String fshFilename) {
		vertexShader = loadShader(vshFilename, GL_VERTEX_SHADER);
		fragmentShader = loadShader(fshFilename, GL_FRAGMENT_SHADER);
		program = glCreateProgram();
		glAttachShader(program, vertexShader);
		glAttachShader(program, fragmentShader);
		bindAttribs();
		glLinkProgram(program);
		glValidateProgram(program);
		getUniformLocations();
	}
	public void start() {
		glUseProgram(program);
	}
	public static void stop() {
		glUseProgram(0);
	}
	protected void loadInt(int location, int i) {
		glUniform1i(location, i);
	}
	protected void loadFloat(int location, float f) {
		glUniform1f(location, f);
	}
	protected void loadBoolean(int location, boolean b) {
		glUniform1i(location, b ? GL_TRUE : GL_FALSE);
	}
	protected void loadVector2f(int location, Vector2f vec) {
		glUniform2f(location, vec.x, vec.y);
	}
	protected void loadColor3f(int location, Color3f color) {
		glUniform3f(location, color.red, color.green, color.blue);
	}
	protected abstract void getUniformLocations();
	protected int getUniformLocation(String varName) {
		return glGetUniformLocation(program, varName);
	}
	protected abstract void bindAttribs();
	protected void bindAttrib(int index, String varName) {
		glBindAttribLocation(program, index, varName);
	}
	public static int loadShader(String filename, int type) {
		String filepath = "/shaders/" + filename;
		String shaderSource = new TextIO(TextIO.RESOURCE, filepath).readTextResourceWhole();
		int shaderID = glCreateShader(type);
		glShaderSource(shaderID, shaderSource);
		glCompileShader(shaderID);
		if(glGetShaderi(shaderID, GL_COMPILE_STATUS) != GL_TRUE) {
			ErrorHandler.printError("Error in shader-source: " + filepath, true);
			ErrorHandler.printError(glGetShaderInfoLog(shaderID, 500));
			GameWindow.close();
		}
		return shaderID;
	}
	public void dispose() {
		glDetachShader(program, vertexShader);
		glDetachShader(program, fragmentShader);
		glDeleteShader(vertexShader);
		glDeleteShader(fragmentShader);
		glDeleteProgram(program);
	}
}
