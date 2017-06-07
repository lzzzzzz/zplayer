package cn.zplayer.mc.framework.utils.SharePreferenceUtils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

import cn.zplayer.mc.Utils.Config;


public class SharedPrefUtil {

    /**
     * 保存SharedPreferences工具类
     *
     * @date 2013-11-19 下午3:19:40
     * @author lkk
     */
    private static SharedPreferences.Editor mEditor;
    private static SharedPreferences mSharedPreferences;
    private static SharedPrefUtil mSharedPrefUtil;

    private SharedPrefUtil(Context cxt) {
        mSharedPreferences = cxt.getSharedPreferences(Config.SHARED_CACHE,
                Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    public static SharedPrefUtil getInstance(Context context) {
        if (mSharedPrefUtil == null) {
            mSharedPrefUtil = new SharedPrefUtil(context);
        }
        return mSharedPrefUtil;
    }

    public void putString(String key, String value) {
        mEditor.putString(key, value);
    }

    public void putArray(String key, Set<String> value) {
        mEditor.putStringSet(key, value);
    }

    public void putInt(String key, int value) {
        mEditor.putInt(key, value);
    }

    public void putFloat(String key, float value) {
        mEditor.putFloat(key, value);
    }

    public void putLong(String key, long value) {
        mEditor.putLong(key, value);
    }

    public void putBoolean(String key, boolean value) {
        mEditor.putBoolean(key, value);
    }

    public int getInt(String key, int defaultValue) {
        return mSharedPreferences.getInt(key, defaultValue);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return mSharedPreferences.getBoolean(key, defaultValue);
    }

    public float getFloat(String key, float defaultValue) {
        return mSharedPreferences.getFloat(key, defaultValue);
    }

    public long getLong(String key, long defaultValue) {
        return mSharedPreferences.getLong(key, defaultValue);
    }

    public String getString(String key, String defaultValue) {
        return mSharedPreferences.getString(key, defaultValue);
    }

    public void commit() {
        mEditor.commit();
    }

    public void clear() {
        mEditor.clear();
    }

}
