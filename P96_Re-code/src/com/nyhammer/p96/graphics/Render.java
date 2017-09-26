package com.nyhammer.p96.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

public class Render{
	public static void render(Model model){
		model.bind();
		glEnableVertexAttribArray(0);
		glDrawElements(GL_TRIANGLES, model.getIndexCount(), GL_UNSIGNED_INT, 0L);
		glDisableVertexAttribArray(0);
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
}