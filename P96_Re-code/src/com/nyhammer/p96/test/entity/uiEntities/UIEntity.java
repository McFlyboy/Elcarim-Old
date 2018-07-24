package com.nyhammer.p96.test.entity.uiEntities;

import com.nyhammer.p96.test.entity.Entity;
import com.nyhammer.p96.util.math.vector.Vector2f;

public abstract class UIEntity extends Entity {
	public UIEntity() {
		super();
	}
	public abstract Vector2f getSize();
}
