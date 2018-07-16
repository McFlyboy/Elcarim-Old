package com.nyhammer.p96.test.structure.UI;

import com.nyhammer.p96.test.entity.uiEntities.UIAppearanceEntity;
import com.nyhammer.p96.test.entity.uiEntities.UIEntity;
import com.nyhammer.p96.test.entity.uiEntities.control.Group;

public class UI {
	public enum CenterPoint {
		TOP_LEFT, TOP, TOP_RIGHT,
		LEFT, CENTER, RIGHT,
		BOTTOM_LEFT, BOTTOM, BOTTOM_RIGHT;
	}
	private UIEntity uiEntity;
	private CenterPoint centerPoint;
	public UI() {
		this(null);
	}
	public UI(UIEntity uiEntity) {
		this.uiEntity = uiEntity;
		centerPoint = CenterPoint.CENTER;
	}
	public UIEntity getUIEntity() {
		return uiEntity;
	}
	public void setUIEntity(UIEntity uiEntity) {
		this.uiEntity = uiEntity;
	}
	public CenterPoint getCenterPoint() {
		return centerPoint;
	}
	public void setCenterPoint(CenterPoint centerPoint) {
		this.centerPoint = centerPoint;
	}
	public void organizeUI() {
		
	}
	public void render() {
		renderUIEntity(uiEntity);
	}
	private void renderUIEntity(UIEntity uiEntity) {
		if(uiEntity instanceof UIAppearanceEntity) {
			renderUIAppearanceEntity((UIAppearanceEntity)uiEntity);
		}
		else {
			renderGroup((Group)uiEntity);
		}
	}
	private void renderUIAppearanceEntity(UIAppearanceEntity uiAppearanceEntity) {
		
	}
	private void renderGroup(Group group) {
		for(UIEntity uiEntity : group.getUIEntities()) {
			renderUIEntity(uiEntity);
		}
	}
}
