package com.nyhammer.p96.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.stb.STBImage.*;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import com.nyhammer.p96.ErrorHandler;
import com.nyhammer.p96.ui.GameWindow;

public class Texture {
	private int width;
	private int height;
	private int colorComp;
	private int texture;
	private int horizontalCount;
	private int verticalCount;
	private int offsetX;
	private int offsetY;
	public Texture(String filename) {
		this(filename, 1, 1, false);
	}
	public Texture(String filename, int horizontalCount, int verticalCount) {
		this(filename, horizontalCount, verticalCount, false);
	}
	public Texture(String filename, int horizontalCount, int verticalCount, boolean linear) {
		ByteBuffer buffer = loadImage(filename);
		this.horizontalCount = horizontalCount;
		this.verticalCount = verticalCount;
		initTexture(buffer, linear);
	}
	public Texture(BufferedImage img) {
		this(img, false);
	}
	public Texture(BufferedImage img, boolean linear) {
		ByteBuffer buffer = createByteBuffer(img);
		horizontalCount = 1;
		verticalCount = 1;
		initTexture(buffer, linear);
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public int getColorComp() {
		return colorComp;
	}
	public int getTexture() {
		return texture;
	}
	public int getHorizontalCount() {
		return horizontalCount;
	}
	public int getVerticalCount() {
		return verticalCount;
	}
	public int getOffsetX() {
		return offsetX;
	}
	public int getOffsetY() {
		return offsetY;
	}
	public void setOffset(int x, int y) {
		offsetX = x;
		offsetY = y;
	}
	private void initTexture(ByteBuffer buffer, boolean linear) {
		texture = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, texture);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);
		int filter = linear ? GL_LINEAR : GL_NEAREST;
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, filter);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, filter);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
	}
	private ByteBuffer createByteBuffer(BufferedImage img) {
		width = img.getWidth();
		height = img.getHeight();
		colorComp = img.getColorModel().getNumComponents();
		AffineTransform transform = AffineTransform.getScaleInstance(1f, -1f);
		transform.translate(0, -height);
		AffineTransformOp operation = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		img = operation.filter(img, null);
		int[] pixels = new int[width * height];
		img.getRGB(0, 0, width, height, pixels, 0, width);
		ByteBuffer buffer = BufferUtils.createByteBuffer(pixels.length * colorComp);
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				int pixel = pixels[x + y * width];
				buffer.put((byte)((pixel >> 16) & 0xff));
				buffer.put((byte)((pixel >> 8) & 0xff));
				buffer.put((byte)(pixel & 0xff));
				buffer.put((byte)((pixel >> 24) & 0xff));
			}
		}
		buffer.flip();
		return buffer;
	}
	private ByteBuffer loadImage(String filename) {
		IntBuffer widthBuffer = BufferUtils.createIntBuffer(1);
		IntBuffer heightBuffer = BufferUtils.createIntBuffer(1);
		IntBuffer colorCompBuffer = BufferUtils.createIntBuffer(1);
		stbi_set_flip_vertically_on_load(true);
		ByteBuffer img = stbi_load("assets/textures/" + filename, widthBuffer, heightBuffer, colorCompBuffer, 4);
		if(img == null) {
			ErrorHandler.printError(String.format("Could not load the texture: %s\n%s", filename, stbi_failure_reason()), true);
			ErrorHandler.printError(new RuntimeException());
			GameWindow.close();
		}
		width = widthBuffer.get();
		height = heightBuffer.get();
		colorComp = colorCompBuffer.get();
		return img;
	}
	public void dispose() {
		glDeleteTextures(texture);
	}
}
