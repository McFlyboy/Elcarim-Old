package com.nyhammer.p96.entities;

import java.util.List;

import com.nyhammer.p96.structure.Attack;
import com.nyhammer.p96.structure.attacks.TestAttack;
import com.nyhammer.p96.util.timing.Timer;

public class Todder extends Enemy{
	public Todder(List<Bullet> sceneBullets, Timer baseTimer){
		super(baseTimer);
		scale.x = 0.08f;
		scale.y = 0.08f;
		cc.radius = 0.06f;
		attacks = new Attack[]{
				new TestAttack(sceneBullets, baseTimer)
		};
	}
	@Override
	public void update(){
		attacks[0].update(position);
	}
}