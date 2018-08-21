package com.returntrader.fcm;

import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.returntrader.database.DatabaseManager;
import com.returntrader.database.NotificationMeta;
import com.returntrader.helper.AppConfigurationManager;
import com.returntrader.utils.GeneratePictureStyleNotification;

import java.util.Map;


public class MessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    private AppConfigurationManager mAppConfigurationManager;
    private DatabaseManager mDatabaseManager;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "onMessageReceived");
        mAppConfigurationManager = new AppConfigurationManager(this);
        mDatabaseManager = new DatabaseManager(this);
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        // Check if message contains a data payload.
        //if (!(TextUtils.isEmpty(mAppConfigurationManager.getUserId()))) {
            if (remoteMessage.getData() != null && remoteMessage.getData().size() > 0) {
                sendNotification(remoteMessage.getData());
            }
        //}
    }


    // [END receive_message]

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private void sendNotification(Map<String, String> messageBody) {
        new GeneratePictureStyleNotification(this, messageBody.get("title"), messageBody.get("desc"), messageBody.get("mediaUrl")).execute();
        insertDataIntoDatabase(messageBody);
    }

    private void insertDataIntoDatabase(Map<String, String> messageBody) {
        String title = messageBody.get("title");
        String content = messageBody.get("desc");
        String link = messageBody.get("link");
        String imageLink = messageBody.get("mediaUrl");
        NotificationMeta notificationMeta = new NotificationMeta();
        notificationMeta.setContent(content);
        notificationMeta.setTitle(title);
        notificationMeta.setImageLink(imageLink);
        notificationMeta.setWebLink(link);
        notificationMeta.setUpdatedAt(String.valueOf(System.currentTimeMillis()));
        mDatabaseManager.insertNotificationData(notificationMeta);
    }
}