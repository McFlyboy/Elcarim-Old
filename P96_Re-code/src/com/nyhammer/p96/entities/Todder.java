package com.nyhammer.p96.entities;

import com.nyhammer.p96.structure.ResourceStorage;
import com.nyhammer.p96.util.math.vector.Vector2f;
import com.nyhammer.p96.util.timing.Timer;

public class Todder extends Enemy{
	public Todder(Timer baseTimer){
		super(baseTimer);
		scale.x = 0.08f;
		scale.y = 0.08f;
		cc.radius = 0.06f;
		model = ResourceStorage.getModel("square");
		texture = ResourceStorage.getTexture("todderTex");
		name = "Todder";
	}
	@Override
	public void update(double time, Vector2f playerPosition){
		position.y += Math.cos(time / 0.5) / 9000f;
		position.x += Math.cos(time / 2.0) / 3000f;
		if(!attacks.isEmpty()){
			attacks.get(attackIndex).update(position, playerPosition);
		}
	}
}