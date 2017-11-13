package com.nyhammer.p96.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.FloatBuffer;

import org.lwjgl.system.MemoryUtil;

import com.nyhammer.p96.entities.TextField;
import com.nyhammer.p96.graphics.font.TextFont;
import com.nyhammer.p96.graphics.font.TextFont.Glyph;
import com.nyhammer.p96.graphics.shading.shaders.S96;
import com.nyhammer.p96.structure.ResourceStorage;

public class BatchRender{
	private int vao;
	private int vbo;
	private FloatBuffer vertices;
	private TextFont font;
	public BatchRender(){
		vao = glGenVertexArrays();
		glBindVertexArray(vao);
		vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		vertices = MemoryUtil.memAllocFloat(16384);
		glBufferData(GL_ARRAY_BUFFER, vertices.capacity() * Float.BYTES, GL_DYNAMIC_DRAW);
		glVertexAttribPointer(0, 2, GL_FLOAT, false, Float.BYTES * 4, 0L);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, Float.BYTES * 4, Float.BYTES * 2);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
		font = ResourceStorage.getTextFont("font");
	}
	public int getVAO(){
		return vao;
	}
	public void prepare(){
		glBindVertexArray(vao);
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glActiveTexture(GL_TEXTURE0);
	}
	public void render(S96 shader, TextField textField){
		glBindTexture(GL_TEXTURE_2D, font.getFontTexture().getTexture());
		shader.loadTextureInfo(1, 1, 0, 0);
		int fontHeight = font.getFontHeight();
		int textHeight = textField.getBaseHeight();
		String text = textField.getText();
		float drawX = -textField.getBaseWidth() / 2f;
		float drawY = textHeight / 2f - fontHeight;
		for(int i = 0; i < text.length(); i++){
			char ch = text.charAt(i);
			if(ch == '\n'){
				drawY -= fontHeight;
				drawX = 0;
				continue;
			}
			if(ch == '\r'){
				continue;
			}
			Glyph glyph = font.getGlyph(ch);
			drawTextureRegion(drawX, drawY, glyph);
			drawX += glyph.getWidth();
		}
		vertices.flip();
		shader.loadTransformation(textField.position, textField.angle, textField.scale);
		glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);
		for(int i = 0; i < text.length(); i++){
			shader.loadColors(true, textField.charColors[i]);
			glDrawArrays(GL_TRIANGLES, i * 6, 6);
		}
		vertices.clear();
	}
	private void drawTextureRegion(float x, float y, Glyph glyph){
		float fontWidth = (float)font.getFontWidth();
		float fontHeight = (float)font.getFontHeight();
		float x1 = x;
		float y1 = y;
		float x2 = x + glyph.getWidth();
		float y2 = y + glyph.getHeight();
		float s1 = (float)glyph.getX() / fontWidth;
		float t1 = (float)glyph.getY() / fontHeight;
		float s2 = ((float)glyph.getX() + (float)glyph.getWidth()) / fontWidth;
		float t2 = ((float)glyph.getY() + (float)glyph.getHeight()) / fontHeight;
		vertices.put(x1).put(y1).put(s1).put(t1);
		vertices.put(x1).put(y2).put(s1).put(t2);
		vertices.put(x2).put(y2).put(s2).put(t2);
		vertices.put(x1).put(y1).put(s1).put(t1);
		vertices.put(x2).put(y2).put(s2).put(t2);
		vertices.put(x2).put(y1).put(s2).put(t1);
	}
	public void dispose(){
		MemoryUtil.memFree(vertices);
		glDeleteBuffers(vbo);
		glDeleteVertexArrays(vao);
	}
}