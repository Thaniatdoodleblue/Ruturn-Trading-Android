package com.returntrader.utils;

import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.returntrader.R;
import com.returntrader.view.activity.HomeActivity;
import com.returntrader.view.activity.NotificationActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

/**
 * Created by moorthy on 11/25/2017.
 */

public class GeneratePictureStyleNotification extends AsyncTask<String, Void, Bitmap> {

    private Context mContext;
    private String title, message, imageUrl;
    private String TAG = getClass().getSimpleName();

    public GeneratePictureStyleNotification(Context context, String title, String message, String imageUrl) {
        super();
        this.mContext = context;
        this.title = title;
        this.message = message;
        this.imageUrl = imageUrl;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        InputStream in;
        try {
            URL url = new URL(this.imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            in = connection.getInputStream();
            return BitmapFactory.decodeStream(in);
        } catch (IOException error) {
            error.printStackTrace();
            try {
                return Glide.
                        with(mContext).
                        load(this.imageUrl).
                        asBitmap().
                        into(500, 250). // Width and height
                        get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        /*try {
            return Glide.
                    with(mContext).
                    load(this.imageUrl).
                    asBitmap().
                    into(500, 250). // Width and height
                    get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            try {
                URL url = new URL(this.imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                in = connection.getInputStream();
                return BitmapFactory.decodeStream(in);
            } catch (IOException error) {
                error.printStackTrace();
            }
        }*/
        return null;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);

        Intent intent = new Intent(mContext, HomeActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(mContext);
        stackBuilder.addParentStack(HomeActivity.class);
        stackBuilder.addNextIntent(intent);

        Intent intentNotification = new Intent(mContext, NotificationActivity.class); // Notification detail activity
        stackBuilder.addNextIntent(intentNotification);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        /*{jse=, desc=JSE Limited is the oldest existing and largest stock exchange in Africa. It is situated at the corner of Maude Street and Gwen Lane in Sandton, Johannesburg, South Africa., link=http://localhost/return_trader/Dashboard/add_articles, title=RT testing, breaking_news=Breaking News, company_news=}*/
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mContext, String.valueOf(System.currentTimeMillis()))
                .setSmallIcon(R.drawable.ic_view_notify)
                .setColor(ContextCompat.getColor(mContext, R.color.colorSDGray))//R.color.homePage
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(result).setSummaryText(message))
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify((int) System.currentTimeMillis(), notificationBuilder.build());
    }
}