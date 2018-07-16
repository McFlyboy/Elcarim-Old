package com.nyhammer.p96.test.entity.uiEntities.control;

import java.util.ArrayList;
import java.util.List;

import com.nyhammer.p96.test.entity.uiEntities.UIEntity;

public class Group extends UIEntity {
	public enum RowType {
		VERTICAL, HORIZONTAL;
	}
	private List<UIEntity> uiEntities;
	private int index;
	private RowType rowType;
	private float spacing;
	public Group() {
		super();
		uiEntities = new ArrayList<UIEntity>();
		index = 0;
		rowType = RowType.VERTICAL;
		spacing = 0f;
	}
	@Override
	public float getWidth() {
		float width = 0f;
		if(rowType == RowType.HORIZONTAL) {
			for(UIEntity uiEntity : uiEntities) {
				width += uiEntity.getWidth();
			}
			width += spacing * (uiEntities.size() - 1);
		}
		else {
			for(UIEntity uiEntity : uiEntities) {
				width = Math.max(width, uiEntity.getWidth());
			}
		}
		return width;
	}
	@Override
	public float getHeight() {
		float height = 0f;
		if(rowType == RowType.VERTICAL) {
			for(UIEntity uiEntity : uiEntities) {
				height += uiEntity.getHeight();
			}
			height += spacing * (uiEntities.size() - 1);
		}
		else {
			for(UIEntity uiEntity : uiEntities) {
				height = Math.max(height, uiEntity.getHeight());
			}
		}
		return height;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public void moveIndexToNext() {
		index++;
	}
	public void moveIndexToPrevious() {
		index--;
	}
	public List<UIEntity> getUIEntities() {
		return uiEntities;
	}
	public void addUIEntity(UIEntity entity) {
		uiEntities.add(entity);
	}
	public RowType getRowType() {
		return rowType;
	}
	public void setRowType(RowType rowType) {
		this.rowType = rowType;
	}
	protected float getSpacing() {
		return spacing;
	}
	protected void setSpacing(float spacing) {
		this.spacing = spacing;
	}
}
