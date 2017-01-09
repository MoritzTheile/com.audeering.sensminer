package com.audeering.sensminer.model.trackconf;

import com.audeering.sensminer.model.configuration.Configuration.TRACKTYPE;

public class GyroTrackConf extends AbstrTrackConf{

	public GyroTrackConf(){
		setEnabled(false);
	}

	@Override
	public TRACKTYPE getTrackType() {
		return TRACKTYPE.GYRO;
	}

	@Override
	public boolean isAvailable() {
		return false;
	}

}
