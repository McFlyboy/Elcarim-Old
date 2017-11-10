package com.nyhammer.p96.entities;

import com.nyhammer.p96.graphics.Model;
import com.nyhammer.p96.graphics.Texture;
import com.nyhammer.p96.util.math.vector.Vector2f;

public class ModelEntity extends Entity{
	public Model model;
	public Texture texture;
	public float red, green, blue;
	public boolean colorActive;
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
}