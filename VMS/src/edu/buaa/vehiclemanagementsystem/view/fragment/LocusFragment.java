package edu.buaa.vehiclemanagementsystem.view.fragment;

import edu.buaa.vehiclemanagementsystem.R;
import edu.buaa.vehiclemanagementsystem.util.Constants;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import java.util.ArrayList;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentById;
import org.androidannotations.annotations.ViewById;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.SupportMapFragment;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.PolylineOptions;

@EFragment(R.layout.fragment_locus)
public class LocusFragment extends Fragment implements OnSeekBarChangeListener {

	private static final String REPLAY = "回放";
	private static final String STOP = "停止";
	@FragmentById(R.id.map)
	SupportMapFragment fragmentMap;
	@ViewById(R.id.btn_play)
	Button btnPlay;
	@ViewById(R.id.seek_bar)
	SeekBar seekbar;

	private AMap aMap;
	private Marker marker;// 当前轨迹点图案
	public Handler timer = new Handler();// 定时器
	public Runnable runnable;

	// 存放所有坐标的数组
	private ArrayList<LatLng> latlngList = new ArrayList<LatLng>();
	private ArrayList<LatLng> latlngList_path = new ArrayList<LatLng>();
	private ArrayList<LatLng> latlngList_path1 = new ArrayList<LatLng>();
	private Handler handler;

	@Override
	public void onResume() {
		super.onResume();
		if (aMap == null) {
			aMap = fragmentMap.getMap();
			if (aMap != null) {
				setUpMap();
			}
		}
	}

	/**
	 * 初始化AMap对象
	 */
	@AfterViews
	void init() {

		// 加入坐标
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

		// 设置进度条最大长度为数组长度
		seekbar.setMax(latlngList.size());
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					int currentProgress = seekbar.getProgress();
					if (currentProgress != seekbar.getMax()) {
						seekbar.setProgress(currentProgress + 1);
						timer.postDelayed(runnable, 500);// 延迟0.5秒后继续执行
					} else {
						btnPlay.setText(REPLAY);// 已执行到最后一个坐标 停止任务
					}
				}
			}
		};
		runnable = new Runnable() {
			@Override
			public void run() {
				handler.sendMessage(Message.obtain(handler, 1));
			}
		};

	}

	private void drawLine(ArrayList<LatLng> list, int current) {
		aMap.clear();
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
		marker = aMap.addMarker(markerOptions);
		// 增加起点开始
		aMap.addMarker(new MarkerOptions()
				.position(latlngList.get(0))
				.title("起点")
				.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(
						getResources(), R.drawable.nav_route_result_start_point))));
		// 增加起点结束
		if (latlngList_path.size() > 1) {
			PolylineOptions polylineOptions = (new PolylineOptions())
					.addAll(latlngList_path).color(Color.rgb(9, 129, 240)).width(6.0f);
			aMap.addPolyline(polylineOptions);
		}
		if (latlngList_path.size() == latlngList.size()) {
			aMap.addMarker(new MarkerOptions()
					.position(latlngList.get(latlngList.size() - 1))
					.title("终点")
					.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
							.decodeResource(getResources(),
									R.drawable.nav_route_result_end_point))));
		}
	}

	private void setUpMap() {
		aMap.setMapType(AMap.MAP_TYPE_NORMAL);
		aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlngList.get(0), 4));
	}

	@Click(R.id.btn_play)
	void replay() {
		// 根据按钮上的字判断当前是否在回放
		if (btnPlay.getText().toString().trim().equals(REPLAY)) {
			if (latlngList != null && latlngList.size() > 0) {
				// 假如当前已经回放到最后一点 置0
				if (seekbar.getProgress() == seekbar.getMax()) {
					seekbar.setProgress(0);
				}
				// 将按钮上的字设为"停止" 开始调用定时器回放
				btnPlay.setText(STOP);
				timer.postDelayed(runnable, 10);
			}
		} else {
			// 移除定时器的任务
			timer.removeCallbacks(runnable);
			btnPlay.setText(REPLAY);
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	// 第二个方法onProgressChanged是当进度发生改变时执行
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

		latlngList_path.clear();
		if (progress != 0) {
			for (int i = 0; i < seekBar.getProgress(); i++) {
				latlngList_path.add(latlngList.get(i));
			}
			drawLine(latlngList_path, progress);
		}
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// 第三个是onStopTrackingTouch,在停止拖动时执行
	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {

		latlngList_path.clear();
		int current = seekBar.getProgress();
		if (current != 0) {
			for (int i = 0; i < seekBar.getProgress(); i++) {
				latlngList_path.add(latlngList.get(i));
			}
			drawLine(latlngList_path, current);
		}
	}
}
