package com.audeering.sensminer.model.record;

import com.audeering.sensminer.model.abstr.AbstrDTO;
import com.audeering.sensminer.model.situation.Situation;

public class Record extends AbstrDTO {

	/*
	 * A millisecond timestamp when the Record-Button was pressed.
	 */	
	private long startTime;
	
	/*
	 * A millisecond timestamp when the Recording is ended with Stop-Button or Timer.
	 */	
	private long endTime;
	
	private Situation situation;
	

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
	public Situation getSituation() {
		return situation;
	}
	public void setSituation(Situation situation) {
		this.situation = situation;
	}

}
