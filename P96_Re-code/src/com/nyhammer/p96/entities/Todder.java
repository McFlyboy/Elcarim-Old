package com.nyhammer.p96.entities;

import com.nyhammer.p96.structure.ResourceStorage;
import com.nyhammer.p96.util.math.vector.Vector2f;
import com.nyhammer.p96.util.timing.Timer;

public class Todder extends Enemy{
	public Todder(Timer baseTimer){
		super(baseTimer, 0);
		scale.x = 0.08f;
		scale.y = 0.08f;
		cc.radius = 0.06f;
		model = ResourceStorage.getModel("square");
		texture = ResourceStorage.getTexture("todderTex");
		name = "Todder";
	}
	@Override
	public void update(double time, float deltaTime, Vector2f playerPosition){
		if(positioned){
			position.x = properPosition.x + (float)Math.sin((time - positionedTime) * 0.5) * 0.5f;
			position.y = properPosition.y + (float)-Math.sin((time - positionedTime) * 1.35) * 0.1f;
		}
		else{
			Vector2f distance = properPosition.getSub(position);
			if(distance.getLength() <= 0.0001f){
				positioned = true;
				positionedTime = time;
			}
			else{
				position.add(distance.getNormalize().getMul(deltaTime * 0.8f));
			}
		}
		if(!attacks.isEmpty() && positioned){
			attacks.get(attackIndex).update(deltaTime, position, playerPosition);
		}
	}
}