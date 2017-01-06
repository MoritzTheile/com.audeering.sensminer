package com.audeering.sensminer.model.situation;


import com.audeering.sensminer.model.abstr.AbstrDTO;

public class Situation extends AbstrDTO implements Comparable<Situation> {
	
	private long lastUsageTimestamp;

	private String name;

	private String auxiliary;

	private String activity;

	private String environment;

	private String mobileStorage;

	public String getAuxiliary() {
		return auxiliary;
	}

	public void setAuxiliary(String auxiliary) {
		this.auxiliary = auxiliary;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public String getMobileStorage() {
		return mobileStorage;
	}

	public void setMobileStorage(String mobileStorage) {
		this.mobileStorage = mobileStorage;
	}

	public long getLastUsageTimestamp() {
		return lastUsageTimestamp;
	}

	public void setLastUsageTimestamp(long lastUsageTimestamp) {
		this.lastUsageTimestamp = lastUsageTimestamp;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int compareTo(Situation situation) {
		Long timestamp1 = new Long(this.getLastUsageTimestamp());
		if(timestamp1 == null){
			timestamp1 = 0L;
		}
		Long timestamp2 = new Long(situation.getLastUsageTimestamp());
		if(timestamp2 == null){
			timestamp2 = 0L;
		}
		return timestamp2.compareTo(timestamp1) ;
	}
}
