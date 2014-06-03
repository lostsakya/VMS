package edu.buaa.vehiclemanagementsystem.view.activity;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;

import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
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
import edu.buaa.vehiclemanagementsystem.util.Constants;
import edu.buaa.vehiclemanagementsystem.util.LogUtil;
import edu.buaa.vehiclemanagementsystem.util.ToastUtil;
import edu.buaa.vehiclemanagementsystem.util.environment.Enviroment;
import edu.buaa.vehiclemanagementsystem.view.activity.base.BaseActivity;
import edu.buaa.vehiclemanagementsystem.view.activity.tabs.HomeActivity_;

@EActivity(R.layout.activity_list)
public class TerminalListActivity extends BaseActivity {

	@ViewById(R.id.lv)
	ListView lv;
	protected ArrayList<Vehicle> vehicles;

	@AfterViews
	void request() {
		String data = null;
		Parameter parameter = new Parameter(8, 2, data);
		String url = Enviroment.URL + JSON.toJSONString(parameter);
		DStringRequest request = new DStringRequest(url,
				new Listener<String>() {

					@Override
					public void onResponse(String response) {
						try {
							Result result = JSON.parseObject(response,
									Result.class);
							LogUtil.log(TAG, result.toString());
							switch (result.getResultId()) {
							case 1:
								ToastUtil.shortToast(getApplicationContext(),
										"下载全部车辆信息成功");
								LogUtil.log(TAG, "下载全部车辆信息成功");
								String data = result.getDataList();
								LogUtil.log(TAG, data);
								vehicles = Parser.parseVehicles(data);
								LogUtil.log(TAG, vehicles.toString());
								lv.setAdapter(new VehiclesAdapter(vehicles));
								break;
							case 0:
								ToastUtil.shortToast(getApplicationContext(),
										"下载全部车辆信息失败");
								LogUtil.log(TAG, "下载全部车辆信息失败");
								break;
							case 2:
								ToastUtil.shortToast(getApplicationContext(),
										"未登录");
								LogUtil.log(TAG, "未登录");
								break;
							default:
								break;
							}

						} catch (Exception e) {
							ToastUtil.longToast(getApplicationContext(),
									"服务端数据解析异常");
						}
					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						if (error instanceof NoConnectionError) {
							ToastUtil.longToast(getApplicationContext(),
									"无网络连接");
						} else if (error instanceof NetworkError) {
							ToastUtil
									.longToast(getApplicationContext(), "网络异常");
						} else if (error instanceof ParseError) {
							ToastUtil.longToast(getApplicationContext(),
									"服务端数据解析异常");
						} else if (error instanceof ServerError) {
							ToastUtil.longToast(getApplicationContext(),
									"服务器异常");
						} else if (error instanceof TimeoutError) {
							ToastUtil
									.longToast(getApplicationContext(), "连接超时");
						} else if (error instanceof AuthFailureError) {
							ToastUtil
									.longToast(getApplicationContext(), "授权异常");
						}
					}
				});
		mRequestQueue.add(request);
	}

	@ItemClick(R.id.lv)
	void itemClick(int position) {
		LogUtil.log(TAG, String.valueOf(position));
		Vehicle vehicle = vehicles.get(position);
		Intent intent = new Intent(this, HomeActivity_.class);
		intent.putExtra(Constants.VEHICLE, vehicle);
		startActivity(intent);
	}

	class VehiclesAdapter extends BaseAdapter {

		private List list;

		public VehiclesAdapter(List list) {
			this.list = list;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Vehicle vehicle = (Vehicle) list.get(position);
			Holder holder;
			if (convertView == null) {
				holder = new Holder();
				convertView = View.inflate(getApplication(), R.layout.item,
						null);
				holder.license = (TextView) convertView
						.findViewById(R.id.tv_item);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}
			holder.license.setText(Html.fromHtml(vehicle.toString()));
			return convertView;
		}
	}

	static class Holder {
		TextView license;
	}
}
