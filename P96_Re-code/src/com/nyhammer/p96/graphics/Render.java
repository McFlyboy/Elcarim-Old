package com.nyhammer.p96.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL20.*;

import java.util.ArrayList;
import java.util.List;

import com.nyhammer.p96.entities.ModelEntity;
import com.nyhammer.p96.entities.TextField;
import com.nyhammer.p96.graphics.shading.ShaderProgram;
import com.nyhammer.p96.graphics.shading.shaders.P96Shader;

public class Render{
	private static final int MODEL_ENTITY_ENTRY = 0;
	private static final int TEXT_FIELD_ENTRY = 1;
	private static StaticRender sRender;
	private static BatchRender bRender;
	private static P96Shader shader;
	private static List<Integer> masterQueue = new ArrayList<Integer>();
	private static List<ModelEntity> entityQueue = new ArrayList<ModelEntity>();
	private static List<TextField> textQueue = new ArrayList<TextField>();
	public static void init(){
		sRender = new StaticRender();
		bRender = new BatchRender();
		shader = new P96Shader();
		shader.start();
	}
	public static void addToQueue(ModelEntity entity){
		entityQueue.add(entity);
		masterQueue.add(MODEL_ENTITY_ENTRY);
	}
	public static void addToQueue(TextField text){
		textQueue.add(text);
		masterQueue.add(TEXT_FIELD_ENTRY);
	}
	public static void renderQueue(){
		int lastVAO = 0;
		int bVAO = bRender.getVAO();
		for(int masterEntry : masterQueue){
			if(masterEntry == MODEL_ENTITY_ENTRY){
				ModelEntity entity = entityQueue.remove(0);
				Model model = entity.getModel();
				int vao = model.getVAO();
				if(lastVAO != vao){
					unprepare();
					sRender.prepareModel(model);
					lastVAO = vao;
				}
				sRender.render(shader, entity);
			}
			else if(masterEntry == TEXT_FIELD_ENTRY){
				TextField text = textQueue.remove(0);
				if(lastVAO != bVAO){
					unprepare();
					bRender.prepare();
					lastVAO = bVAO;
				}
				bRender.render(shader, text);
			}
		}
		masterQueue.clear();
	}
	private static void unprepare(){
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
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
		bRender.dispose();
	}
}