package com.returntrader.fcm;


import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.returntrader.helper.AppConfigurationManager;
import com.returntrader.library.Log;
import com.returntrader.utils.CodeSnippet;


public class InstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    private AppConfigurationManager mAppConfigurationManager;
    private CodeSnippet mCodeSnippet;


    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        sendRegistrationToServer(refreshedToken);
    }
    // [END refresh_token]

    /**
     * Persist token to third-party servers.
     * <p>
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        mAppConfigurationManager = new AppConfigurationManager(this);
        mCodeSnippet = new CodeSnippet(this);
        mCodeSnippet.setAllDefaultTopic();

        /*if (TextUtils.isEmpty(mAppConfigurationManager.getUserId())) {
            return;
        }*/

      /*  Log.d(TAG, "sendRegistrationToServer");
        FcmTokenRequest fcmTokenRequest = new FcmTokenRequest(mAppConfigurationManager.getUserId());
        fcmTokenRequest.setToken(token);
        fcmTokenRequest.setDeviceId(mCodeSnippet.getDeviceId());
        fcmTokenRequest.setDeviceType(Constants.Common.FCM_DEVICE_TYPE);
        FcmTokenModel fcmTokenModel = new FcmTokenModel();
        if (mCodeSnippet.hasNetworkConnection()) {
            fcmTokenModel.registerFcmToken(fcmTokenRequest);
        }
*/
    }
}