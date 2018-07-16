package com.nyhammer.p96.test.entity.uiEntities;

import com.nyhammer.p96.test.entity.uiEntities.util.UIAction;

public abstract class UIActionAppearanceEntity extends UIAppearanceEntity {
	private UIAction action;
	public UIActionAppearanceEntity() {
		super();
		action = null;
	}
	protected UIAction getAction() {
		return action;
	}
	protected void setAction(UIAction action) {
		this.action = action;
	}
}
