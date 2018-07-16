package com.nyhammer.p96.test.entity.uiEntities;

import com.nyhammer.p96.test.entity.Entity;

public abstract class UIEntity extends Entity {
	public UIEntity() {
		super();
	}
	public abstract float getWidth();
	public abstract float getHeight();
}
