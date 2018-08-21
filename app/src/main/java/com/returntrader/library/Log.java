package com.returntrader.library;

/**
 * Created by azeem on 2/23/2017.
 */

public class Log {

    public static void d(String name, String value) {
        android.util.Log.d(name, value);
    }

    public static void i(String name, String value) {
        android.util.Log.i(name, value);
    }

    public static void e(String name, String value) {
        android.util.Log.e(name, value);
    }

    public static void w(String name, String value) {
        android.util.Log.w(name, value);
    }
}
