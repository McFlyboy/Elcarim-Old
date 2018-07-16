package com.nyhammer.p96.util;

public class Color3f {
	public float red, green, blue;
	public Color3f() {
		red = 0f;
		green = 0f;
		blue = 0f;
	}
	public Color3f(int color) {
		red = (float)((color & 0xFF0000) >> 16) / 255f;
		green = (float)((color & 0xFF00) >> 8) / 255f;
		blue = (float)(color & 0xFF) / 255f;
	}
	public Color3f(float red, float green, float blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	public Color3f getAdd(Color3f color) {
		return new Color3f(red + color.red, green + color.green, blue + color.blue);
	}
	public void add(Color3f color) {
		red += color.red;
		green += color.green;
		blue += color.blue;
	}
	public Color3f getSub(Color3f color) {
		return new Color3f(red - color.red, green - color.green, blue - color.blue);
	}
	public void sub(Color3f color) {
		red -= color.red;
		green -= color.green;
		blue -= color.blue;
	}
	public Color3f getMul(float scalar) {
		return new Color3f(red * scalar, green * scalar, blue * scalar);
	}
	public void mul(float scalar) {
		red *= scalar;
		green *= scalar;
		blue *= scalar;
	}
}
