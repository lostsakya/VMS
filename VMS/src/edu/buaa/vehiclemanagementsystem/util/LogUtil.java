package edu.buaa.vehiclemanagementsystem.util;

import android.text.TextUtils;
import android.util.Log;

import edu.buaa.vehiclemanagementsystem.util.environment.Enviroment;

/**
 * Log Util with switch and level control
 * 
 * @author Hongjian.Liu 4:51:50 PM Nov 7, 2013
 */
public class LogUtil {
	private static final boolean LOGABLE = Enviroment.DEBUG;

	private static final int LOG_LEVEL_VERBOSE = 1;
	private static final int LOG_LEVEL_DEBUG = 2;
	private static final int LOG_LEVEL_INFO = 3;
	private static final int LOG_LEVEL_WARN = 4;
	private static final int LOG_LEVEL_ERROR = 5;

	private static final int LOG_LEVEL = LOG_LEVEL_VERBOSE;

	/**
	 * log with switch and level control
	 * 
	 * @param tag
	 * @param msg
	 */
	public static void log(String tag, String msg) {
		if (!TextUtils.isEmpty(msg)) {
			if (LOGABLE) {
				switch (LOG_LEVEL) {
				case LOG_LEVEL_VERBOSE:
					Log.v(tag, msg);
					break;
				case LOG_LEVEL_DEBUG:
					Log.d(tag, msg);
					break;
				case LOG_LEVEL_INFO:
					Log.i(tag, msg);
					break;
				case LOG_LEVEL_WARN:
					Log.w(tag, msg);
					break;
				case LOG_LEVEL_ERROR:
					Log.e(tag, msg);
					break;

				default:
					break;
				}
			}
		}
	}

	/**
	 * log.v() with switch control
	 * 
	 * @param tag
	 * @param msg
	 */
	public static void v(String tag, String msg) {
		if (LOGABLE) {
			Log.v(tag, msg);
		}
	}

	/**
	 * log.d() with switch control
	 * 
	 * @param tag
	 * @param msg
	 */
	public static void d(String tag, String msg) {
		if (LOGABLE) {
			Log.d(tag, msg);
		}
	}

	/**
	 * log.i() with switch control
	 * 
	 * @param tag
	 * @param msg
	 */
	public static void i(String tag, String msg) {
		if (LOGABLE) {
			Log.i(tag, msg);
		}
	}

	/**
	 * log.w() with switch control
	 * 
	 * @param tag
	 * @param msg
	 */
	public static void w(String tag, String msg) {
		if (LOGABLE) {
			Log.w(tag, msg);
		}
	}

	/**
	 * log.e() with switch control
	 * 
	 * @param tag
	 * @param msg
	 */
	public static void e(String tag, String msg) {
		if (LOGABLE) {
			Log.e(tag, msg);
		}
	}

}
