package com.audeering.sensminer.model.trackconf;

import com.audeering.sensminer.model.configuration.Configuration.TRACKTYPE;

public class AudioTrackConf extends AbstrTrackConf{

	private String outputFormat = "AMR_NB";
	
	@Override
	public TRACKTYPE getTrackType() {
		return TRACKTYPE.AUDIO;
	}

}
