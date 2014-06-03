package edu.buaa.vehiclemanagementsystem.model;

import com.amap.api.maps2d.model.LatLng;

public class Point implements Comparable<Long> {
	private long time;
	private LatLng coordinate;

	@Override
	public int compareTo(Long another) {
		return Long.valueOf(time).compareTo(another);
	}

}
