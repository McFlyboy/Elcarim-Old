package com.nyhammer.p96.graphics;

public class Models{
	public static Model createSquare(){
		Model square = new Model();
		square.setFaces(new int[]{
				0, 1, 2,
				0, 2, 3
		});
		square.addAttrib(0, 2, new float[]{
				-1, -1,
				1, -1,
				1, 1,
				-1, 1
		});
		square.addAttrib(1, 2, new float[]{
				0, 0,
				1, 0,
				1, 1,
				0, 1
		});
		Model.unbind();
		return square;
	}
}