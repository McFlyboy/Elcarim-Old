package com.nyhammer.p96.test.graphics.appearance;

import com.nyhammer.p96.test.graphics.Model;
import com.nyhammer.p96.graphics.Texture;
import com.nyhammer.p96.util.Color3f;

public class ModelAppearance extends Appearance {
	private Model model;
	private Texture texture;
	private Color3f color;
	private boolean colorActive;
	public ModelAppearance() {
		super();
		model = null;
		texture = null;
		color = new Color3f(1f, 1f, 1f);
		colorActive = false;
	}
	public Model getModel() {
		return model;
	}
	public void setModel(Model model) {
		this.model = model;
	}
	public Texture getTexture() {
		return texture;
	}
	public void setTexture(Texture texture) {
		this.texture = texture;
	}
	public Color3f getColor() {
		return color;
	}
	public void setColor(Color3f color) {
		this.color = color;
	}
	public boolean isColorActive() {
		return colorActive;
	}
	public void setColorActive(boolean colorActive) {
		this.colorActive = colorActive;
	}
	@Override
	public float getWidth() {
		return model.getWidth();
	}
	@Override
	public float getHeight() {
		return model.getHeight();
	}
}
