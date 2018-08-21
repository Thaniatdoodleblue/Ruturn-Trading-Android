package com.returntrader.common;

import com.returntrader.database.CompanyItemInfo;
import com.returntrader.model.dto.response.BankVerifyResponse;
import com.returntrader.model.webservice.executor.IGeneralResponseListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RetroFitBase {
    //Single ton object...
    private static RetroFitBase RetroFitUtility = null;
    private final String TAG = "RetroFitUtils";

    //Single ton method...
    public static RetroFitBase getInstance() {
        if (RetroFitUtility != null) {
            return RetroFitUtility;
        } else {
            RetroFitUtility = new RetroFitBase();
            return RetroFitUtility;
        }
    }


    /***/
    public void retrofitEnqueue(Call<List<CompanyItemInfo>> call, final IGeneralResponseListener resListener, final int flag) {

        call.enqueue(new Callback<List<CompanyItemInfo>>() {
            @Override
            public void onResponse(Call<List<CompanyItemInfo>> call, Response<List<CompanyItemInfo>> response) {

                switch (response.code()) {
                    case 200:
                        resListener.onSuccess(response.body(), flag);
                        break;
                    case 201:
                        resListener.onFailure(response.message(), flag);
                        break;
                    case 202:
                        resListener.onFailure(response.message(), flag);
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
                        resListener.showInternalServerErrorDialog(response.message(), flag);
                        break;
                    default:
                        resListener.logOut(response.code());
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<CompanyItemInfo>> call, Throwable t) {
                resListener.onFailure(t, flag);
            }
        });
    }


    /***/
    public void retrofitEnqueueVerifyBank(Call<List<BankVerifyResponse>> call, final IGeneralResponseListener resListener, final int flag) {

        call.enqueue(new Callback<List<BankVerifyResponse>>() {
            @Override
            public void onResponse(Call<List<BankVerifyResponse>> call, Response<List<BankVerifyResponse>> response) {

                switch (response.code()) {
                    case 200:
                        resListener.onSuccess(response.body(), flag);
                        break;
                    case 201:
                        resListener.onFailure(response.message(), flag);
                        break;
                    case 202:
                        resListener.onFailure(response.message(), flag);
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
                        resListener.showInternalServerErrorDialog(response.message(), flag);
                        break;
                    default:
                        resListener.logOut(response.code());
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<BankVerifyResponse>> call, Throwable t) {
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
