package com.nyhammer.p96.structure;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.nyhammer.p96.ErrorHandler;
import com.nyhammer.p96.graphics.Model;
import com.nyhammer.p96.graphics.Texture;

public class ResourceStorage{
	private static Map<String, Model> modelStorage = new HashMap<String, Model>();
	private static Map<String, Texture> textureStorage = new HashMap<String, Texture>();
	public static Model getModel(String name){
		Model model = modelStorage.get(name);
		if(model == null){
			ErrorHandler.printError("Resource-name mismatch!");
		}
		return model;
	}
	public static Texture getTexture(String name){
		Texture texture = textureStorage.get(name);
		if(texture == null){
			ErrorHandler.printError("Resource-name mismatch!");
		}
		return texture;
	}
	public static void add(String name, Model model){
		modelStorage.put(name, model);
	}
	public static void add(String name, Texture texture){
		textureStorage.put(name, texture);
	}
	public static void disposeModel(String name){
		Model model = modelStorage.remove(name);
		if(model != null){
			model.dispose();
		}
	}
	public static void disposeTexture(String name){
		Texture texture = textureStorage.remove(name);
		if(texture != null){
			texture.dispose();
		}
	}
	public static void disposeModels(){
		Iterator<Entry<String, Model>> iterator = modelStorage.entrySet().iterator();
		while(iterator.hasNext()){
			iterator.next().getValue().dispose();
		}
		modelStorage.clear();
	}
	public static void disposeTextures(){
		Iterator<Entry<String, Texture>> iterator = textureStorage.entrySet().iterator();
		while(iterator.hasNext()){
			iterator.next().getValue().dispose();
		}
		textureStorage.clear();
	}
	public static void disposeAll(){
		disposeModels();
		disposeTextures();
	}
}