package edu.buaa.vehiclemanagementsystem.view.fragment;

import edu.buaa.vehiclemanagementsystem.R;
import edu.buaa.vehiclemanagementsystem.util.Constants;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;

public class LocationFragment0 extends Fragment {

	private AMap aMap;
	private Marker marker;
	private Object polyline;
	private LatLng position;
	private MapView mapView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_location, container, false);
		mapView = (MapView) view.findViewById(R.id.mapview);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		mapView.onCreate(savedInstanceState);
	}

	@Override
	public void onResume() {
		super.onResume();
		mapView.onResume();
		aMap = mapView.getMap();

		// if (isVehicleOnline()) {
		// position = getPositionFromServer();
		// } else {
		// position = getLastPosition();
		// }
		// if (position != null) {
		// CameraUpdateFactory.newCameraPosition(new CameraPosition(position,
		// 18, 0, 30));
		// }
	}

	@Override
	public void onPause() {
		super.onPause();
		mapView.onPause();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}

	// private LatLng getLastPosition() {
	// return SharedPreferencesUtil.getPosition(getActivity());
	// }

	private void addMarker(LatLng position) {
		MarkerOptions markerOptions = new MarkerOptions();
		markerOptions.position(position);
		markerOptions.icon(BitmapDescriptorFactory
				.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
		marker = aMap.addMarker(markerOptions);
	}

	private boolean isVehicleOnline() {
		return false;
	}

	private LatLng getPositionFromServer() {
		return Constants.XIAN;
	}

	class LooperThread extends Thread {
		public Handler mHandler;

		@Override
		public void run() {
			Looper.prepare();
			mHandler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					switch (msg.what) {

					default:
						break;
					}
				}
			};

			Looper.loop();
		}
	}

}
