package edu.buaa.vehiclemanagementsystem.view.fragment;

import edu.buaa.vehiclemanagementsystem.R;
import edu.buaa.vehiclemanagementsystem.VMS;
import edu.buaa.vehiclemanagementsystem.model.Parameter;
import edu.buaa.vehiclemanagementsystem.util.Constants;
import edu.buaa.vehiclemanagementsystem.util.environment.Enviroment;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.CheckedChange;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.SeekBarProgressChange;
import org.androidannotations.annotations.ViewById;

import com.alibaba.fastjson.JSON;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.PolylineOptions;

@EFragment(R.layout.fragment_location)
public class LocationFragment extends BaseFragment implements Listener<String>,
		ErrorListener {

	@ViewById(R.id.mapview)
	MapView mapView;

	@ViewById(R.id.rl_locus)
	RelativeLayout rlLocus;

	@ViewById(R.id.btn_pause)
	Button btnPause;

	@ViewById(R.id.btn_play)
	Button btnPlay;

	@ViewById(R.id.rb_location)
	Button btnLocation;

	@ViewById(R.id.rb_locus)
	Button btnLocus;

	@ViewById(R.id.seek_bar)
	SeekBar seekBar;

	@App
	VMS vms;

	private AMap map;
	// 存放所有坐标的数组
	private ArrayList<LatLng> latlngList = new ArrayList<LatLng>();

	private Timer timer;

	private TimerTask timerTask;

	private Handler handler;

	protected int count;

	private Marker marker;

	private ArrayList<LatLng> pathList;

	private StringRequest request;

	private RequestQueue mRequestQueue;

	private String startTime;

	private String endTime;

	private int filterStopPoint = 1;

	private int index = 0;

	private int itemPerPage = 50;

	/**
	 * 7、下载轨迹信息数量<br/>
	 * 申请参数：{"Func":8,"Type":6, "Data":
	 * "JlyID ! StartTime ! EndTime ! FilterStopPoint!index ! itemperpage"}<br/>
	 * 返回结果：{"ResultID":结果编号, DataList:"轨迹列表"} <br/>
	 * 说明：<br/>
	 * 结果编号：0：成功，1：失败，2：未登录<br/>
	 * JlyID：终端编号<br/>
	 * StartTime：开始时间<br/>
	 * EndTime：结束时间<br/>
	 * FilterStopPoint：是否过滤速度为0 的数据 1、是；0、否<br/>
	 * Index: 分页序号从0开始<br/>
	 * Itemperpage：每页数量 目前规定为50条<br/>
	 * 轨迹列表：轨迹数据|轨迹数据……<br/>
	 * 轨迹数据：时间,定位状态(0定位无效，1定位有效),速度,方向,油位,驾驶员姓名,经度,纬度,海拔,报警描述,状态描述,位置描述<br/>
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mapView.onCreate(savedInstanceState);
	}

	@AfterViews
	void init() {
		mRequestQueue = vms.getRequestQueue();
		String jlyId = "";

		String data = jlyId + "!" + startTime + "!" + endTime + "!" + filterStopPoint
				+ "!" + index + "!" + itemPerPage;
		Parameter parameter = new Parameter(8, 1, data);
		String url = Enviroment.URL + JSON.toJSONString(parameter);
		request = new StringRequest(Enviroment.URL, new Listener<String>() {

			@Override
			public void onResponse(String response) {

			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {

			}
		});
		mRequestQueue.add(request);

		map.setMapType(AMap.MAP_TYPE_NORMAL);
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlngList.get(0), 4));
		latlngList.add(Constants.marker1);
		latlngList.add(Constants.marker2);
		latlngList.add(Constants.marker3);
		latlngList.add(Constants.marker4);
		latlngList.add(Constants.marker5);
		latlngList.add(Constants.marker6);
		latlngList.add(Constants.marker7);
		latlngList.add(Constants.marker8);
		latlngList.add(Constants.marker9);
		latlngList.add(Constants.marker10);

		seekBar.setMax(latlngList.size());

		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				int progress = seekBar.getProgress();
				if (progress != seekBar.getMax()) {
					seekBar.setProgress(progress + 1);
				} else {
					timer.cancel();
				}
			}
		};
		timer = new Timer();
		timerTask = new TimerTask() {

			@Override
			public void run() {
				handler.sendEmptyMessage(0);
			}
		};
	}

	@CheckedChange(R.id.rb_location)
	void location(CompoundButton button, boolean isChecked) {
		if (isChecked) {
			rlLocus.setVisibility(View.GONE);
			map.clear();
		}
	}

	@CheckedChange(R.id.rb_locus)
	void locus(CompoundButton button, boolean isChecked) {
		if (isChecked) {
			rlLocus.setVisibility(View.VISIBLE);
			map.clear();
		}
	}

	@Click(R.id.btn_play)
	void play() {
		timer.schedule(timerTask, 0, 500);
	}

	@SeekBarProgressChange(R.id.seek_bar)
	void progressChange(SeekBar seekBar, int progress, boolean fromUser) {
		pathList = new ArrayList<LatLng>(latlngList.subList(0, progress));
	}

	private void drawLine(ArrayList<LatLng> list, int current) {
		map.clear();
		LatLng replayGeoPoint = latlngList.get(current - 1);
		if (marker != null) {
			marker.destroy();
		}
		// 添加汽车位置
		MarkerOptions markerOptions = new MarkerOptions();
		markerOptions
				.position(replayGeoPoint)
				.title("起点")
				.snippet(" ")
				.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(
						getResources(), R.drawable.car))).anchor(0.5f, 0.5f);
		marker = map.addMarker(markerOptions);
		// 增加起点开始
		map.addMarker(new MarkerOptions()
				.position(latlngList.get(0))
				.title("起点")
				.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(
						getResources(), R.drawable.nav_route_result_start_point))));
		// 增加起点结束
		if (pathList.size() > 1) {
			PolylineOptions polylineOptions = (new PolylineOptions()).addAll(pathList)
					.color(Color.rgb(9, 129, 240)).width(6.0f);
			map.addPolyline(polylineOptions);
		}
		if (pathList.size() == latlngList.size()) {
			map.addMarker(new MarkerOptions()
					.position(latlngList.get(latlngList.size() - 1))
					.title("终点")
					.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
							.decodeResource(getResources(),
									R.drawable.nav_route_result_end_point))));
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		mapView.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		mapView.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	@Override
	public void onErrorResponse(VolleyError error) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onResponse(String response) {
		// TODO Auto-generated method stub

	}
}
