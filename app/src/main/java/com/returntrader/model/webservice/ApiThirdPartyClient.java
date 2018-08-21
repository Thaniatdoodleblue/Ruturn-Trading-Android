package com.returntrader.model.webservice;

import com.github.aurae.retrofit2.LoganSquareConverterFactory;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * Created by Karthikeyan on 18-07-2017
 */

public class ApiThirdPartyClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            // set your desired log level
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            // addItem your other interceptors …
            // addItem logging as last interceptor
            httpClient.addInterceptor(logging);  // <-- this is the important line!
/*https://api.newsapi.aylien.com/api/v1/stories?text=%22MTN%22+OR+%22MTN+Group%22&published_at.start=NOW-60DAYS%2FDAY&published_at.end=NOW&sort_by=recency*/
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://newsapi.org/v1/")
                    .client(httpClient.build())
                    .addConverterFactory(LoganSquareConverterFactory.create()) /*Use can mention your desired parser over here.!*/
                    .build();
        }
        return retrofit;
    }
}
