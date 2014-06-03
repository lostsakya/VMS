package edu.buaa.vehiclemanagementsystem.controller.net;

import edu.buaa.vehiclemanagementsystem.VMS_;
import edu.buaa.vehiclemanagementsystem.util.Constants;
import edu.buaa.vehiclemanagementsystem.util.LogUtil;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DStringRequest extends StringRequest {

	private String[] cookieNames;

	public DStringRequest(String url, Listener<String> listener,
			ErrorListener errorListener) {
		super(url, listener, errorListener);
		this.cookieNames = new String[] { Constants.ASP_NET_SESSIONID,
				Constants.CAR_ADMIN_USER_COOKIE_DATA };
	}

	public DStringRequest(String url, String[] cookieNames, Listener<String> listener,
			ErrorListener errorListener) {
		super(url, listener, errorListener);
		this.cookieNames = cookieNames;
	}

	public DStringRequest(int method, String url, String[] cookieNames,
			Listener<String> listener, ErrorListener errorListener) {
		super(method, url, listener, errorListener);
		this.cookieNames = cookieNames;
	}

	@Override
	protected Response<String> parseNetworkResponse(NetworkResponse response) {
		VMS_.getInstance().setCookie(response.headers);
		String parsed;
		try {
			parsed = new String(response.data,
					HttpHeaderParser.parseCharset(response.headers));
		} catch (UnsupportedEncodingException e) {
			parsed = new String(response.data);
		}
		LogUtil.log("response", parsed);
		return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		Map<String, String> headers = super.getHeaders();
		if (headers == null || headers.equals(Collections.emptyMap())) {
			headers = new HashMap<String, String>();
			VMS_.getInstance().getCookie(headers, cookieNames);
		}
		return headers;
	}
}
