package com.nyhammer.p96.test.graphics.appearance;

import java.util.ArrayList;
import java.util.List;

import com.nyhammer.p96.graphics.font.TextFont;
import com.nyhammer.p96.structure.ResourceStorage;
import com.nyhammer.p96.util.Color3f;

public class TextAppearance extends Appearance {
	private String content;
	private float width, height;
	private List<Color3f> charColors;
	private Color3f drawColor;
	public TextAppearance() {
		super();
		//super.setScale(0.004f);
		content = "";
		width = 0f;
		height = (float)ResourceStorage.getTextFont("font").getFontHeight();
		charColors = new ArrayList<Color3f>();
		drawColor = new Color3f(1f, 1f, 1f);
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		if(content.equals(this.content)) {
			return;
		}
		this.content = content;
		charColors.clear();
		colorizeNewContent(content);
		calculateSize();
	}
	public void appendContent(String string) {
		content += string;
		colorizeNewContent(string);
	}
	public void clearContent() {
		content = "";
		charColors.clear();
		width = 0f;
		height = (float)ResourceStorage.getTextFont("font").getFontHeight();
	}
	@Override
	public float getWidth() {
		return width;
	}
	@Override
	public float getHeight() {
		return height;
	}
	public List<Color3f> getCharColors() {
		return charColors;
	}
	public void setCharColors(Color3f color) {
		for(int i = 0; i < charColors.size(); i++) {
			setCharColor(i, color);
		}
	}
	public void setCharColor(int index, Color3f color) {
		if(index < 0 || index >= charColors.size()) {
			return;
		}
		Color3f charColor = charColors.get(index);
		charColor.red = color.red;
		charColor.green = color.green;
		charColor.blue = color.blue;
	}
	public Color3f getDrawColor() {
		return drawColor;
	}
	public void setDrawColor(Color3f color) {
		drawColor.red = color.red;
		drawColor.green = color.green;
		drawColor.blue = color.blue;
	}
	private void colorizeNewContent(String newContent) {
		for(int i = 0; i < newContent.length(); i++) {
			charColors.add(new Color3f(drawColor.red, drawColor.green, drawColor.blue));
		}
	}
	public void calculateSize() {
		TextFont font = ResourceStorage.getTextFont("font");
		int width = 0;
		int height = 0;
		int lineWidth = 0;
		int lines = 1;
		for(int i = 0; i < content.length(); i++) {
			char ch = content.charAt(i);
			if(ch != '\n') {
				lineWidth += font.getGlyph(ch).getWidth();
			}
			else {
				width = Math.max(width, lineWidth);
				lineWidth = 0;
				lines++;
			}
		}
		width = Math.max(width, lineWidth);
		height = lines * font.getFontHeight();
		this.width = (float)width;
		this.height = (float)height;
	}
}
