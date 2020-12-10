package com.bc.bit.util;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * 数据保存
 * 
 * @author Administrator
 *
 */
public class SharePreferce {

	private static SharePreferce tool = null;

	private SharedPreferences shareprefece;
	private SharedPreferences.Editor editor;

	/**
	 * Construct
	 */
	private SharePreferce(Context context) {
		// Preferences对象
		shareprefece = context.getSharedPreferences("QingYang",
				Context.MODE_APPEND );
		editor = shareprefece.edit();
	}

	/**
	 * 获取单例 Create at 2013-6-17
	 * 
	 * @author huang
	 * @param context
	 * @return SharePreferce
	 */
	public static SharePreferce getInstance(Context context) {
		if (tool == null) {
			tool = new SharePreferce(context);
		}
		return tool;
	}

	/**
	 * 是否有key值
	 * @param key
	 * @return  有返回false 没有返回true
	 */
	public boolean isEmpty(String key) {
		return shareprefece.contains(key);
	}

	/**
	 * 清理缓存 Create at 2013-7-1
	 * 
	 */
	public void clearCache() {
		editor.clear();
		editor.commit();
	}


	/**
	 *
	 * 设置SharedPrefere缓存 Create at 2013-6-17
	 *
	 * @param key 键值
	 * @param value 缓存内容
     */
	public void setCache(String key, Object value) {
		if (value instanceof Boolean)// 布尔对象
			editor.putBoolean(key, (Boolean) value);
		else if (value instanceof String)// 字符串
			editor.putString(key, (String) value);
		else if (value instanceof Integer)// 整型数
			editor.putInt(key, (Integer) value);
		else if (value instanceof Long)// 长整型
			editor.putLong(key, (Long) value);
		else if (value instanceof Float)// 浮点数
			editor.putFloat(key, (Float) value);
		editor.commit();
	}

	/**
	 * 读取缓存中的字符串 Create at 2013-6-17
	 * 
	 * @param key
	 * @return String
	 */
	public String getString(String key) {
		return shareprefece.getString(key, "");
	}

	/**
	 * 读取缓存中的布尔型缓存 Create at 2013-6-17
	 * 
	 * @param key
	 * @return boolean
	 */
	public boolean getBoolean(String key) {
		return shareprefece.getBoolean(key, false);
	}

	/**
	 * 读取缓存中的整型数 Create at 2013-6-17
	 * 
	 * @param key
	 * @return int
	 */
	public int getInt(String key) {
		return shareprefece.getInt(key, 0);
	}

	/**
	 * 读取缓存中的长整型数 Create at 2013-6-17
	 * 
	 * @param key
	 * @return long
	 */
	public long getLong(String key) {
		return shareprefece.getLong(key, 0);
	}

	/**
	 * 读取缓存中的浮点数 Create at 2013-6-17
	 * 
	 * @param key
	 * @return float
	 */
	public float getFloat(String key) {
		return shareprefece.getFloat(key, 0.0f);
	}
	
	/**
	 * 判断是否有缓存
	 * @param string
	 * @return
	 */
	public boolean ifHaveShare(String string){
		return shareprefece.contains(string);
	}



}
