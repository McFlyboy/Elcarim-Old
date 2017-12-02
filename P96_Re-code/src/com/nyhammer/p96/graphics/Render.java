package com.nyhammer.p96.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import java.util.ArrayList;
import java.util.List;

import com.nyhammer.p96.entities.ModelEntity;
import com.nyhammer.p96.entities.TextField;
import com.nyhammer.p96.graphics.shading.ShaderProgram;
import com.nyhammer.p96.graphics.shading.shaders.S96;
import com.nyhammer.p96.structure.Scene;
import com.nyhammer.p96.util.Color3f;

public class Render{
	private static StaticRender sRender;
	private static BatchRender bRender;
	private static S96 shader;
	private static Scene activeScene;
	private static List<Entry> masterQueue = new ArrayList<Entry>();
	private static List<Scene> sceneQueue = new ArrayList<Scene>();
	private static List<ModelEntity> entityQueue = new ArrayList<ModelEntity>();
	private static List<TextField> textQueue = new ArrayList<TextField>();
	private enum Entry{
		MODEL_ENTITY_ENTRY, TEXT_FIELD_ENTRY;
	}
	public static void init(){
		sRender = new StaticRender();
		bRender = new BatchRender();
		shader = new S96();
		shader.start();
	}
	public static void addToQueue(ModelEntity entity){
		if(!entity.visible){
			return;
		}
		entityQueue.add(entity);
		masterQueue.add(Entry.MODEL_ENTITY_ENTRY);
		sceneQueue.add(activeScene);
	}
	public static void addToQueue(TextField text){
		if(!text.visible){
			return;
		}
		textQueue.add(text);
		masterQueue.add(Entry.TEXT_FIELD_ENTRY);
		sceneQueue.add(activeScene);
	}
	public static void renderQueue(){
		int lastVAO = 0;
		int bVAO = bRender.getVAO();
		for(Entry masterEntry : masterQueue){
			if(masterEntry == Entry.MODEL_ENTITY_ENTRY){
				ModelEntity entity = entityQueue.remove(0);
				int vao = entity.model.getVAO();
				if(lastVAO != vao){
					unprepare();
					sRender.prepareModel(entity.model);
					lastVAO = vao;
				}
				sRender.render(shader, sceneQueue.remove(0), entity);
			}
			else if(masterEntry == Entry.TEXT_FIELD_ENTRY){
				TextField text = textQueue.remove(0);
				if(lastVAO != bVAO){
					unprepare();
					bRender.prepare();
					lastVAO = bVAO;
				}
				bRender.render(shader, sceneQueue.remove(0), text);
			}
		}
		masterQueue.clear();
		sceneQueue.clear();
		unprepare();
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
	public static void setClearColor(Color3f color){
		setClearColor(color.red, color.green, color.blue);
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
	public static void setScene(Scene scene){
		activeScene = scene;
	}
	public static void terminate(){
		ShaderProgram.stop();
		shader.dispose();
		bRender.dispose();
	}
}