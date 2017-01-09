package com.audeering.sensminer.model.trackconf;

import com.audeering.sensminer.model.configuration.Configuration.TRACKTYPE;

public class ActivityTrackConf extends AbstrTrackConf{

	public ActivityTrackConf(){
		setEnabled(false);
	}

	@Override
	public TRACKTYPE getTrackType() {
		return TRACKTYPE.ACTIVITY;
	}

	@Override
	public boolean isAvailable() {
		return false;
	}

}
