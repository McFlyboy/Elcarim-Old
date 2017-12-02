package com.nyhammer.p96.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;

import com.nyhammer.p96.entities.ModelEntity;
import com.nyhammer.p96.graphics.shading.shaders.S96;
import com.nyhammer.p96.structure.Scene;

public class StaticRender{
	public void prepareModel(Model model){
		model.bind();
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glActiveTexture(GL_TEXTURE0);
	}
	public void render(S96 shader, Scene scene, ModelEntity entity){
		glBindTexture(GL_TEXTURE_2D, entity.texture.getTexture());
		shader.loadTextureInfo(entity.texture.getHorizontalCount(), entity.texture.getVerticalCount(), entity.texture.getOffsetX(), entity.texture.getOffsetY());
		shader.loadTransformation(scene.position, entity.position, entity.angle, entity.scale);
		shader.loadColors(scene.brightness, entity.colorActive, entity.color);
		glDrawElements(GL_TRIANGLES, entity.model.getIndexCount(), GL_UNSIGNED_INT, 0L);
	}
}