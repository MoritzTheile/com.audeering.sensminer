package com.audeering.sensminer.model.trackconf;

import com.audeering.sensminer.model.abstr.AbstrDTO;
import com.audeering.sensminer.model.configuration.Configuration.TRACKTYPE;
import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class AbstrTrackConf extends AbstrDTO{
	
	private boolean enabled = true;

	@JsonIgnore
	public abstract TRACKTYPE getTrackType();

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@JsonIgnore
	public abstract boolean isAvailable();

}
