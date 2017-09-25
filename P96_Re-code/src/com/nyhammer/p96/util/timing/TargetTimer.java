package com.nyhammer.p96.util.timing;

public class TargetTimer{
	private Timer timer;
	private double targetTime;
	public TargetTimer(double targetTime){
		this(null, targetTime);
	}
	public TargetTimer(Timer baseTimer, double targetTime){
		timer = new Timer(baseTimer, true);
		this.targetTime = targetTime;
	}
	public boolean targetReached(){
		if(timer.getTime() >= targetTime){
			timer.reset(true);
			return true;
		}
		return false;
	}
}