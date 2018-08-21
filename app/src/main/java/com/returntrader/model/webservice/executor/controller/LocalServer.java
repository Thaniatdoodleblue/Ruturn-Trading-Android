package com.returntrader.model.webservice.executor.controller;



import com.returntrader.common.UrlId;
import com.returntrader.model.webservice.APIRequestDTO;

import java.io.IOException;

/**
 * Created by Guru karthik on 26-11-2016.
 */

public class LocalServer {

    private String TAG = getClass().getSimpleName();

    public <T> T getLocalData(APIRequestDTO response) throws IOException {
        UrlId urlId = response.getContentUrl().getUrlId();
        Object contentRequest = response.getContentRequest();
        switch (urlId) {
            case SAMPLE_TASK:
                return (T) doSampleTask((Integer) contentRequest);


        }
        return null;
    }

    private String doSampleTask(Integer integer) {
        return String.valueOf(integer);
    }

}
