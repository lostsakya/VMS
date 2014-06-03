package edu.buaa.vehiclemanagementsystem.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
	private static Toast toast;

	public static void toast(Context context, String text, int duration) {
		if (toast == null) {
			toast = Toast.makeText(context, text, duration);
		} else {
			toast.setText(text);
			toast.setDuration(duration);
		}
		toast.show();
	}

	public static void shortToast(Context context, String text) {
		toast(context, text, Toast.LENGTH_SHORT);
	}

	public static void longToast(Context context, String text) {
		toast(context, text, Toast.LENGTH_SHORT);
	}
}
