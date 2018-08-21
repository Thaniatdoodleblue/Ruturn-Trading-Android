package com.returntrader.sync;

import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.returntrader.common.Constants;
import com.returntrader.common.TraderApplication;
import com.returntrader.database.CompanyItemInfo;
import com.returntrader.database.DatabaseManager;
import com.returntrader.helper.AppConfigurationManager;
import com.returntrader.library.CustomException;
import com.returntrader.model.common.DayHistoryDo;
import com.returntrader.model.common.DelayPrice;
import com.returntrader.model.dto.request.BaseRequest;
import com.returntrader.model.dto.response.DayHistoryPriceResponse;
import com.returntrader.model.dto.response.DelayPriceResponse;
import com.returntrader.model.listener.IBaseModelListener;
import com.returntrader.model.webservice.DayHistoryPriceModel;
import com.returntrader.model.webservice.GetDelayPriceModel;
import com.returntrader.utils.CodeSnippet;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class DelayPriceSyncService extends IntentService {

    private String TAG = getClass().getSimpleName();
    private CodeSnippet mCodeSnippet;
    private GetDelayPriceModel mGetDelayPriceModel;
    private DatabaseManager mDatabaseManger;
    private DayHistoryPriceModel mDayHistoryPriceModel;
    private AppConfigurationManager mAppConfigurationManager;

    public DelayPriceSyncService() {
        super("DelayPriceSyncService");
    }


    /***/
    private void getDelayPrice() {
        if (mCodeSnippet.hasNetworkConnection()) {
            mGetDelayPriceModel.getDelayPriceList(0, new BaseRequest());
        }
    }

    /***/
    private void getHistoryPrices() {
        Log.d(TAG, "isMarketAvailable() " + isMarketAvailable());
        if (mCodeSnippet.hasNetworkConnection() /*&& isMarketAvailable()*/) {
            mDayHistoryPriceModel.getDayHistoryPriceForMarketTime();
        }
    }

    /***/
    private void setMarketAvailability(boolean isAvailable) {
        mAppConfigurationManager.setMarketAvailability(isAvailable);
    }

    /***/
    private boolean isMarketAvailable() {
        return mAppConfigurationManager.isMarketAvailable();
    }

    /***/
    private void setTrustedAccountType(String accountType) {
        if (!(TextUtils.isEmpty(accountType))) {
            mAppConfigurationManager.setTrustAccountType(accountType);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        this.mCodeSnippet = new CodeSnippet(this);
        this.mAppConfigurationManager = new AppConfigurationManager(this);
        this.mDatabaseManger = new DatabaseManager(this);
        this.mGetDelayPriceModel = new GetDelayPriceModel(delayPriceResponseListener);
        this.mDayHistoryPriceModel = new DayHistoryPriceModel(dayHistoryPriceResponseListener);
    }


    /***/
    private IBaseModelListener<DayHistoryPriceResponse> dayHistoryPriceResponseListener = new IBaseModelListener<DayHistoryPriceResponse>() {
        @Override
        public void onSuccessfulApi(long taskId, DayHistoryPriceResponse response) {
            if (response != null) {
                if (response.getDayHistoryList() != null) {
                    updateDayPrice(response.getDayHistoryList());
                }
            }
        }

        @Override
        public void onFailureApi(long taskId, CustomException e) {
            stopSelf();
        }
    };


    /***/
    private void updateDayPrice(List<DayHistoryDo> responseList) {
        try {
            while (responseList != null && responseList.size() > 0) {
                DayHistoryDo dayHistoryDo = responseList.get(0);
                if (dayHistoryDo != null) {
                    List<DayHistoryDo> filterList = Stream.of(responseList).
                            filter(x -> x.getIsinCode()
                                    .equalsIgnoreCase(dayHistoryDo.getIsinCode()))
                            .collect(Collectors.toList());
                    if (filterList != null) {
                        List<Float> floats = Stream.of(filterList)
                                .map(DayHistoryDo::getLast)
                                .collect(Collectors.toList());
                        if (floats == null) {
                            floats = new ArrayList<>();
                        }
                        CompanyItemInfo info = mDatabaseManger.getCompanyItem(dayHistoryDo.getIsinCode());
                        if (info != null) {
                            String data = info.getStringGraphList(floats);
                            if (!(TextUtils.isEmpty(data))) {
                                info.setGraphData(data);
                                mDatabaseManger.updateCompanyInfo(info);
                            }
                        }
                    }

                    responseList = Stream.of(responseList).
                            filterNot(x -> x.getIsinCode().equalsIgnoreCase(dayHistoryDo.getIsinCode()))
                            .collect(Collectors.toList());
                }
            }
            sendBroadCastComplete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        stopSelf();
    }


    /***/
    private void syncCompanyInfoWithDB(DelayPriceResponse response) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                getHistoryPrices();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    if (response != null) {
                        if (response.getDelayPrices() != null) {
                            setMarketAvailability(response.getMarketAvailability() == 1);
                            setTrustedAccountType(response.getAccountType());

                            List<CompanyItemInfo> companyList = mDatabaseManger.loadAll();
                            List<DelayPrice> delayPrices = response.getDelayPrices();

                            if (companyList != null) {
                                if (delayPrices != null) {
                                    Stream.of(delayPrices).forEach(delayPrice -> {

                                        Log.d(TAG, "doInBackground: " +(new CodeSnippet(getApplicationContext()).getJsonStringFromObject(delayPrice)));


                                        CompanyItemInfo info = null;
                                        try {
                                            info = Stream.of(companyList)
                                                    .filter(companyItem -> companyItem.getISINCode().equalsIgnoreCase(delayPrice.getIsinCode()))
                                                    .single();
                                        } catch (NoSuchElementException | IllegalStateException exception) {
                                            exception.printStackTrace();
                                        }

                                        if (info != null) {
                                            info.setClosePrice(TraderApplication.getInstance().convertPriceCentToRand(delayPrice.getClosePrice()));
                                            info.setBid(TraderApplication.getInstance().convertPriceCentToRand(delayPrice.getBid()));
                                            info.setLastPrice(TraderApplication.getInstance().convertPriceCentToRand(delayPrice.getLastPrice()));
                                            info.setPriceStatus(delayPrice.getCompanyItemStatus());
                                            //  Log.d(TAG,"getCompanyItemStatus : "  + delayPrice.getCompanyItemStatus());
                                            info.setCompanyAvailabilityStatus(delayPrice.getCompanyItemStatus());

                                            float lastKnownDelayPrice = TraderApplication.getInstance().getLatestPrice(info);
                                            info.setLastKnownDelayPrice(lastKnownDelayPrice);
                                            String maxTradeAmount = TextUtils.isEmpty(delayPrice.getMaxTradeAmount()) ? "0.0" : mCodeSnippet.doReplaceComma(delayPrice.getMaxTradeAmount());
                                            float maxTradeAmnt = Float.valueOf(maxTradeAmount) / 100f;
                                            Log.d("maxTradeAmount STR" + maxTradeAmount, "maxTradeAmount FF" + maxTradeAmnt);
                                            info.setMaxTradeAmount("" + maxTradeAmnt);
                                            mDatabaseManger.updateCompanyInfo(info);
                                        }
                                    });
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }


    /***/
    private IBaseModelListener<DelayPriceResponse> delayPriceResponseListener = new IBaseModelListener<DelayPriceResponse>() {
        @Override
        public void onSuccessfulApi(long taskId, DelayPriceResponse response) {
            syncCompanyInfoWithDB(response);
        }

        @Override
        public void onFailureApi(long taskId, CustomException e) {
            stopSelf();
        }
    };


    /***/
    private void sendBroadCastComplete() {
        Log.d(TAG, "sendBroadCastComplete");
        Intent broadCastIntent = new Intent();
        broadCastIntent.setAction(Constants.BroadCastKey.ACTION_DELAY_PRICE_UPDATE_COMPLETED);
        broadCastIntent.addCategory(Intent.CATEGORY_DEFAULT);
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadCastIntent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(TAG, "onHandleIntent");
        getDelayPrice();
    }
}