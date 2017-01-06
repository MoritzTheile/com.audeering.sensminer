package com.audeering.sensminer.model.configuration;

import com.audeering.sensminer.model.abstr.AbstrDTO;
import com.audeering.sensminer.model.situation.Situation;
import com.audeering.sensminer.model.trackconf.AccelerationTrackConf;
import com.audeering.sensminer.model.trackconf.AudioTrackConf;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Configuration extends AbstrDTO{

	/**
	 * Diese Enumeration benennt alle dem System bekannten Tracktypen.
	 * Wird ein neuer Track in die App aufgenommen muss der entsprechende Typ hier ergänzt werden.
	 */
	public static enum TRACKTYPE {AUDIO, ACCELEROMETER, LOCATION};

	private String recordDuration;

	/**
	 * Diese Werte können als Auxiliary gewahlt werden.
	 */
	private List<String> situationAuxiliaryValues = new ArrayList<>();

	/**
	 * Diese Werte können als Activity gew?hlt werden.
	 */
	private List<String> situationActivityValues = new ArrayList<>();

	/**
	 * Diese Werte können als Environment gew?hlt werden.
	 */
	private List<String> situationEnvironmentValues = new ArrayList<>();

	/**
	 * Diese Werte k?nnen als MobileStorage gew?hlt werden.
	 */
	private List<String> situationMobileStorageValues = new ArrayList<>();

	private Map<String, Integer> recordDurationsString2Seconds = new LinkedHashMap<>();

	private AudioTrackConf audioTrackConf = new AudioTrackConf();
	private AccelerationTrackConf accelerationTrackConf = new AccelerationTrackConf();

	public List<String> getSituationAuxiliaryValues() {
		return situationAuxiliaryValues;
	}

	public void setSituationAuxiliaryValues(List<String> situationAuxiliaryValues) {
		this.situationAuxiliaryValues = situationAuxiliaryValues;
	}

	public List<String> getSituationActivityValues() {
		return situationActivityValues;
	}

	public void setSituationActivityValues(List<String> situationActivityValues) {
		this.situationActivityValues = situationActivityValues;
	}

	public List<String> getSituationEnvironmentValues() {
		return situationEnvironmentValues;
	}

	public void setSituationEnvironmentValues(
			List<String> situationEnvironmentValues) {
		this.situationEnvironmentValues = situationEnvironmentValues;
	}

	public List<String> getSituationMobileStorageValues() {
		return situationMobileStorageValues;
	}

	public void setSituationMobileStorageValues(
			List<String> situationMobileStorageValues) {
		this.situationMobileStorageValues = situationMobileStorageValues;
	}

	public AudioTrackConf getAudioTrackConf() {
		return audioTrackConf;
	}

	public void setAudioTrackConf(AudioTrackConf audioTrackConf) {
		this.audioTrackConf = audioTrackConf;
	}

	public AccelerationTrackConf getAccelerationTrackConf() {
		return accelerationTrackConf;
	}

	public void setAccelerationTrackConf(AccelerationTrackConf accelerationTrackConf) {
		this.accelerationTrackConf = accelerationTrackConf;
	}

	public Map<String, Integer> getRecordDurations() {
		return recordDurationsString2Seconds;
	}

	public void setRecordDurations(Map<String, Integer> recordDurations) {
		this.recordDurationsString2Seconds = recordDurations;
	}

	public String getRecordDuration() {
		return recordDuration;
	}

	public void setRecordDuration(String recordDuration) {
		this.recordDuration = recordDuration;
	}
}
