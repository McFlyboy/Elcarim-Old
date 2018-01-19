package com.nyhammer.p96.entities;

import com.nyhammer.p96.graphics.Texture;
import com.nyhammer.p96.structure.ResourceStorage;
import com.nyhammer.p96.util.math.vector.Vector2f;
import com.nyhammer.p96.util.timing.Timer;

public class LuckTodder extends Enemy{
	public LuckTodder(Timer baseTimer, String name, Texture texture){
		super(baseTimer, 2);
		scale.x = 0.1f;
		scale.y = 0.1f;
		cc.radius = 0.075f;
		model = ResourceStorage.getModel("square");
		super.texture = texture;
		super.name = name;
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