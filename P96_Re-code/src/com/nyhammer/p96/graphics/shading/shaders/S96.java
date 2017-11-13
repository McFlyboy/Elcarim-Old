package com.nyhammer.p96.graphics.shading.shaders;

import com.nyhammer.p96.graphics.shading.ShaderProgram;
import com.nyhammer.p96.util.Color3f;
import com.nyhammer.p96.util.math.vector.Vector2f;

public class S96 extends ShaderProgram{
	private static final String vshFilename = "p96.vsh";
	private static final String fshFilename = "p96.fsh";
	private int positionLocation;
	private int angleLocation;
	private int scaleLocation;
	private int horizontalTextureCountLocation;
	private int verticalTextureCountLocation;
	private int textureOffsetXLocation;
	private int textureOffsetYLocation;
	private int colorActiveLocation;
	private int colorLocation;
	public S96(){
		super(vshFilename, fshFilename);
	}
	@Override
	protected void bindAttribs(){
		super.bindAttrib(0, "vertex");
		super.bindAttrib(1, "textureCoord");
	}
	@Override
	protected void getUniformLocations(){
		positionLocation = super.getUniformLocation("position");
		angleLocation = super.getUniformLocation("angle");
		scaleLocation = super.getUniformLocation("scale");
		horizontalTextureCountLocation = super.getUniformLocation("horizontalTextureCount");
		verticalTextureCountLocation = super.getUniformLocation("verticalTextureCount");
		textureOffsetXLocation = super.getUniformLocation("textureOffsetX");
		textureOffsetYLocation = super.getUniformLocation("textureOffsetY");
		colorActiveLocation = super.getUniformLocation("colorActive");
		colorLocation = super.getUniformLocation("color");
	}
	public void loadTransformation(Vector2f position, float angle, Vector2f scale){
		super.loadVector2f(positionLocation, position);
		super.loadFloat(angleLocation, angle);
		super.loadVector2f(scaleLocation, scale);
	}
	public void loadTextureInfo(int horizontalTextureCount, int verticalTextureCount, int textureOffsetX, int textureOffsetY){
		super.loadInt(horizontalTextureCountLocation, horizontalTextureCount);
		super.loadInt(verticalTextureCountLocation, verticalTextureCount);
		super.loadInt(textureOffsetXLocation, textureOffsetX);
		super.loadInt(textureOffsetYLocation, textureOffsetY);
	}
	public void loadColors(boolean colorActive, Color3f color){
		super.loadBoolean(colorActiveLocation, colorActive);
		super.loadColor3f(colorLocation, color);
	}
}