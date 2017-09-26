package com.nyhammer.p96.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;

import com.nyhammer.p96.graphics.shading.ShaderProgram;
import com.nyhammer.p96.graphics.shading.shaders.P96Shader;
import com.nyhammer.p96.util.math.vector.Vector2f;

public class Render{
	private static P96Shader shader;
	public static void init(){
		shader = new P96Shader();
		shader.start();
	}
	public static void render(Model model, Texture texture){
		model.bind();
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, texture.getTexture());
		shader.loadTransformation(new Vector2f(), 0f, new Vector2f(16f/9f, 1f));
		glDrawElements(GL_TRIANGLES, model.getIndexCount(), GL_UNSIGNED_INT, 0L);
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		Model.unbind();
	}
	public static void setClearColor(float red, float green, float blue){
		glClearColor(red, green, blue, 1f);
	}
	public static void clear(){
		glClear(GL_COLOR_BUFFER_BIT);
	}
	public static void setWireframe(boolean wireframe){
		glPolygonMode(GL_FRONT_AND_BACK, wireframe ? GL_LINE : GL_FILL);
	}
	public static void setAlphaBlend(boolean alphaBlend){
		if(alphaBlend){
			glEnable(GL_BLEND);
			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		}
		else{
			glDisable(GL_BLEND);
		}
	}
	public static void terminate(){
		ShaderProgram.stop();
		shader.dispose();
	}
}