package com.returntrader.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


/**
 * This class handles the all the shared preference operation.
 * .i.e., creating shared preference and to set and get value.
 *
 * @author Jeevanandhan
 */

public class SharedPref {


    // Single ton objects...
    private SharedPreferences preference = null;


    /**
     * Singleton object for the shared preference.
     *
     * @param context Context of current state of the application/object
     * @return SharedPreferences object is returned.
     */

    private SharedPreferences getPreferenceInstance(Context context) {

        if (preference != null) {
            return preference;
        } else {
            //TODO: Shared Preference name has to be set....
            preference = context.getSharedPreferences("com.db.ruturntrader", Context.MODE_PRIVATE);
            return preference;
        }
    }

    /**
     * Set the String value in the shared preference W.R.T the given key.
     *
     * @param context Context of current state of the application/object
     * @param key     String used as a key for accessing the value.
     * @param value   String value which is to be stored in shared preference.
     */

    public void setSharedValue(Context context, String key, String value) {
        getPreferenceInstance(context);
        Editor editor = preference.edit();
        editor.putString(key, value);
        editor.apply();
    }


    /**
     * Set the Integer value in the shared preference W.R.T the given key.
     *
     * @param context Context of current state of the application/object
     * @param key     String used as a key for accessing the value.
     * @param value   Integer value which is to be stored in shared preference.
     */

    public void setSharedValue(Context context, String key, int value) {
        getPreferenceInstance(context);
        Editor editor = preference.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * Set the boolean value in the shared preference W.R.T the given key.
     *
     * @param context Context of current state of the application/object
     * @param key     String used as a key for accessing the value.
     * @param value   Boolean value which is to be stored in shared preference.
     */

    public void setSharedValue(Context context, String key, Boolean value) {
        getPreferenceInstance(context);
        Editor editor = preference.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * Returns Boolean value for the given key.
     * By default it will return "false".
     *
     * @param context Context of current state of the application/object
     * @param key     String used as a key for accessing the value.
     * @return false by default; returns the Boolean value for the given key.
     */

    public Boolean getBooleanValue(Context context, String key) {
        return getPreferenceInstance(context).getBoolean(key, false);
    }


    /**
     * get boolean value for the key and can set desired default key values to return
     *
     * @param context
     * @param key
     * @param defaultValue
     * @return
     */

    public Boolean getBooleanValue(Context context, String key, boolean defaultValue) {
        return getPreferenceInstance(context).getBoolean(key, defaultValue);
    }

    /**
     * Returns Integer value for the given key.
     * By default it will return "-1".
     *
     * @param context Context of current state of the application/object
     * @param key     String used as a key for accessing the value.
     * @return -1 by default; returns the Integer value for the given key.
     */

    public int getIntValue(Context context, String key) {
        return getPreferenceInstance(context).getInt(key, -1);
    }

    /**
     * Returns Integer value for the given key.
     * By default it will return return mentioned value as default value.
     */

    public int getIntValue(Context context, String key, int defaultValue) {
        return getPreferenceInstance(context).getInt(key, defaultValue);
    }


    /**
     * Returns String value for the given key.
     * By default it will return null.
     *
     * @param context Context of current state of the application/object
     * @param key     String used as a key for accessing the value.
     * @return null by default; returns the String value for the given key.
     */

    public String getStringValue(Context context, String key) {
        return getPreferenceInstance(context).getString(key, null);
    }


    public String getStringValue(Context context, String key,String defaultValue) {
        return getPreferenceInstance(context).getString(key, defaultValue);
    }

    public void clearPreference(Context context) {
        getPreferenceInstance(context);
        Editor editor = preference.edit();
        editor.clear();
        editor.apply();
    }
}
