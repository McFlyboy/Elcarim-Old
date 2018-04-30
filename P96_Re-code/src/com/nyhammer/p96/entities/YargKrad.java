package com.nyhammer.p96.entities;

import com.nyhammer.p96.structure.ResourceStorage;
import com.nyhammer.p96.util.math.vector.Vector2f;
import com.nyhammer.p96.util.timing.Timer;

public class YargKrad extends Enemy {
	public YargKrad(Timer baseTimer) {
		super(baseTimer, 15);
		scale.x = 0.14f;
		scale.y = 0.14f;
		cc.radius = 0.105f;
		model = ResourceStorage.getModel("square");
		texture = ResourceStorage.getTexture("yargKradTex");
		name = "Yarg Krad";
	}
	@Override
	public void update(double time, float deltaTime, Vector2f playerPosition) {
		if(positioned) {
			if(state == 0) {
				position.x = properPosition.x + (float)Math.sin((time - positionedTime) * 0.5) * 0.5f;
				position.y = properPosition.y - (float)Math.sin((time - positionedTime) * 1.35) * 0.1f;
			}
		}
		else {
			float moveSpeed = 0.8f;
			if(state > 0) {
				moveSpeed = 0.4f;
			}
			Vector2f distance = properPosition.getSub(position);
			if(distance.getLength() <= 0.0001f) {
				positioned = true;
				positionedTime = time;
			}
			else {
				position.add(distance.getNormalize().getMul(deltaTime * moveSpeed));
			}
		}
		if(super.lives < 4) {
			if(state == 0) {
				state = 1;
				attackIndex = 3;
				properPosition.y = 0.65f;
				properPosition.x = 0f;
				positioned = false;
			}
			if(state == 2) {
				state = 3;
				attackIndex = 4;
				properPosition.y = 0.5f;
				properPosition.x = 0f;
				positioned = false;
			}
		}
		else if(super.lives < 8) {
			attackIndex = 2;
		}
		else if(super.lives < 12) {
			attackIndex = 1;
		}
		if(!attacks.isEmpty() && positioned) {
			attacks.get(attackIndex).update(deltaTime, position, playerPosition);
		}
	}
}
