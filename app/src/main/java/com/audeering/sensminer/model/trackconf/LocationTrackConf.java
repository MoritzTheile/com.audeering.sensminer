package com.audeering.sensminer.model.trackconf;

import com.audeering.sensminer.model.configuration.Configuration.TRACKTYPE;

public class LocationTrackConf extends AbstrTrackConf{

	
	@Override
	public TRACKTYPE getTrackType() {
		return TRACKTYPE.LOCATION;
	}

}
