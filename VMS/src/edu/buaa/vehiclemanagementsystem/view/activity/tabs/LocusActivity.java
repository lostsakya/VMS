package edu.buaa.vehiclemanagementsystem.view.activity.tabs;

import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.PopupWindow;
import android.widget.SeekBar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import com.alibaba.fastjson.JSON;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.LatLngBounds;
import com.amap.api.maps2d.model.LatLngBounds.Builder;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.PolylineOptions;
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
import edu.buaa.vehiclemanagementsystem.VMS;
import edu.buaa.vehiclemanagementsystem.controller.net.DStringRequest;
import edu.buaa.vehiclemanagementsystem.controller.parser.Parser;
import edu.buaa.vehiclemanagementsystem.model.LocusData;
import edu.buaa.vehiclemanagementsystem.model.Parameter;
import edu.buaa.vehiclemanagementsystem.model.Result;
import edu.buaa.vehiclemanagementsystem.model.Vehicle;
import edu.buaa.vehiclemanagementsystem.util.Constants;
import edu.buaa.vehiclemanagementsystem.util.LogUtil;
import edu.buaa.vehiclemanagementsystem.util.ToastUtil;
import edu.buaa.vehiclemanagementsystem.util.environment.Enviroment;
import edu.buaa.vehiclemanagementsystem.view.activity.base.BaseActivity;

@EActivity
public class LocusActivity extends BaseActivity {

	private static final float ZOOMLEVEL = 15;

	// @ViewById(R.id.btn_pause)
	// Button btnPause;

	@ViewById(R.id.ll_locus)
	LinearLayout llLocus;

	@ViewById(R.id.btn_play)
	Button btnPlay;

	@ViewById(R.id.rb_location)
	Button btnLocation;

	@ViewById(R.id.rb_locus)
	Button btnLocus;

	// @ViewById(R.id.seek_bar)
	SeekBar seekBar;

	@ViewById(R.id.btn_download_locus_info)
	Button btnDownloadLocusInfo;

	@App
	VMS vms;

	private AMap map;

	private PopupWindow popupWindow;

	private MapView mapView;

	private DatePicker dpStartTime;

	private DatePicker dpEndTime;

	private NumberPicker npIndex;

	private NumberPicker npItemPerPage;

	private String startTime;

	private String endTime;

	protected int index;

	protected int itemPerPage;

	private ImageView ivClose;

	private Marker marker;

	private ArrayList<LocusData> locusDatas;

	private ArrayList<LatLng> totalList;

	private PolylineOptions polylineOptions;

	private int size;

	private final long FREQUENCY = 1000L;

	private MarkerOptions markerOptions;

