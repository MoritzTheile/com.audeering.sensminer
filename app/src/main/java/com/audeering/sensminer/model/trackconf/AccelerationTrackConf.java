package com.audeering.sensminer.model.trackconf;

import com.audeering.sensminer.model.configuration.Configuration.TRACKTYPE;

public class AccelerationTrackConf extends AbstrTrackConf{

	
	@Override
	public TRACKTYPE getTrackType() {
		return TRACKTYPE.ACCELEROMETER;
	}

	public static void main(String args[]  ){
        System.out.println("hello android world");
    }
}
