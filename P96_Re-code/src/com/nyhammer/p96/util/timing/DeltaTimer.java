package com.nyhammer.p96.util.timing;

public class DeltaTimer{
	private Timer timer;
	public DeltaTimer(){
		this(null);
	}
	public DeltaTimer(Timer basetimer){
		timer = new Timer(basetimer, true);
	}
	public double getTime(){
		double time = timer.getTime();
		timer.reset(true);
		return time;
	}
}