package edu.buaa.vehiclemanagementsystem.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.amap.api.maps2d.model.LatLng;

public class SharedPreferencesUtil {

	public static void clear(Context context, String filename) {
		SharedPreferences sharedPreferences = getSharedPreferences(context, filename);
		Editor editor = sharedPreferences.edit();
		editor.clear();
		editor.commit();
	}

	public static void savePosition(Context context, LatLng position) {
		if (position == null) {
			throw new IllegalArgumentException("position cannot be null");
		}
		SharedPreferences sharedPreferences = getSharedPreferences(context, "VMS");
		Editor editor = sharedPreferences.edit();
		double latitude = position.latitude;
		double longitude = position.longitude;
		editor.putFloat("latitude", (float) latitude);
		editor.putFloat("longitude", (float) longitude);
		editor.commit();
	}

	public static LatLng getPosition(Context context) {
		SharedPreferences sharedPreferences = getSharedPreferences(context, "VMS");
		double latitude = sharedPreferences.getFloat("latitude", -1f);
		double longitude = sharedPreferences.getFloat("longitude", -1f);
		if (latitude == -1f || longitude == -1f) {
			return null;
		}
		return new LatLng(latitude, longitude);
	}

	public static Editor getCookieEditor(Context context) {
		return getSharedPreferences(context, "Cookie").edit();
	}

	public static SharedPreferences getSharedPreferences(Context context, String filename) {
		return context.getSharedPreferences(filename, Context.MODE_PRIVATE);
	}
}
