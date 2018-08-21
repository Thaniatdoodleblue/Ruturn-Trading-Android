package com.returntrader.common;

import android.util.Log;

import com.returntrader.model.webservice.ResponseListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RetroFitUtils {
    //Single ton object...
    private static RetroFitUtils RetroFitUtility = null;
    private final String TAG = "RetroFitUtils";

    //Single ton method...
    public static RetroFitUtils getInstance() {
        if (RetroFitUtility != null) {
            return RetroFitUtility;
        } else {
            RetroFitUtility = new RetroFitUtils();
            return RetroFitUtility;
        }
    }


    public void retrofitEnqueue(Call<ResponseBody> call, final ResponseListener resListener, final int flag) {
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Log.d(TAG, "response.code(): " + response.code());
                Log.d(TAG, "response.code(): " + response.raw().code());

                Log.d(TAG, "=" + response.raw());

                String resultRes = null;
                if (response.body() != null) {
                    resultRes = getStringFromByte(response.body().byteStream());
                    Log.d(TAG, "=" + resultRes);
                }

                switch (response.code()) {
                    case 200:
                        resListener.onSuccess(resultRes, flag);
                        break;
                    case 201:
                        resListener.onFailure(resultRes, flag);
                        break;
                    case 400:
                        if (response.errorBody() != null) {
                            resListener.showErrorDialog(getStringFromByte(response.errorBody().byteStream()), flag);
                        }
                        break;
                    case 401:
                        resListener.logOut(response.code());
                        break;
                    case 500:
                        resListener.showInternalServerErrorDialog(resultRes, flag);
                        break;
                    default:
                        resListener.logOut(response.code());
                        break;
                }

            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                resListener.onFailure(t, flag);
            }
        });
    }


    public String getStringFromByte(InputStream inputStream) {

        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();

        reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;

        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        String result = sb.toString();
        return result;
    }
}
