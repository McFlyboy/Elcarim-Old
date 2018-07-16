package com.nyhammer.p96.test.entity.uiEntities;

import com.nyhammer.p96.test.graphics.appearance.ModelAppearance;

public class Picture extends UIAppearanceEntity {
	public Picture() {
		super();
	}
	public ModelAppearance getPicture() {
		return (ModelAppearance)super.getAppearance();
	}
	public void setPicture(ModelAppearance picture) {
		super.setAppearance(picture);
	}
}
