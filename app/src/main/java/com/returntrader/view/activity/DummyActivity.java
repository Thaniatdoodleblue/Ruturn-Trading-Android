package com.returntrader.view.activity;

import android.os.Bundle;
import android.view.View;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;
import com.returntrader.R;

import java.util.HashMap;
import java.util.Map;

public class DummyActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_subscribe_news:
                FirebaseMessaging.getInstance().subscribeToTopic("news");
                break;
            case R.id.btn_unsubscribe_news:
                FirebaseMessaging.getInstance().unsubscribeFromTopic("news");
                break;
            case R.id.btn_subscribe_testing:
                FirebaseMessaging.getInstance().subscribeToTopic("testing");
                break;
            case R.id.btn_unsubscribe_testing:
                FirebaseMessaging.getInstance().unsubscribeFromTopic("testing");
                break;
            case R.id.btn_send_news:
                sendNotification("news");
                break;
            case R.id.btn_send_testing:
                sendNotification("testing");
                break;
        }
    }

    private void sendNotification(String topic) {
        Bundle bundle = new Bundle();
        bundle.putString("title", "Push notification title");
        bundle.putString("desc", "Description");
        bundle.putString("link", "www.google.com");
        bundle.putString("mediaUrl", "");

        Map<String, String> bundle1 = new HashMap<>();
        bundle1.put("title", "Push notification title");
        bundle1.put("desc", "Description");
        bundle1.put("link", "www.google.com");
        bundle1.put("mediaUrl", "https://cdn.pixabay.com/photo/2015/12/13/09/42/banner-1090835_960_720.jpg");
        bundle1.put("to", topic);


        RemoteMessage.Builder builder = new RemoteMessage.Builder("Message");
        builder.setData(bundle1);
        FirebaseMessaging.getInstance().send(builder.build());

    }
}
