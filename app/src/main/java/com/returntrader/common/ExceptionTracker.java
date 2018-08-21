package com.returntrader.common;

public class ExceptionTracker {

    public static void track(Exception exception) {
        //Crashlytics.logException(exception);
        exception.printStackTrace();
    }

    public static void track(String message) {
        //Crashlytics.log(message);
        //Log.e();
    }
}