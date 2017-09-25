package com.nyhammer.p96.util.timing;

public class Timer{
	private Timer baseTimer;
	private double startTime;
	private double pauseTime;
	private double pausedTime;
	private boolean pause;
	public Timer(){
		this(null, false);
	}
	public Timer(boolean startOnInit){
		this(null, startOnInit);
	}
	public Timer(Timer baseTimer){
		this(baseTimer, false);
	}
	public Timer(Timer baseTimer, boolean startOnInit){
		this.baseTimer = baseTimer;
		startTime = baseTimer != null ? baseTimer.getTime() : Time.getTime();
		if(!startOnInit){
			pause = true;
			pauseTime = startTime;
		}
	}
	public double getTime(){
		if(pause){
			return pauseTime - startTime - pausedTime;
		}
		return baseTimer != null ? baseTimer.getTime() : Time.getTime() - startTime - pausedTime;
	}
	public void pause(){
		if(pause){
			return;
		}
		pauseTime = baseTimer != null ? baseTimer.getTime() : Time.getTime();
		pause = true;
	}
	public void resume(){
		if(!pause){
			return;
		}
		pausedTime += baseTimer != null ? baseTimer.getTime() : Time.getTime() - pauseTime;
		pause = false;
	}
	public void reset(boolean startOnInit){
		startTime = baseTimer != null ? baseTimer.getTime() : Time.getTime();
		pausedTime = 0.0;
		if(!startOnInit){
			pause = true;
			pauseTime = startTime;
		}
		else{
			pause = false;
			pauseTime = 0.0;
		}
	}
}