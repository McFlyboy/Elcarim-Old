package com.nyhammer.p96.test.entity.uiEntities;

import com.nyhammer.p96.test.graphics.appearance.Appearance;
import com.nyhammer.p96.util.math.vector.Vector2f;

public abstract class UIAppearanceEntity extends UIEntity {
	private Appearance appearance;
	public UIAppearanceEntity() {
		super();
		appearance = null;
	}
	@Override
	public Vector2f getSize() {
		Vector2f scale = super.getScale();
		return new Vector2f(scale.x * appearance.getWidth(), scale.y * appearance.getHeight());
	}
	protected Appearance getAppearance() {
		return appearance;
	}
	protected void setAppearance(Appearance appearance) {
		this.appearance = appearance;
	}
}
