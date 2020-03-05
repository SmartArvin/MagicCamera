package com.ktc.utils;

import android.util.Log;

/**
*TODO 打印log日志
*@author Arvin
*@date 2020.01.09
*/
public class LogerUtil {
	
	private static final String TAG = "MagicCamera_";
	public static boolean enableLog = true ;

	/**
	 * TODO i信息
	 * @param tag , String msg
	 * @return void
	 */
	public static void i(String tag , String msg){
		if(enableLog){
			Log.i(TAG + tag, msg);
		}
	}
	
	/**
	 * TODO d信息
	 * @param tag , String msg
	 * @return void
	 */
	public static void d(String tag , String msg){
		if(enableLog){
			Log.d(TAG + tag, msg);
		}
	}
	
	/**
	 * TODO W信息
	 * @param tag , msg
	 * @return void
	 */
	public static void w(String tag , String msg){
		if(enableLog){
			Log.w(TAG + tag, msg);
		}
	}
	
	/**
	 * TODO E信息
	 * @param tag , String msg
	 * @return void
	 */
	public static void e(String tag , String msg){
		if(enableLog){
			Log.e(TAG + tag, msg);
		}
	}
	
}
