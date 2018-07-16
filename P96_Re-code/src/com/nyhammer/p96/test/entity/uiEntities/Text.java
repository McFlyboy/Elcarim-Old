package com.nyhammer.p96.test.entity.uiEntities;

import com.nyhammer.p96.test.graphics.appearance.TextAppearance;

public class Text extends UIAppearanceEntity {
	public Text() {
		super();
	}
	public TextAppearance getText() {
		return (TextAppearance)super.getAppearance();
	}
	public void setText(TextAppearance text) {
		super.setAppearance(text);
	}
}
