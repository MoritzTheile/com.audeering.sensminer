package com.audeering.sensminer.model.trackconf;

import com.audeering.sensminer.model.configuration.Configuration.TRACKTYPE;

public class AccelerationTrackConf extends AbstrTrackConf{

	public AccelerationTrackConf(){
		setEnabled(false);
	}

	@Override
	public TRACKTYPE getTrackType() {
		return TRACKTYPE.ACCELEROMETER;
	}

	@Override
	public boolean isAvailable() {
		return false;
	}

}
