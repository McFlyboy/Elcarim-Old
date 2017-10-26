package com.nyhammer.p96.entities;

import com.nyhammer.p96.graphics.Model;
import com.nyhammer.p96.graphics.Texture;
import com.nyhammer.p96.util.math.vector.Vector2f;

public class ModelEntity extends Entity{
	private Model model;
	private Texture texture;
	private float red, green, blue;
	private boolean colorActive;
	public ModelEntity(){
		super();
		model = null;
		texture = null;
		red = 1f;
		green = 1f;
		blue = 1f;
		colorActive = false;
	}
	public ModelEntity(Model model, Texture texture, Vector2f position, Vector2f scale, float angle){
		super(position, scale, angle);
		this.model = model;
		this.texture = texture;
		red = 1f;
		green = 1f;
		blue = 1f;
		colorActive = false;
	}
	public Model getModel(){
		return model;
	}
	public void setModel(Model model){
		this.model = model;
	}
	public Texture getTexture(){
		return texture;
	}
	public void setTexture(Texture texture){
		this.texture = texture;
	}
	public float getRed(){
		return red;
	}
	public void setRed(float red){
		this.red = red;
	}
	public float getGreen(){
		return green;
	}
	public void setGreen(float green){
		this.green = green;
	}
	public float getBlue(){
		return blue;
	}
	public void setBlue(float blue){
		this.blue = blue;
	}
	public boolean isColorActive(){
		return colorActive;
	}
	public void setColorActive(boolean colorActive){
		this.colorActive = colorActive;
	}
}