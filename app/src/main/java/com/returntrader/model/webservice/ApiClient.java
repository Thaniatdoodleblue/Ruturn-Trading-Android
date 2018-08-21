package com.returntrader.model.webservice;

import com.github.aurae.retrofit2.LoganSquareConverterFactory;
import com.returntrader.common.TraderApplication;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;


public class ApiClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            // set your desired log level
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.readTimeout(60, TimeUnit.SECONDS);
            httpClient.connectTimeout(60, TimeUnit.SECONDS);
            // addItem your other interceptors …
            // addItem logging as last interceptor
            httpClient.addInterceptor(logging);  // <-- this is the important line!

            retrofit = new Retrofit.Builder()
//                    .baseUrl("http://192.168.10.183/Returntrader/index.php/")
                    .baseUrl(TraderApplication.getInstance().getApiUrl())
                    .client(httpClient.build())
                    .addConverterFactory(LoganSquareConverterFactory.create()) /*Use can mention your desired parser over here.!*/
                    .build();
        }
        return retrofit;
    }
}
