package edu.buaa.vehiclemanagementsystem.view.activity.tabs;

import java.util.ArrayList;

import org.androidannotations.annotations.EActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import edu.buaa.vehiclemanagementsystem.R;
import edu.buaa.vehiclemanagementsystem.controller.net.DStringRequest;
import edu.buaa.vehiclemanagementsystem.controller.parser.Parser;
import edu.buaa.vehiclemanagementsystem.model.Parameter;
import edu.buaa.vehiclemanagementsystem.model.Result;
import edu.buaa.vehiclemanagementsystem.model.Vehicle;
import edu.buaa.vehiclemanagementsystem.model.VehicleStateInfo;
import edu.buaa.vehiclemanagementsystem.util.Constants;
import edu.buaa.vehiclemanagementsystem.util.LogUtil;
import edu.buaa.vehiclemanagementsystem.util.ToastUtil;
import edu.buaa.vehiclemanagementsystem.util.environment.Enviroment;
import edu.buaa.vehiclemanagementsystem.view.activity.base.BaseActivity;

@EActivity
public class LocationActivity extends BaseActivity {
	private MapView mapView;
	private AMap map;
	private Vehicle vehicle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location);
		mapView = (MapView) findViewById(R.id.mapview);
		mapView.onCreate(savedInstanceState);// 此方法必须重写
		map = mapView.getMap();

		Intent intent = getIntent();
		vehicle = (Vehicle) intent.getSerializableExtra(Constants.VEHICLE);
		String code = vehicle.getCode();
		request(code);
	}

	void request(String data) {
		Parameter parameter = new Parameter(8, 4, data);
		String url = Enviroment.URL + JSON.toJSONString(parameter);
		DStringRequest request = new DStringRequest(url, new Listener<String>() {

			@Override
			public void onResponse(String response) {
				try {
					Result result = JSON.parseObject(response, Result.class);
					LogUtil.log(TAG, result.toString());
					switch (result.getResultId()) {
					case 1:
						ToastUtil.shortToast(getApplicationContext(), "下载状态信息成功");
						LogUtil.log(TAG, "下载状态信息成功");
						String data = result.getDataList();
						LogUtil.log(TAG, data);
						ArrayList<VehicleStateInfo> vehicleStateInfos = Parser.parseStateInfo(data);
						LogUtil.log(TAG, vehicleStateInfos.toString());
						VehicleStateInfo vehicleStateInfo = vehicleStateInfos.get(0);
						float latitude = Float.parseFloat(vehicleStateInfo.getLatitude());
						float longitude = Float.parseFloat(vehicleStateInfo.getLongitude());
						LatLng latLng = new LatLng(latitude, longitude);
						String title = vehicleStateInfo.getPositionDescription();
						// CameraPosition LUJIAZUI = new
						// CameraPosition.Builder()
						// .target(latLng).zoom(18).bearing(0).tilt(30).build();
						LogUtil.log(TAG, title);
						map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
						Marker marker = map.addMarker(new MarkerOptions().position(latLng).title(title)
								.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.car))).anchor(0.5f, 0.5f));
						marker.showInfoWindow();
						break;
					case 0:
						ToastUtil.shortToast(getApplicationContext(), "下载状态信息失败");
						LogUtil.log(TAG, "下载状态信息失败");
						break;
					case 2:
						ToastUtil.shortToast(getApplicationContext(), "未登录");
						LogUtil.log(TAG, "未登录");
						break;
					default:
						break;
					}

				} catch (Exception e) {
					ToastUtil.longToast(getApplicationContext(), "服务端数据解析异常");
				}
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				if (error instanceof NoConnectionError) {
					ToastUtil.longToast(getApplicationContext(), "无网络连接");
				} else if (error instanceof NetworkError) {
					ToastUtil.longToast(getApplicationContext(), "网络异常");
				} else if (error instanceof ParseError) {
					ToastUtil.longToast(getApplicationContext(), "服务端数据解析异常");
				} else if (error instanceof ServerError) {
					ToastUtil.longToast(getApplicationContext(), "服务器异常");
				} else if (error instanceof TimeoutError) {
					ToastUtil.longToast(getApplicationContext(), "连接超时");
				} else if (error instanceof AuthFailureError) {
					ToastUtil.longToast(getApplicationContext(), "授权异常");
				}
			}
		});
		mRequestQueue.add(request);
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onPause() {
		super.onPause();
		mapView.onPause();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}
}
