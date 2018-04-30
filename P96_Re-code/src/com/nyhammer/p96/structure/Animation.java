package com.nyhammer.p96.structure;

public class Animation {
	private int textureRow;
	private int firstFrame;
	private int animationLength;
	public Animation(int textureRow, int firstFrame, int animationLength) {
		this.textureRow = textureRow;
		this.firstFrame = firstFrame;
		this.animationLength = animationLength;
	}
	public int getTextureRow() {
		return textureRow;
	}
	public void setTextureRow(int textureRow) {
		this.textureRow = textureRow;
	}
	public int getFirstFrame() {
		return firstFrame;
	}
	public void setFirstFrame(int firstFrame) {
		this.firstFrame = firstFrame;
	}
	public int getAnimationLength() {
		return animationLength;
	}
	public void setAnimationLength(int animationLength) {
		this.animationLength = animationLength;
	}
	public int getFrame(int frame) {
		if(frame < 0f) {
			return (frame % animationLength) + animationLength + firstFrame - 1;
		}
		return (frame % animationLength) + firstFrame;
	}
}
