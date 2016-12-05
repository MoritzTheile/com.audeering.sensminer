package com.audeering.sensminer.model.trackconf;

import com.audeering.sensminer.model.abstr.AbstrDTO;
import com.audeering.sensminer.model.configuration.Configuration.TRACKTYPE;

public abstract class AbstrTrackConf extends AbstrDTO{
	
	private boolean enabled = true;
	
	public abstract TRACKTYPE getTrackType();

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
}
