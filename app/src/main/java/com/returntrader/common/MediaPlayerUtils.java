package com.returntrader.common;

import android.media.MediaPlayer;

public class MediaPlayerUtils {
    //Single ton object...
    private static MediaPlayerUtils mediaPlayerUtils = null;
    private final String TAG = getClass().getSimpleName();
    private MediaPlayer mediaPlayer;

    //Single ton method...
    public static MediaPlayerUtils getInstance() {
        if (mediaPlayerUtils != null) {
            return mediaPlayerUtils;
        } else {
            mediaPlayerUtils = new MediaPlayerUtils();
            return mediaPlayerUtils;
        }
    }




}
