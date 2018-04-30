package com.nyhammer.p96;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.nyhammer.p96.util.io.TextIO;

public class Settings {
	private static TextIO file;
	private static Map<String, String> data;
	public static void init() {
		file = new TextIO(TextIO.FILE, "Settings.cfg");
		data = new HashMap<String, String>();
		data.put("fullscreen", "false");
		data.put("vsync", "false");
	}
	public static void readFromFile() {
		String fileContent = file.readFileWhole();
		String[] lines = fileContent.split("\n");
		for(String line : lines) {
			if(!line.contains("=")) {
				continue;
			}
			String[] components = line.split("=");
			data.replace(components[0], components[1]);
		}
	}
	public static void writeToFile() {
		StringBuilder content = new StringBuilder();
		Iterator<Entry<String, String>> iterator = data.entrySet().iterator();
		while(iterator.hasNext()) {
			Entry<String, String> entry = iterator.next();
			content.append(entry.getKey()).append("=").append(entry.getValue()).append("\n");
		}
		file.writeFileWhole(content.toString());
	}
	public static String getValue(String key) {
		return data.get(key);
	}
	public static void setValue(String key, String value) {
		data.replace(key, value);
	}
}
