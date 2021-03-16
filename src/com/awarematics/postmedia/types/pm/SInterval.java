package com.awarematics.postmedia.types.pm;

public class SInterval {

	long 		startTime;
	long 		endTime;
	boolean 	leftClosed;
	boolean 	rightClosed;

	public SInterval( long startTime, long endTime, boolean leftClosed, boolean rightClosed )
	{
		this.startTime = startTime;
		this.endTime = endTime;
		this.leftClosed = leftClosed;
		this.rightClosed = rightClosed;
	}
	
	public long getStartTime() {
		return startTime;
	}
	
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	
	public long getEndTime() {
		return endTime;
	}
	
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	
	public boolean isLeftClosed() {
		return leftClosed;
	}
	
	public void setLeftClosed(boolean leftClosed) {
		this.leftClosed = leftClosed;
	}
	
	public boolean isRightClosed() {
		return rightClosed;
	}
	
	public void setRightClosed(boolean rightClosed) {
		this.rightClosed = rightClosed;
	}
	
}
