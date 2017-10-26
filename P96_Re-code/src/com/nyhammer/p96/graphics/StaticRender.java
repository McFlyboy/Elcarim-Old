package com.nyhammer.p96.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;

import com.nyhammer.p96.entities.ModelEntity;
import com.nyhammer.p96.graphics.shading.shaders.P96Shader;

public class StaticRender{
	public void prepareModel(Model model){
		model.bind();
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glActiveTexture(GL_TEXTURE0);
	}
	public void render(P96Shader shader, ModelEntity entity){
		glBindTexture(GL_TEXTURE_2D, entity.getTexture().getTexture());
		shader.loadTransformation(entity.getPosition(), entity.getAngle(), entity.getScale());
		shader.loadColors(entity.isColorActive(), entity.getRed(), entity.getGreen(), entity.getBlue());
		glDrawElements(GL_TRIANGLES, entity.getModel().getIndexCount(), GL_UNSIGNED_INT, 0L);
	}
}