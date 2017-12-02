package com.nyhammer.p96.graphics.shading.shaders;

import com.nyhammer.p96.graphics.shading.ShaderProgram;
import com.nyhammer.p96.util.Color3f;
import com.nyhammer.p96.util.math.vector.Vector2f;

public class S96 extends ShaderProgram{
	private static final String vshFilename = "s96.vsh";
	private static final String fshFilename = "s96.fsh";
	private int scenePositionLocation;
	private int positionLocation;
	private int angleLocation;
	private int scaleLocation;
	private int sceneBrightnessLocation;
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
		scenePositionLocation = super.getUniformLocation("scenePosition");
		positionLocation = super.getUniformLocation("position");
		angleLocation = super.getUniformLocation("angle");
		scaleLocation = super.getUniformLocation("scale");
		sceneBrightnessLocation = super.getUniformLocation("sceneBrightness");
		horizontalTextureCountLocation = super.getUniformLocation("horizontalTextureCount");
		verticalTextureCountLocation = super.getUniformLocation("verticalTextureCount");
		textureOffsetXLocation = super.getUniformLocation("textureOffsetX");
		textureOffsetYLocation = super.getUniformLocation("textureOffsetY");
		colorActiveLocation = super.getUniformLocation("colorActive");
		colorLocation = super.getUniformLocation("color");
	}
	public void loadTransformation(Vector2f scenePosition, Vector2f position, float angle, Vector2f scale){
		super.loadVector2f(scenePositionLocation, scenePosition);
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
	public void loadColors(float sceneBrightness, boolean colorActive, Color3f color){
		super.loadFloat(sceneBrightnessLocation, sceneBrightness);
		super.loadBoolean(colorActiveLocation, colorActive);
		super.loadColor3f(colorLocation, color);
	}
}