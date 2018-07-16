package com.nyhammer.p96.test.entity.uiEntities;

import com.nyhammer.p96.test.entity.uiEntities.util.UIAction;
import com.nyhammer.p96.test.graphics.appearance.Appearance;

public class Button extends UIActionAppearanceEntity {
	public Button() {
		super();
	}
	public UIAction getOnClick() {
		return super.getAction();
	}
	public void setOnClick(UIAction action) {
		super.setAction(action);
	}
	public Appearance getAppearance() {
		return super.getAppearance();
	}
	public void setAppearance(Appearance appearance) {
		super.setAppearance(appearance);
	}
}
