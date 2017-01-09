package com.audeering.sensminer.model.trackconf;

import com.audeering.sensminer.model.configuration.Configuration.TRACKTYPE;

public class PictureTrackConf extends AbstrTrackConf{

	public PictureTrackConf(){
		setEnabled(false);
	}

	@Override
	public TRACKTYPE getTrackType() {
		return TRACKTYPE.PICTURE;
	}

	@Override
	public boolean isAvailable() {
		return false;
	}

}
