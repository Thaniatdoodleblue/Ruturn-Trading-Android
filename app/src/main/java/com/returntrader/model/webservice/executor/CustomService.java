package com.returntrader.model.webservice.executor;

import com.returntrader.model.dto.response.TwitterFollowerList;
import com.returntrader.model.dto.response.TwitterSendMessageResponse;
import com.twitter.sdk.android.core.models.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

// example users/show service endpoint
public interface CustomService {


    @GET("/1.1/users/show.json")
    Call<User> show(@Query("user_id") long id);


    @POST("/1.1/direct_messages/new.json")
    Call<TwitterSendMessageResponse> sendMessage(@Query("user_id") long id, @Query("text") String text);


    @GET("/1.1/followers/list.json")
    Call<TwitterFollowerList> getFollowerList(@Query("user_id") long id);
}