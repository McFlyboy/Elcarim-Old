package com.nyhammer.p96.entities;

import com.nyhammer.p96.graphics.font.TextFont;
import com.nyhammer.p96.structure.ResourceStorage;
import com.nyhammer.p96.util.math.vector.Vector2f;

public class TextField extends Entity{
	private String text;
	public float red, green, blue;
	private int baseWidth, baseHeight;
	public TextField(){
		super();
		super.scale.x = 0.005f;
		super.scale.y = 0.005f;
		red = 1f;
		green = 1f;
		blue = 1f;
		text = "";
		baseWidth = 0;
		baseHeight = ResourceStorage.getTextFont("font").getFontHeight();
	}
	public TextField(String text, float red, float green, float blue, Vector2f position, Vector2f scale, float angle){
		super(position, scale, angle);
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.text = text;
		calculateBaseSize();
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