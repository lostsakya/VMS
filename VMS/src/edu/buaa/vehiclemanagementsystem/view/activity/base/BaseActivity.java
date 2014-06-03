package edu.buaa.vehiclemanagementsystem.view.activity.base;

import edu.buaa.vehiclemanagementsystem.VMS;

import android.app.Activity;
import android.os.Bundle;

import com.android.volley.RequestQueue;

public abstract class BaseActivity extends Activity {
	protected RequestQueue mRequestQueue;
	protected String TAG = getClass().getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mRequestQueue = ((VMS) getApplication()).getRequestQueue();
	}
}
