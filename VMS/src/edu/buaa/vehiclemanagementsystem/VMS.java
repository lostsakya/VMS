package edu.buaa.vehiclemanagementsystem;

import edu.buaa.vehiclemanagementsystem.controller.net.DVolley;
import edu.buaa.vehiclemanagementsystem.util.Constants;
import edu.buaa.vehiclemanagementsystem.util.LogUtil;
import edu.buaa.vehiclemanagementsystem.util.SharedPreferencesUtil;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

import com.android.volley.RequestQueue;

import java.util.Map;

import org.androidannotations.annotations.EApplication;

@EApplication
public class VMS extends Application {

	private RequestQueue mRequestQueue;
	private static VMS instance;

	@Override
	public void onCreate() {
		super.onCreate();
		setRequestQueue(DVolley.newRequestQueue(this));
		instance = this;
	}

	public static VMS getInstance() {
		return instance;
	}

	public RequestQueue getRequestQueue() {
		return mRequestQueue;
	}

	public void setRequestQueue(RequestQueue mRequestQueue) {
		this.mRequestQueue = mRequestQueue;
	}

	public final void setCookie(Map<String, String> headers) {
		if (headers.containsKey(Constants.SET_COOKIE)) {
			String rawCookie = headers.get(Constants.SET_COOKIE);
			if (rawCookie != null) {
				LogUtil.log(Constants.SET_COOKIE, rawCookie);
				String[] cookies = rawCookie.split(";");
				Editor editor = SharedPreferencesUtil.getCookieEditor(this);
				for (String cookie : cookies) {
					String name = cookie.split("=")[0];
					if (!TextUtils.isEmpty(name)) {
						editor.putString(name, cookie);
					}
				}
				editor.commit();
			}
		}
	}

	/**
	 * Adds session cookie to headers if exists.
	 * 
	 * @param headers
	 */
	public final void getCookie(Map<String, String> headers, String[] names) {
		if (names == null) {
			return;
		}

		SharedPreferences sharedPreferences = SharedPreferencesUtil.getSharedPreferences(
				this, Constants.COOKIE);
		StringBuilder stringBuilder = new StringBuilder();
		int length = names.length;

		for (int i = 0; i < length; i++) {
			String name = names[i];
			if (sharedPreferences.contains(name)) {
				String data = sharedPreferences.getString(name, "");
				if (!TextUtils.isEmpty(data)) {
					stringBuilder.append(data);
					if (i < length - 1) {
						stringBuilder.append(";");
					}
				}
			}
		}
		if (headers.containsKey(Constants.COOKIE)) {
			stringBuilder.append(headers.get(Constants.COOKIE));
		}
		LogUtil.log(Constants.COOKIE, stringBuilder.toString());
		headers.put(Constants.COOKIE, stringBuilder.toString());
	}

	public void clearCookie() {
		Editor editor = SharedPreferencesUtil.getCookieEditor(this);
		editor.clear();
		editor.commit();
	}
}
