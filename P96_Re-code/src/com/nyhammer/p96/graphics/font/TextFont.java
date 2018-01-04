package com.nyhammer.p96.graphics.font;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nyhammer.p96.ErrorHandler;
import com.nyhammer.p96.graphics.Texture;
import com.nyhammer.p96.ui.GameWindow;

public class TextFont{
	private Texture fontTexture;
	private int fontWidth;
	private int fontHeight;
	private Map<Character, Glyph> glyphs = new HashMap<Character, Glyph>();
	public TextFont(String filename, int size){
		Font font = null;
		int imgWidth = 0;
		int imgHeight = 0;
		try(BufferedInputStream fontStream = new BufferedInputStream(Class.class.getResourceAsStream("/fonts/" + filename))){
			font = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(Font.PLAIN, size);
		}
		catch(IOException e){
			ErrorHandler.printError("Could not read font-file: " + filename, true);
			ErrorHandler.printError(e);
			GameWindow.close();
		}
		catch(FontFormatException e){
			ErrorHandler.printError("Could not process font-file: " + filename, true);
			ErrorHandler.printError(e);
			GameWindow.close();
		}
		FontMetrics metrics = createFontMetrics(font);
		List<BufferedImage> charImages = new ArrayList<BufferedImage>();
		List<Character> chars = new ArrayList<Character>();
		for(int i = 32; i < 256; i++){
			char ch = (char)i;
			BufferedImage charImage = createCharImage(ch, font, metrics);
			imgWidth += charImage.getWidth();
			imgHeight = Math.max(imgHeight,  charImage.getHeight());
			charImages.add(charImage);
			chars.add(ch);
		}
		fontWidth = imgWidth;
		fontHeight = imgHeight;
		BufferedImage fontImg = new BufferedImage(fontWidth, fontHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = fontImg.createGraphics();
		int drawPoint = 0;
		for(int i = 0; i < charImages.size(); i++){
			BufferedImage charImage = charImages.get(i);
			int charWidth = charImage.getWidth();
			int charHeight = charImage.getHeight();
			Glyph glyph = new Glyph(drawPoint, fontImg.getHeight() - charHeight, charWidth, charHeight);
			g.drawImage(charImage, drawPoint, 0, null);
			drawPoint += glyph.getWidth();
			glyphs.put(chars.get(i), glyph);
		}
		g.dispose();
		fontTexture = new Texture(fontImg);
	}
	public Texture getFontTexture(){
		return fontTexture;
	}
	public int getFontWidth(){
		return fontWidth;
	}
	public int getFontHeight(){
		return fontHeight;
	}
	public Map<Character, Glyph> getGlyphs(){
		return glyphs;
	}
	public Glyph getGlyph(char ch){
		return glyphs.get(ch);
	}
	public void dispose(){
		fontTexture.dispose();
	}
	private FontMetrics createFontMetrics(Font font){
		BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = img.createGraphics();
		g.setFont(font);
		FontMetrics metrics = g.getFontMetrics();
		g.dispose();
		return metrics;
	}
	private BufferedImage createCharImage(char ch, Font font, FontMetrics metrics){
		BufferedImage charImage = new BufferedImage(metrics.charWidth(ch) + 1, metrics.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = charImage.createGraphics();
		g.setFont(font);
		g.setPaint(Color.WHITE);
		g.drawString(String.valueOf(ch), 0, metrics.getAscent());
		g.dispose();
		return charImage;
	}
	public class Glyph{
		private int x, y, width, height;
		public Glyph(int x, int y, int width, int height){
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
		}
		public int getX(){
			return x;
		}
		public int getY(){
			return y;
		}
		public int getWidth(){
			return width;
		}
		public int getHeight(){
			return height;
		}
	}
}