	private Builder builder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_locus);
		mapView = (MapView) findViewById(R.id.mapview);
		mapView.onCreate(savedInstanceState);// 此方法必须重写
		map = mapView.getMap();
		map.setMapType(AMap.MAP_TYPE_NORMAL);
		seekBar = (SeekBar) findViewById(R.id.seek_bar);
		seekBar.setClickable(false);
	}

	@Click(R.id.btn_download_locus_info)
	void downLocusInfo() {
		initPopupWindow();
	}

	private void initPopupWindow() {
		if (popupWindow == null) {
			View view = View.inflate(this, R.layout.popupwindow_choose_info, null);
			initView(view);
			initDataPicker();
			initNumberPicker();

			popupWindow = new PopupWindow(view);
			popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
			popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
			popupWindow.setOutsideTouchable(true);
			popupWindow.setBackgroundDrawable(new ColorDrawable(Color.GRAY));
		}
		popupWindow.setAnimationStyle(android.R.anim.fade_in);
		popupWindow.showAtLocation((View) btnDownloadLocusInfo.getParent(), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

	}

	private void initView(View view) {
		ivClose = (ImageView) view.findViewById(R.id.close);
		dpStartTime = (DatePicker) view.findViewById(R.id.dp_start_time);
		dpEndTime = (DatePicker) view.findViewById(R.id.dp_end_time);
		npIndex = (NumberPicker) view.findViewById(R.id.np_index);
		npItemPerPage = (NumberPicker) view.findViewById(R.id.np_item_per_page);
		btnDownloadLocusInfo = (Button) view.findViewById(R.id.btn_download_locus_info);

		ivClose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dismissPopupwindow();
			}
		});
		btnDownloadLocusInfo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String data = "";
				String code = ((Vehicle) getIntent().getSerializableExtra(Constants.VEHICLE)).getCode().trim();

				String filterStopPoint = "1";
				index = npIndex.getValue();
				itemPerPage = npItemPerPage.getValue();
				StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append(code);
				stringBuilder.append("!");
				stringBuilder.append(startTime);
				stringBuilder.append("!");
				stringBuilder.append(endTime);
				stringBuilder.append("!");
				stringBuilder.append(filterStopPoint);
				stringBuilder.append("!");
				stringBuilder.append(index);
				stringBuilder.append("!");
				stringBuilder.append(itemPerPage);

				data = stringBuilder.toString();
				LogUtil.log(TAG, data);
				request(data);
			}
		});

	}

	private void dismissPopupwindow() {
		if (popupWindow != null && popupWindow.isShowing()) {
			popupWindow.dismiss();
		}
	}

	void initDataPicker() {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int monthOfYear = calendar.get(Calendar.MONTH);
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		Date date = new Date();
		dpStartTime.setMaxDate(date.getTime());
		dpEndTime.setMaxDate(date.getTime());
		startTime = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
		endTime = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
		dpStartTime.init(year, monthOfYear, dayOfMonth, new OnDateChangedListener() {

			@Override
			public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				startTime = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
				LogUtil.log(TAG, "startTime " + startTime);
			}
		});

		dpEndTime.init(year, monthOfYear, dayOfMonth, new OnDateChangedListener() {

			@Override
			public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				endTime = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
				LogUtil.log(TAG, "endTime " + endTime);
			}
		});
	}

	void initNumberPicker() {
		// TODO Auto-generated method stub
		npIndex.setMinValue(0);
		npIndex.setMaxValue(100);
		npIndex.setValue(0);

		npIndex.setOnValueChangedListener(new OnValueChangeListener() {

			@Override
			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
				index = newVal;
			}
		});
		npItemPerPage.setMaxValue(Integer.MAX_VALUE);
		npItemPerPage.setMinValue(0);
		npItemPerPage.setValue(50);
		npItemPerPage.setOnValueChangedListener(new OnValueChangeListener() {

			@Override
			public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
				itemPerPage = newVal;
			}
		});
	}

	void request(String data) {
		Parameter parameter = new Parameter(8, 6, data);
		String url = Enviroment.getInstance().getUrl() + JSON.toJSONString(parameter);
		DStringRequest request = new DStringRequest(url, new Listener<String>() {
			@Override
			public void onResponse(String response) {
				try {
					Result result = JSON.parseObject(response, Result.class);
					LogUtil.log(TAG, result.toString());
					switch (result.getResultId()) {
					case 1:
						ToastUtil.shortToast(getApplicationContext(), "下载轨迹信息成功");
						LogUtil.log(TAG, "下载轨迹信息成功");
						llLocus.setVisibility(View.VISIBLE);
						dismissPopupwindow();
						String data = result.getDataList();
						LogUtil.log(TAG, data);
						locusDatas = Parser.parseLocusData(data);
						LogUtil.log(TAG, locusDatas.toString());
						totalList = getLatLngList(locusDatas);
						LogUtil.log(TAG, totalList.toString());
						size = totalList.size();
						seekBar.setMax(size);
						break;
					case 0:
						ToastUtil.shortToast(getApplicationContext(), "下载轨迹信息失败");
						LogUtil.log(TAG, "下载轨迹信息失败");
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

	@Click(R.id.btn_play)
	void play() {
		seekBar.setProgress(0);
		map.clear();
		if (size < 2) {
			return;
		}
		final LatLng startPoint = totalList.get(0);
		final LatLng endPoint = totalList.get(size - 1);

		MarkerOptions startMarkerOptions = new MarkerOptions();
		startMarkerOptions.position(startPoint).title("起点").snippet(" ")
				.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.nav_route_result_start_point))).anchor(0.5f, 0.5f);

		map.addMarker(startMarkerOptions);

		markerOptions = new MarkerOptions();
		markerOptions.position(startPoint).title(" ").snippet(" ").icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.car))).anchor(0.5f, 0.5f);
		marker = map.addMarker(markerOptions);
		addBound(startPoint);
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(startPoint, ZOOMLEVEL));
		polylineOptions = new PolylineOptions();
		polylineOptions.add(startPoint);
		map.addPolyline(polylineOptions);

		CountDownTimer countDownTimer = new CountDownTimer(size * FREQUENCY, FREQUENCY) {

			@Override
			public void onTick(long millisUntilFinished) {
				// TODO 2.添加过程，移动到下一点
				int currentProgress = totalList.size() - (int) (millisUntilFinished / FREQUENCY);
				seekBar.setProgress(currentProgress);
				polylineOptions.add(totalList.get(currentProgress)).color(Color.rgb(9, 129, 240)).width(6.0f);
				map.addPolyline(polylineOptions);
				marker.setPosition(totalList.get(currentProgress));
				LatLng currentLatLng = totalList.get(currentProgress);
				LatLngBounds bounds = addBound(currentLatLng);
				map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));
			}

			@Override
			public void onFinish() {
				// TODO 3.添加终点
				seekBar.setProgress(size);
				MarkerOptions endMarkerOptions = new MarkerOptions();
				endMarkerOptions.position(totalList.get(size - 1)).title("终点")
						.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.nav_route_result_end_point)));
				map.addMarker(endMarkerOptions);
				polylineOptions.add(endPoint);
				map.addPolyline(polylineOptions);
				LatLngBounds bounds = addBound(endPoint);
				map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));
			}
		};
		countDownTimer.start();

	}

	protected LatLngBounds addBound(LatLng latLng) {
		if (builder == null) {
			builder = new LatLngBounds.Builder();
		}
		return builder.include(latLng).build();
	}

	private ArrayList<LatLng> getLatLngList(ArrayList<LocusData> locusDatas) {
		ArrayList<LatLng> list = new ArrayList<LatLng>();
		int size = locusDatas.size();
		for (int i = 0; i < size; i++) {
			LocusData locusData = locusDatas.get(i);
			float latitude;
			float longitude;
			try {
				latitude = Float.parseFloat(locusData.getLatitude());
				longitude = Float.parseFloat(locusData.getLongitude());
				LatLng latLng = new LatLng(latitude, longitude);
				list.add(latLng);

			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return list;
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
		dismissPopupwindow();
		popupWindow = null;
	}
}
