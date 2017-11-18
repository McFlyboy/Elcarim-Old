package com.nyhammer.p96.entities;

import com.nyhammer.p96.util.timing.Timer;

public class Todder extends Enemy{
	public Todder(Timer baseTimer){
		super(baseTimer);
		scale.x = 0.08f;
		scale.y = 0.08f;
		cc.radius = 0.06f;
	}
	@Override
	public void update(){
		
	}
}