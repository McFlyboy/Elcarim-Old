package com.nyhammer.p96.util.timing;

public class Timer{
	private Timer baseTimer;
	private double startTime;
	private double pauseTime;
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
			return pauseTime - startTime;
		}
		return (baseTimer != null ? baseTimer.getTime() : Time.getTime()) - startTime;
	}
	public void setTime(double time){
		startTime = (baseTimer != null ? baseTimer.getTime() : Time.getTime()) - time;
		pauseTime = startTime;
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
		startTime += (baseTimer != null ? baseTimer.getTime() : Time.getTime()) - pauseTime;
		pause = false;
	}
	public void reset(boolean startOnInit){
		startTime = baseTimer != null ? baseTimer.getTime() : Time.getTime();
		if(!startOnInit){
			pause = true;
			pauseTime = startTime;
		}
		else{
			pauseTime = 0.0;
		}
	}
}