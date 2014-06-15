package edu.buaa.vehiclemanagementsystem.view.activity;

import android.view.View;
import android.view.ViewGroup;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import com.alibaba.fastjson.JSON;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import edu.buaa.vehiclemanagementsystem.R;
import edu.buaa.vehiclemanagementsystem.VMS_;
import edu.buaa.vehiclemanagementsystem.controller.net.DStringRequest;
import edu.buaa.vehiclemanagementsystem.model.Parameter;
import edu.buaa.vehiclemanagementsystem.model.Result;
import edu.buaa.vehiclemanagementsystem.util.ToastUtil;
import edu.buaa.vehiclemanagementsystem.util.environment.Enviroment;
import edu.buaa.vehiclemanagementsystem.view.activity.base.BaseActivity;

@EActivity(R.layout.activity_login)
public class LoginActivity extends BaseActivity {
	@ViewById(R.id.et_username)
	EditText etUsername;

	@ViewById(R.id.et_password)
	EditText etPassword;

	@ViewById(R.id.btn_login)
	Button btnLogin;

	@ViewById(R.id.btn_logout)
	Button btnLogout;
	@ViewById(R.id.sp)
	Spinner spinner;

	private boolean firstLogin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestServerList();
	}

	/**
	 * 请求服务器列表
	 */
	private void requestServerList() {

	}

	/**
	 * 1、登录验证<br/>
	 * 申请参数：{"Func":8,"Type":1,"Data":"用户名 ! 密码"} <br/>
	 * 返回结果：{"ResultID":结果编号} <br/>
	 * 说明：<br/>
	 * 结果编号：0：成功，1：用户不存在，2：密码错误，3：没有权限<br/>
	 */
	@Click(R.id.btn_login)
	void loginClick() {
		firstLogin = true;
		login();
	}

	@AfterViews
	void afterView() {
		spinner.setAdapter(new SpinnerAdapter() {

			@Override
			public void unregisterDataSetObserver(DataSetObserver observer) {
				// TODO Auto-generated method stub

			}

			@Override
			public void registerDataSetObserver(DataSetObserver observer) {
				// TODO Auto-generated method stub

			}

			@Override
			public boolean isEmpty() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean hasStableIds() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public int getViewTypeCount() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public int getItemViewType(int position) {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public View getDropDownView(int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				return null;
			}
		});
	}

	private void login() {
		String username = etUsername.getText().toString();
		String password = etPassword.getText().toString();
		if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
			ToastUtil.shortToast(this, "用户名或密码不能为空");
			return;
		}
		String data = username + "!" + password;
		Parameter parameter = new Parameter(8, 1, data);
		String url = Enviroment.getInstance().getUrl() + JSON.toJSONString(parameter);
		DStringRequest request = new DStringRequest(url, new com.android.volley.Response.Listener<String>() {

			@Override
			public void onResponse(String response) {
				if (response != null) {
					Result result = JSON.parseObject(response, Result.class);
					switch (result.getResultId()) {
					case 0:
						if (!firstLogin) {
							ToastUtil.shortToast(getApplicationContext(), "登录成功");
							startActivity(new Intent(getApplicationContext(), TerminalListActivity_.class));
						} else {
							login();
						}
						firstLogin = false;
						break;
					case 1:
						ToastUtil.shortToast(getApplicationContext(), "用户不存在");
						break;
					case 2:
						ToastUtil.shortToast(getApplicationContext(), "密码错误");
						break;
					case 3:
						ToastUtil.shortToast(getApplicationContext(), "没有权限");
						break;
					default:
						break;
					}
				}
			}

		}, new com.android.volley.Response.ErrorListener() {
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

	@Click(R.id.btn_logout)
	void logout() {
		String data = null;
		Parameter parameter = new Parameter(8, 0, data);
		String url = Enviroment.getInstance().getUrl() + JSON.toJSONString(parameter);
		DStringRequest request = new DStringRequest(url, null, new com.android.volley.Response.Listener<String>() {

			@Override
			public void onResponse(String response) {
				if (response != null) {
					try {
						Result result = JSON.parseObject(response, Result.class);
						switch (result.getResultId()) {
						case 0:
							VMS_.getInstance().clearCookie();
							ToastUtil.shortToast(getApplicationContext(), "注销成功");
							break;
						case 1:
							ToastUtil.shortToast(getApplicationContext(), "注销失败");
							break;
						default:
							break;
						}
					} catch (Exception e) {
						ToastUtil.longToast(getApplicationContext(), "服务端数据解析异常");
					}
				}
			}

		}, new com.android.volley.Response.ErrorListener() {
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

	// @Click(R.id.btn_function)
	// void function() {
	// startActivity(new Intent(this, FunctionActivity_.class));
	// }

}
