package com.nyhammer.p96.graphics.shading.shaders;

import com.nyhammer.p96.graphics.shading.ShaderProgram;
import com.nyhammer.p96.util.math.vector.Vector2f;

public class P96Shader extends ShaderProgram{
	private static final String vshFilename = "p96.vsh";
	private static final String fshFilename = "p96.fsh";
	private int positionLocation;
	private int angleLocation;
	private int scaleLocation;
	private int colorActiveLocation;
	private int redLocation;
	private int greenLocation;
	private int blueLocation;
	public P96Shader(){
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
		colorActiveLocation = super.getUniformLocation("colorActive");
		redLocation = super.getUniformLocation("red");
		greenLocation = super.getUniformLocation("green");
		blueLocation = super.getUniformLocation("blue");
	}
	public void loadTransformation(Vector2f position, float angle, Vector2f scale){
		super.loadVector2f(positionLocation, position);
		super.loadFloat(angleLocation, angle);
		super.loadVector2f(scaleLocation, scale);
	}
	public void loadColors(boolean colorActive, float red, float green, float blue){
		super.loadBoolean(colorActiveLocation, colorActive);
		super.loadFloat(redLocation, red);
		super.loadFloat(greenLocation, green);
		super.loadFloat(blueLocation, blue);
	}
}