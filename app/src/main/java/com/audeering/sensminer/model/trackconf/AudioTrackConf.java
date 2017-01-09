package com.audeering.sensminer.model.trackconf;

import com.audeering.sensminer.model.configuration.Configuration.TRACKTYPE;

public class AudioTrackConf extends AbstrTrackConf{

	private Integer sampleRateInHz;
	private Integer recorderBPP;
	private Integer numberOfChannels;

	
	@Override
	public TRACKTYPE getTrackType() {
		return TRACKTYPE.AUDIO;
	}

	@Override
	public boolean isAvailable() {
		return true;
	}

	public Integer getSampleRateInHz() {
		return sampleRateInHz;
	}

	public void setSampleRateInHz(Integer sampleRateInHz) {
		this.sampleRateInHz = sampleRateInHz;
	}

	public Integer getRecorderBPP() {
		return recorderBPP;
	}

	public void setRecorderBPP(Integer recorderBPP) {
		this.recorderBPP = recorderBPP;
	}

	public Integer getNumberOfChannels() {
		return numberOfChannels;
	}

	public void setNumberOfChannels(Integer numberOfChannels) {
		this.numberOfChannels = numberOfChannels;
	}
}
