package com.nyhammer.p96.entities;

import com.nyhammer.p96.graphics.font.TextFont;
import com.nyhammer.p96.structure.ResourceStorage;
import com.nyhammer.p96.util.Color3f;

public class TextField extends Entity{
	private String text;
	public Color3f[] charColors;
	public Color3f mainColor;
	private int baseWidth, baseHeight;
	public boolean visible;
	public TextField(){
		super();
		scale.x = 0.005f;
		scale.y = 0.005f;
		text = "";
		baseWidth = 0;
		baseHeight = ResourceStorage.getTextFont("font").getFontHeight();
		mainColor = new Color3f(1f, 1f, 1f);
		charColors = new Color3f[1];
		charColors[0] = new Color3f(mainColor.red, mainColor.green, mainColor.blue);
		visible = true;
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
		charColors = new Color3f[text.length()];
		for(int i = 0; i < text.length(); i++){
			charColors[i] = new Color3f(mainColor.red, mainColor.green, mainColor.blue);
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
	public void setColors(Color3f color){
		for(int i = 0; i < charColors.length; i++){
			charColors[i].red = color.red;
			charColors[i].green = color.green;
			charColors[i].blue = color.blue;
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