package com.nyhammer.p96.entities;

import com.nyhammer.p96.graphics.font.TextFont;
import com.nyhammer.p96.structure.ResourceStorage;

public class TextField extends Entity{
	private String text;
	public float[] charReds;
	public float[] charGreens;
	public float[] charBlues;
	public float mainRed;
	public float mainGreen;
	public float mainBlue;
	private int baseWidth, baseHeight;
	public TextField(){
		super();
		super.scale.x = 0.005f;
		super.scale.y = 0.005f;
		text = "";
		baseWidth = 0;
		baseHeight = ResourceStorage.getTextFont("font").getFontHeight();
		mainRed = 1f;
		mainGreen = 1f;
		mainBlue = 1f;
		charReds = new float[1];
		charReds[0] = mainRed;
		charGreens = new float[1];
		charGreens[0] = mainGreen;
		charBlues = new float[1];
		charBlues[0] = mainBlue;
	}
	public String getText(){
		return text;
	}
	public void setText(String text){
		if(text.equals(this.text)){
			return;
		}
		this.text = text;
		calculateBaseSize();
		charReds = new float[text.length()];
		charGreens = new float[text.length()];
		charBlues = new float[text.length()];
		for(int i = 0; i < text.length(); i++){
			charReds[i] = mainRed;
			charGreens[i] = mainGreen;
			charBlues[i] = mainBlue;
		}
	}
	public int getBaseWidth(){
		return baseWidth;
	}
	public int getBaseHeight(){
		return baseHeight;
	}
	public float getWidth(){
		return (float)baseWidth * scale.x;
	}
	public float getHeight(){
		return (float)baseHeight * scale.y;
	}
	public void setReds(float red){
		for(int i = 0; i < charReds.length; i++){
			charReds[i] = red;
		}
	}
	public void setGreens(float green){
		for(int i = 0; i < charGreens.length; i++){
			charGreens[i] = green;
		}
	}
	public void setBlues(float blue){
		for(int i = 0; i < charBlues.length; i++){
			charBlues[i] = blue;
		}
	}
	private void calculateBaseSize(){
		TextFont font = ResourceStorage.getTextFont("font");
		baseWidth = 0;
		baseHeight = 0;
		int lineWidth = 0;
		int lines = 1;
		for(int i = 0; i < text.length(); i++){
			char ch = text.charAt(i);
			if(ch != '\n'){
				lineWidth += font.getGlyph(ch).getWidth();
			}
			else{
				baseWidth = Math.max(baseWidth, lineWidth);
				lineWidth = 0;
				lines++;
			}
		}
		baseWidth = Math.max(baseWidth, lineWidth);
		baseHeight = lines * font.getFontHeight();
	}
}