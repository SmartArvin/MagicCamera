package com.ktc.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.ktc.MagicCameraApplication;

public class SPUtil {
    private SharedPreferences mSharedPreferences;
    private static SPUtil mSPUtils;
    private static final String FILE_NAME = "Launcher" ;

    
    public SPUtil(Context context) {
		this.mSharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
	}

	/*public static SharedPreferencesUtil getInstance(Context context) {
		if (mSharedPreferencesUtil == null) {
			mSharedPreferencesUtil = new SharedPreferencesUtil(context);
		}
		return mSharedPreferencesUtil;
	}*/

	public static SPUtil getInstance() {
		if (mSPUtils == null) {
			mSPUtils = new SPUtil(MagicCameraApplication.getContext());
		}
		return mSPUtils;
	}
	
    /**
     * 自定义的ContentValue类，来进行对多个数据的处理
     */
   public static class ContentValue {
        String key;
        Object value;

        public ContentValue(String key, Object value) {
            this.key = key;
            this.value = value;
        }
    }

    //一次可以传入多个ContentValue对象的值
    public void putValues(ContentValue... contentValues) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        for (ContentValue contentValue : contentValues) {
            if (contentValue.value instanceof String) {
                editor.putString(contentValue.key, contentValue.value.toString()).commit();
            }else if (contentValue.value instanceof Integer) {
                editor.putInt(contentValue.key, Integer.parseInt(contentValue.value.toString())).commit();
            }else if (contentValue.value instanceof Long) {
                editor.putLong(contentValue.key, Long.parseLong(contentValue.value.toString())).commit();
            }else if (contentValue.value instanceof Boolean) {
                editor.putBoolean(contentValue.key, Boolean.parseBoolean(contentValue.value.toString())).commit();
            }

        }
    }

    //获取数据的方法
    public String getString(String key) {
        return mSharedPreferences.getString(key, null);
    }

    public void putString(String key , String value){
        putValues(new ContentValue(key ,value));
    }

    public boolean getBoolean(String key) {
        return mSharedPreferences.getBoolean(key, false);
    }

    public void putBoolean(String key , Boolean value){
        putValues(new ContentValue(key ,value));
    }

    public int getInt(String key) {
        return mSharedPreferences.getInt(key, 0);
    }

    public void putInt(String key , Integer value){
        putValues(new ContentValue(key ,value));
    }

    public long getLong(String key) {
        return mSharedPreferences.getLong(key, 0);
    }

    public void putLong(String key , Long value){
        putValues(new ContentValue(key ,value));
    }

    public void clear() {
        mSharedPreferences.edit().clear().commit();
    }

}
