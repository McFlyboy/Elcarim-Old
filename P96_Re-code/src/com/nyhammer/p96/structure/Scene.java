package com.nyhammer.p96.structure;

import com.nyhammer.p96.graphics.Render;
import com.nyhammer.p96.util.math.vector.Vector2f;
import com.nyhammer.p96.util.timing.DeltaTimer;
import com.nyhammer.p96.util.timing.Timer;

public abstract class Scene {
	private boolean running;
	protected Timer timer;
	protected DeltaTimer deltaTimer;
	public Vector2f position;
	public float brightness;
	public Scene(Timer timer) {
		running = false;
		this.timer = new Timer(timer);
		deltaTimer = new DeltaTimer(this.timer);
		position = new Vector2f();
		brightness = 1f;
	}
	public boolean isRunning() {
		return running;
	}
	protected abstract void startSpecifics();
	protected abstract void updateSpecifics(float deltaTime);
	protected abstract void renderSpecifics();
	protected abstract void stopSpecifics();
	protected abstract void disposeSpecifics();
	public void start() {
		if(running) {
			return;
		}
		timer.start();
		startSpecifics();
		running = true;
	}
	public void update() {
		if(!running) {
			return;
		}
		updateSpecifics((float)deltaTimer.getTime());
	}
	public void render() {
		Render.setScene(this);
		renderSpecifics();
	}
	public void stop() {
		if(!running) {
			return;
		}
		timer.pause();
		stopSpecifics();
		running = false;
	}
	public void dispose() {
		disposeSpecifics();
	}
}
