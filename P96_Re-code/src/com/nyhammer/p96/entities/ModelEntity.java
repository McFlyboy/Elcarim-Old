package com.nyhammer.p96.entities;

import com.nyhammer.p96.graphics.Model;
import com.nyhammer.p96.graphics.Texture;
import com.nyhammer.p96.util.Color3f;
import com.nyhammer.p96.util.math.vector.Vector2f;

public class ModelEntity extends Entity{
	public Model model;
	public Texture texture;
	public Color3f color;
	public boolean colorActive;
	public boolean visible;
	public boolean monochrome;
	public ModelEntity(){
		super();
		model = null;
		texture = null;
		color = new Color3f(1f, 1f, 1f);
		colorActive = false;
		visible = true;
		monochrome = false;
	}
	public ModelEntity(Model model, Texture texture, Vector2f position, Vector2f scale, float angle){
		super(position, scale, angle);
		this.model = model;
		this.texture = texture;
		color = new Color3f(1f, 1f, 1f);
		colorActive = false;
		visible = true;
	}
}