package com.audeering.sensminer.model.trackconf;

import com.audeering.sensminer.model.configuration.Configuration.TRACKTYPE;

public class MagneticTrackConf extends AbstrTrackConf{

	public MagneticTrackConf(){
		setEnabled(false);
	}

	@Override
	public TRACKTYPE getTrackType() {
		return TRACKTYPE.MAGNETIC;
	}


	@Override
	public boolean isAvailable() {
		return false;
	}

}
