package com.nyhammer.p96.test.entity.uiEntities.control;

import java.util.ArrayList;
import java.util.List;

import com.nyhammer.p96.test.entity.uiEntities.UIEntity;
import com.nyhammer.p96.util.math.vector.Vector2f;

public class Group extends UIEntity {
	public enum RowType {
		VERTICAL, HORIZONTAL;
	}
	public enum Alignment {
		ALIGN_NEGATIVE, ALIGN_CENTER, ALIGN_POSITIVE;
	}
	private List<UIEntity> uiEntities;
	private int index;
	private RowType rowType;
	private Alignment alignment;
	private float spacing;
	public Group() {
		super();
		uiEntities = new ArrayList<UIEntity>();
		index = 0;
		rowType = RowType.VERTICAL;
		alignment = Alignment.ALIGN_CENTER;
		spacing = 0f;
	}
	@Override
	public Vector2f getSize() {
		Vector2f size = new Vector2f();
		if(rowType == RowType.VERTICAL) {
			for(UIEntity uiEntity : uiEntities) {
				Vector2f uiEntitySize = uiEntity.getSize();
				size.x = Math.max(size.x, uiEntitySize.x);
				size.y += uiEntitySize.y;
			}
			size.y += spacing * (uiEntities.size() - 1);
		}
		else {
			for(UIEntity uiEntity : uiEntities) {
				Vector2f uiEntitySize = uiEntity.getSize();
				size.x += uiEntitySize.x;
				size.y = Math.max(size.y, uiEntitySize.y);
			}
			size.x += spacing * (uiEntities.size() - 1);
		}
		return size;
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
	public Alignment getAlignment() {
		return alignment;
	}
	public void setAlignment(Alignment alignment) {
		this.alignment = alignment;
	}
	protected float getSpacing() {
		return spacing;
	}
	protected void setSpacing(float spacing) {
		this.spacing = spacing;
	}
}
