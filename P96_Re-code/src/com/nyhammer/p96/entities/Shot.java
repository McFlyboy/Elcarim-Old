package com.nyhammer.p96.entities;

import com.nyhammer.p96.structure.ResourceStorage;
import com.nyhammer.p96.util.math.collision.CC;

public class Shot extends ModelEntity{
	public CC cc;
	public boolean intact;
	public Shot(){
		super();
		scale.x = 0.05f;
		scale.y = 0.05f;
		intact = true;
		cc = new CC(position, 0.05f * 3f/8f);
		ResourceStorage.getSound("shotSound").play();
	}
	public void update(float deltaTime){
		position.y += 3f * deltaTime;
		if(position.y > 1f + scale.y){
			intact = false;
		}
	}
}