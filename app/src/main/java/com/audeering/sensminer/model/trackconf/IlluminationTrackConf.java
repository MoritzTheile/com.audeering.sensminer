package com.audeering.sensminer.model.trackconf;

import com.audeering.sensminer.model.configuration.Configuration.TRACKTYPE;

public class IlluminationTrackConf extends AbstrTrackConf{

	public IlluminationTrackConf(){
		setEnabled(false);
	}

	@Override
	public TRACKTYPE getTrackType() {
		return TRACKTYPE.ILLUMINATION;
	}


	@Override
	public boolean isAvailable() {
		return false;
	}

}
