package com.nyhammer.p96.test.structure.UI;

import com.nyhammer.p96.test.entity.uiEntities.UIAppearanceEntity;
import com.nyhammer.p96.test.entity.uiEntities.UIEntity;
import com.nyhammer.p96.test.entity.uiEntities.control.Group;
import com.nyhammer.p96.util.math.vector.Vector2f;

public class UI {
	public enum AnchorPoint {
		TOP_LEFT, TOP, TOP_RIGHT,
		LEFT, CENTER, RIGHT,
		BOTTOM_LEFT, BOTTOM, BOTTOM_RIGHT;
	}
	private UIEntity uiEntity;
	private AnchorPoint anchorPoint;
	private Vector2f anchorPosition;
	public UI() {
		this(null);
	}
	public UI(UIEntity uiEntity) {
		this.uiEntity = uiEntity;
		anchorPoint = AnchorPoint.CENTER;
		anchorPosition = new Vector2f();
	}
	public UIEntity getUIEntity() {
		return uiEntity;
	}
	public void setUIEntity(UIEntity uiEntity) {
		this.uiEntity = uiEntity;
	}
	public AnchorPoint getAnchorPoint() {
		return anchorPoint;
	}
	public void setAnchorPoint(AnchorPoint anchorPoint) {
		this.anchorPoint = anchorPoint;
	}
	public Vector2f getAnchorPosition() {
		return anchorPosition;
	}
	public void setAnchorPosition(Vector2f anchorPosition) {
		this.anchorPosition = anchorPosition;
	}
	public void organizeUI() {
		if(uiEntity == null) {
			return;
		}
		float halfWidth = uiEntity.getWidth() / 2f;
		float halfHeight = uiEntity.getWidth() / 2f;
		Vector2f center = null;
		switch(anchorPoint) {
		case TOP_LEFT:
			center = anchorPosition.getAdd(new Vector2f(halfWidth, -halfHeight));
			break;
		case TOP:
			center = anchorPosition.getAdd(new Vector2f(0f, -halfHeight));
			break;
		case TOP_RIGHT:
			center = anchorPosition.getAdd(new Vector2f(-halfWidth, -halfHeight));
			break;
		case LEFT:
			center = anchorPosition.getAdd(new Vector2f(halfWidth, 0f));
			break;
		case CENTER:
			center = anchorPosition.getAdd(new Vector2f());
			break;
		case RIGHT:
			center = anchorPosition.getAdd(new Vector2f(-halfWidth, 0f));
			break;
		case BOTTOM_LEFT:
			center = anchorPosition.getAdd(new Vector2f(halfWidth, halfHeight));
			break;
		case BOTTOM:
			center = anchorPosition.getAdd(new Vector2f(0f, halfHeight));
			break;
		case BOTTOM_RIGHT:
			center = anchorPosition.getAdd(new Vector2f(-halfWidth, halfHeight));
			break;
		}
		Vector2f top = center.getAdd(new Vector2f(0f, halfHeight));
	}
	public void render() {
		if(uiEntity == null) {
			return;
		}
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
