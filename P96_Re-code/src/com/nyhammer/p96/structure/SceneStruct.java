package com.nyhammer.p96.structure;

import com.nyhammer.p96.util.timing.DeltaTimer;
import com.nyhammer.p96.util.timing.Timer;

public abstract class SceneStruct{
	private boolean running;
	protected Timer sceneTimer;
	protected DeltaTimer deltaSceneTimer;
	public SceneStruct(Timer timer){
		sceneTimer = new Timer(timer);
		deltaSceneTimer = new DeltaTimer(sceneTimer);
	}
	public boolean isRunning(){
		return running;
	}
	protected abstract void startSpecifics();
	protected abstract void updateSpecifics(float deltaTime);
	protected abstract void renderSpecifics();
	protected abstract void stopSpecifics();
	protected abstract void disposeSpecifics();
	public void start(){
		if(running){
			return;
		}
		sceneTimer.resume();
		startSpecifics();
		running = true;
	}
	public void update(){
		if(!running){
			return;
		}
		updateSpecifics((float)deltaSceneTimer.getTime());
	}
	public void render(){
		renderSpecifics();
	}
	public void stop(){
		if(!running){
			return;
		}
		sceneTimer.pause();
		stopSpecifics();
		running = false;
	}
	public void dispose(){
		disposeSpecifics();
	}
}