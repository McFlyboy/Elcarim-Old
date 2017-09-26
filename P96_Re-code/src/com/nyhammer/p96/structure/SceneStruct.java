package com.nyhammer.p96.structure;

import com.nyhammer.p96.util.timing.Timer;

public abstract class SceneStruct{
	private boolean running;
	protected Timer sceneTimer;
	//List of what to render
	public SceneStruct(Timer timer){
		sceneTimer = new Timer(timer);
	}
	public boolean isRunning(){
		return running;
	}
	protected abstract void startSpecifics();
	protected abstract void updateSpecifics();
	protected abstract void renderSpecifics();
	protected abstract void stopSpecifics();
	protected abstract void disposeSpecifics();
	public void start(){
		if(running){
			return;
		}
		sceneTimer.resume();
		startSpecifics();
	}
	public void update(){
		if(!running){
			return;
		}
		updateSpecifics();
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
	}
	public void dispose(){
		disposeSpecifics();
	}
}