package com.nyhammer.p96.test.entity.uiEntities;

import com.nyhammer.p96.test.graphics.appearance.Appearance;

public abstract class UIAppearanceEntity extends UIEntity {
	private Appearance appearance;
	public UIAppearanceEntity() {
		super();
		appearance = null;
	}
	@Override
	public float getWidth() {
		return appearance.getWidth() * super.getScale().x;
	}
	@Override
	public float getHeight() {
		return appearance.getHeight() * super.getScale().y;
	}
	protected Appearance getAppearance() {
		return appearance;
	}
	protected void setAppearance(Appearance appearance) {
		this.appearance = appearance;
	}
}
