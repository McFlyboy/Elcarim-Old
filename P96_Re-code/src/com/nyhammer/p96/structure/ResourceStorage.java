package com.nyhammer.p96.structure;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.nyhammer.p96.ErrorHandler;
import com.nyhammer.p96.audio.Music;
import com.nyhammer.p96.audio.Sound;
import com.nyhammer.p96.graphics.Model;
import com.nyhammer.p96.graphics.Texture;
import com.nyhammer.p96.graphics.font.TextFont;

public class ResourceStorage {
	private static Map<String, Model> modelStorage = new HashMap<String, Model>();
	private static Map<String, Texture> textureStorage = new HashMap<String, Texture>();
	private static Map<String, TextFont> fontStorage = new HashMap<String, TextFont>();
	private static Map<String, Sound> soundStorage = new HashMap<String, Sound>();
	private static Map<String, Music> musicStorage = new HashMap<String, Music>();
	public static Model getModel(String name) {
		Model model = modelStorage.get(name);
		if(model == null) {
			ErrorHandler.printError("Resource-name mismatch!\nWrong name: " + name);
		}
		return model;
	}
	public static Texture getTexture(String name) {
		Texture texture = textureStorage.get(name);
		if(texture == null) {
			ErrorHandler.printError("Resource-name mismatch!\nWrong name: " + name);
		}
		return texture;
	}
	public static TextFont getTextFont(String name) {
		TextFont font = fontStorage.get(name);
		if(font == null) {
			ErrorHandler.printError("Resource-name mismatch!\nWrong name: " + name);
		}
		return font;
	}
	public static Sound getSound(String name) {
		Sound sound = soundStorage.get(name);
		if(sound == null) {
			ErrorHandler.printError("Resource-name mismatch!\nWrong name: " + name);
		}
		return sound;
	}
	public static Music getMusic(String name) {
		Music music = musicStorage.get(name);
		if(music == null) {
			ErrorHandler.printError("Resource-name mismatch!\nWrong name: " + name);
		}
		return music;
	}
	public static void add(String name, Model model) {
		modelStorage.put(name, model);
	}
	public static void add(String name, Texture texture) {
		textureStorage.put(name, texture);
	}
	public static void add(String name, TextFont font) {
		fontStorage.put(name, font);
	}
	public static void add(String name, Sound sound) {
		soundStorage.put(name, sound);
	}
	public static void add(String name, Music music) {
		musicStorage.put(name, music);
	}
	public static void disposeModel(String name) {
		Model model = modelStorage.remove(name);
		if(model != null) {
			model.dispose();
		}
	}
	public static void disposeTexture(String name) {
		Texture texture = textureStorage.remove(name);
		if(texture != null) {
			texture.dispose();
		}
	}
	public static void disposeTextFont(String name) {
		TextFont font = fontStorage.remove(name);
		if(font != null) {
			font.dispose();
		}
	}
	public static void disposeSound(String name) {
		Sound sound = soundStorage.remove(name);
		if(sound != null) {
			sound.dispose();
		}
	}
	public static void disposeMusic(String name) {
		Music music = musicStorage.remove(name);
		if(music != null) {
			music.dispose();
		}
	}
	public static void disposeModels() {
		Iterator<Entry<String, Model>> iterator = modelStorage.entrySet().iterator();
		while(iterator.hasNext()) {
			iterator.next().getValue().dispose();
		}
		modelStorage.clear();
	}
	public static void disposeTextures() {
		Iterator<Entry<String, Texture>> iterator = textureStorage.entrySet().iterator();
		while(iterator.hasNext()) {
			iterator.next().getValue().dispose();
		}
		textureStorage.clear();
	}
	public static void disposeTextFonts() {
		Iterator<Entry<String, TextFont>> iterator = fontStorage.entrySet().iterator();
		while(iterator.hasNext()) {
			iterator.next().getValue().dispose();
		}
		fontStorage.clear();
	}
	public static void disposeSounds() {
		Iterator<Entry<String, Sound>> iterator = soundStorage.entrySet().iterator();
		while(iterator.hasNext()) {
			iterator.next().getValue().dispose();
		}
		soundStorage.clear();
	}
	public static void disposeMusics() {
		Iterator<Entry<String, Music>> iterator = musicStorage.entrySet().iterator();
		while(iterator.hasNext()) {
			iterator.next().getValue().dispose();
		}
		musicStorage.clear();
	}
	public static void disposeAll() {
		disposeModels();
		disposeTextures();
		disposeTextFonts();
		disposeSounds();
		disposeMusics();
	}
}
