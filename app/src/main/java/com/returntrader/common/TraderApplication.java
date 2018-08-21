package com.returntrader.common;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDex;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.crashlytics.android.Crashlytics;
import com.facebook.FacebookSdk;
import com.returntrader.R;
import com.returntrader.database.CompanyItemInfo;
import com.returntrader.database.DaoMaster;
import com.returntrader.database.DaoSession;
import com.returntrader.helper.LocaleHelper;
import com.returntrader.utils.CryptLib;
import com.returntrader.view.widget.CropCircleTransformation;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;

import org.greenrobot.greendao.database.Database;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;

import io.branch.referral.Branch;
import io.fabric.sdk.android.Fabric;


public class
TraderApplication extends Application {


    private String TAG = getClass().getSimpleName();
    private static TraderApplication mAppController;


    public static TraderApplication getInstance() {
        if (mAppController == null) {
            mAppController = new TraderApplication();
        }
        return mAppController;
    }

    public static final boolean ENCRYPTED = true;
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        LocaleHelper.setLocale(getApplicationContext(), "en_US");
        // Initialize the Branch object
        Branch.getAutoInstance(this);
        setUpDatabaseSession();
        setUpTwitter();
        Fabric.with(this, new Crashlytics());
        FacebookSdk.sdkInitialize(getApplicationContext());
        mAppController = this;
        FontsOverride.overrideDefaultFonts(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        super.attachBaseContext(LanguageHelper.onAttach(base, LanguageHelper.getLanguage(base)));
        MultiDex.install(this);
    }

    private void setUpTwitter() {
        //Twitter.initialize(this);
        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(getString(R.string.twitter_sdk_consumer_key), getString(R.string.twitter_sdk_consumer_secrete)))
                .debug(true)
                .build();
        Twitter.initialize(config);


    }

    private void setUpDatabaseSession() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "trader-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public void loadImage(String imageUrl, ImageView target) {
        Glide.with(this)
                .load(imageUrl)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(target);
    }


    public void loadImage(String imageUrl, ImageView target, boolean isRounded) {
        Glide.with(this)
                .load(imageUrl)
                .crossFade()
                .bitmapTransform(new CropCircleTransformation(getApplicationContext()))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(target);
    }

    public void loadImage(int drawable, ImageView target) {
        Glide.with(this)
                .load(drawable)
                .crossFade()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(target);
    }

    public void loadImage(File file, ImageView target) {
        Glide.with(this)
                .load(file)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(target);
    }

    public void loadCircularImage(String url, ImageView view, View viewGroup) {
        if (viewGroup != null)
            viewGroup.setVisibility(View.VISIBLE);

        Glide.with(this).load(url).asBitmap()
                //.centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                // .placeholder(R.drawable.ic_shake_make_gift)
                .placeholder(R.drawable.ic_background_circle_shakemake_pink)
                .error(R.drawable.ic_background_circle_shakemake_pink)
                .animate(R.anim.zoom_in_center)
                .into(new BitmapImageViewTarget(view) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(getApplicationContext().getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        circularBitmapDrawable.setTargetDensity(resource.getDensity());
                        view.setImageDrawable(circularBitmapDrawable);
                    }

                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        super.onResourceReady(resource, glideAnimation);
                        if (viewGroup != null)
                            viewGroup.setVisibility(View.VISIBLE);

                    }
                });
    }


    public String getTotalTransactionTaxCost(String price, int transactionType) {
        try {
            BigDecimal amount = new BigDecimal(Constants.Common.DEFAULT_PRICE_ZERO);
            Log.d(TAG, "DEFAULT_PRICE_ZERO: " + String.valueOf(amount));
            //amount = amount.setScale(2, BigDecimal.ROUND_UNNECESSARY);
            amount = amount.add(new BigDecimal(Constants.TaxInfo.DEFAULT_TRADER_COST));
            Log.d(TAG, "DEFAULT_TRADER_COST: " + String.valueOf(amount));
            amount = amount.add(new BigDecimal(getCalculatedAmount(Constants.TaxInfo.TAX_BROKER_COMMISSION, price)));
            Log.d(TAG, "TAX_BROKER_COMMISSION: " + new BigDecimal(getCalculatedAmount(Constants.TaxInfo.TAX_BROKER_COMMISSION, price)));
            if (transactionType == Constants.TransactionType.TRANSACTION_BUY) {
                amount = amount.add(new BigDecimal(getCalculatedAmount(Constants.TaxInfo.TAX_SECURITY_TRANSFER_ADMINISTRATION, price)));
            }
            Log.d(TAG, "TAX_SECURITY_TRANSFER_ADMINISTRATION: " + new BigDecimal(getCalculatedAmount(Constants.TaxInfo.TAX_SECURITY_TRANSFER_ADMINISTRATION, price)));
            amount = amount.add(new BigDecimal(getCalculatedAmount(Constants.TaxInfo.TAX_SETTLE_MENT_ADMINISTRATION, price)));
            Log.d(TAG, "TAX_SETTLE_MENT_ADMINISTRATION: " + new BigDecimal(getCalculatedAmount(Constants.TaxInfo.TAX_SETTLE_MENT_ADMINISTRATION, price)));
            amount = amount.add(new BigDecimal(getCalculatedAmount(Constants.TaxInfo.TAX_IPL, price)));
            Log.d(TAG, "TAX_IPL: " + new BigDecimal(getCalculatedAmount(Constants.TaxInfo.TAX_IPL, price)));
            amount = amount.add(new BigDecimal(getVatCalculatedAmount(Constants.TaxInfo.TAX_VAT, String.valueOf(price)))); //price
            Log.d(TAG, "TAX_VAT: " + new BigDecimal(getVatCalculatedAmount(Constants.TaxInfo.TAX_VAT, price)));
            Log.d(TAG, "getTotalCost : " + amount);
            Log.d(TAG, "getTotalCost without round : " + String.valueOf(amount));
            return formatWithoutRoundOff(String.valueOf(amount));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0";
    }

    public String getTransactionTaxCost(String price, int transactionType) {
        Log.d(TAG, "getTransactionTaxCost: " + String.valueOf(getTotalTransactionTaxCost(price, transactionType)));
        return String.valueOf(getTotalTransactionTaxCost(price, transactionType));
    }

    public String getTotalAmount(String price, int transactionType) {
        try {
            if (TextUtils.isEmpty(price)) {
                price = Constants.Common.DEFAULT_PRICE_ZERO;
            }
            BigDecimal amount = new BigDecimal(price);
//        amount = amount.setScale(2, BigDecimal.ROUND_UNNECESSARY);
            amount = amount.add(new BigDecimal(getTotalTransactionTaxCost(price, transactionType)));
            return formatWithoutRoundOff(String.valueOf(amount));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0";
    }


    public String getTaxAmount(String percentage, String price) {
        switch (percentage) {
            case Constants.TaxInfo.TAX_VAT:
                return formatWithoutRoundOff(getVatCalculatedAmount(percentage, price));
                default:
                return formatWithoutRoundOff(getCalculatedAmount(percentage, price));
        }
    }


    public String getVatCalculatedAmount(String percentage, String price) {
        try {
            BigDecimal serviceTax = new BigDecimal(percentage);
            BigDecimal amountBrokerTaxAmount = new BigDecimal(getCalculatedAmount(Constants.TaxInfo.TAX_BROKER_COMMISSION, price));
            //amountBrokerTaxAmount = amountBrokerTaxAmount.setScale(2, BigDecimal.ROUND_UNNECESSARY);
            Log.d(TAG, "TAX_VAT TAX_BROKER_COMMISSION: " + new BigDecimal(getCalculatedAmount(Constants.TaxInfo.TAX_BROKER_COMMISSION, price)));
            BigDecimal amountIpl = new BigDecimal(getCalculatedAmount(Constants.TaxInfo.TAX_IPL, price));
            Log.d(TAG, "TAX_VAT TAX_IPL: " + new BigDecimal(getCalculatedAmount(Constants.TaxInfo.TAX_IPL, price)));
            //amountIpl = amountIpl.setScale(2, BigDecimal.ROUND_UNNECESSARY);
            BigDecimal settlementAmount = new BigDecimal(getCalculatedAmount(Constants.TaxInfo.TAX_SETTLE_MENT_ADMINISTRATION, price));
            Log.d(TAG, "TAX_VAT TAX_SETTLE_MENT_ADMINISTRATION: " + new BigDecimal(getCalculatedAmount(Constants.TaxInfo.TAX_SETTLE_MENT_ADMINISTRATION, price)));
            //settlementAmount = settlementAmount.setScale(2, BigDecimal.ROUND_UNNECESSARY);
            BigDecimal defaultTraderCost = new BigDecimal(Constants.TaxInfo.DEFAULT_TRADER_COST);
            Log.d(TAG, "TAX_VAT DEFAULT_TRADER_COST: " + defaultTraderCost);
            //defaultTraderCost = defaultTraderCost.setScale(2, BigDecimal.ROUND_UNNECESSARY);
            BigDecimal totalAddonTaxAmount;
            totalAddonTaxAmount = amountBrokerTaxAmount.add(amountIpl).add(settlementAmount).add(defaultTraderCost);
            Log.d(TAG, "TAX_VAT VAT: " + totalAddonTaxAmount);
            //totalAddonTaxAmount = totalAddonTaxAmount.setScale(2, BigDecimal.ROUND_UNNECESSARY);
            Log.d(TAG, "getVatCalculatedAmount: " + String.valueOf(serviceTax.multiply(totalAddonTaxAmount)));
            return String.valueOf(serviceTax.multiply(totalAddonTaxAmount));//.setScale(2, BigDecimal.ROUND_UNNECESSARY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0";
    }

    public String getCalculatedAmount(String percentage, String price) {
        try {
            BigDecimal amount = new BigDecimal(price);
            BigDecimal serviceTax = new BigDecimal(percentage);
            //serviceTax = serviceTax.setScale(2, BigDecimal.ROUND_UNNECESSARY);
            //amount = amount.setScale(2, BigDecimal.ROUND_UNNECESSARY);
            BigDecimal getVat = amount.multiply(serviceTax);
            BigDecimal divider = new BigDecimal(100);
            //        divider = divider.setScale(2, BigDecimal.ROUND_UNNECESSARY);

            /*hide by venkat*/
            // BigDecimal finalVat = getVat.divide(divider);

            BigDecimal finalVat = getVat;
            //finalVat = finalVat.setScale(2, BigDecimal.ROUND_UNNECESSARY);
            if (finalVat.compareTo(new BigDecimal(Constants.Common.DEFAULT_MIN_PRICE)) < 0) {
                return Constants.Common.DEFAULT_MIN_PRICE; //  For low amount add min amount to it.
            }
            Log.d("***TotalAddonTaxAmount", "" + finalVat);
//        return formatWithoutRoundOff(String.valueOf(finalVat));
            return String.valueOf(finalVat);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0";
    }

    /*
    public String getCalculatedAmount(String percentage, String price) {
        BigDecimal amount = new BigDecimal(price);
        BigDecimal serviceTax = new BigDecimal(percentage);
        //serviceTax = serviceTax.setScale(2, BigDecimal.ROUND_UNNECESSARY);
        //amount = amount.setScale(2, BigDecimal.ROUND_UNNECESSARY);
        BigDecimal getVat = amount.multiply(serviceTax);
        //BigDecimal divider = new BigDecimal(100);
        //divider = divider.setScale(2, BigDecimal.ROUND_UNNECESSARY);
        BigDecimal finalVat = getVat;//.divide(divider, BigDecimal.ROUND_UNNECESSARY);
        //finalVat = finalVat.setScale(2, BigDecimal.ROUND_UNNECESSARY);
        if (finalVat.compareTo(new BigDecimal(Constants.Common.DEFAULT_MIN_PRICE)) == -1) {
            return Constants.Common.DEFAULT_MIN_PRICE; //  For low amount add min amount to it.
        }
        return formatWithoutRoundOff(String.valueOf(finalVat));
    }
    */

    public String getAppUrl() {
        return "https://ruturntrading.co.za/";
    }

    public String getAppStoreUrl() {
        return "http://play.google.com/store/apps/details?id=" + getPackageName();
    }


    public BigDecimal getBuyAmount(String amount) {
        try {
            return new BigDecimal(amount).multiply(new BigDecimal("100").setScale(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public boolean isHigh(float value) {
        return value > 0;
    }


    public String getValue(float value) {
        return Constants.Common.DECIMAL_FORMAT.format(value);
    }

    public float getLatestPrice(CompanyItemInfo data) {
        // Log.d(TAG, "getLastPrice: " + data.getLastPrice() + " closedPrice : " + data.getClosePrice() + " data ISN" + data.getISINCode());
        if (data.getClosePrice() > 0) {
            float value = ((data.getLastPrice() - data.getClosePrice()));
            return ((value / data.getClosePrice())) * 100;
        }

        return 0.00f;
    }

    /***/
    public String getInvestedAmountBySharePrice(String numberOfShare, String sharePrice) {
        try {
            BigDecimal decimalNumberOfShare = new BigDecimal(numberOfShare);
            BigDecimal decimalSharePrice = new BigDecimal(sharePrice);
            return String.valueOf(decimalNumberOfShare.multiply(decimalSharePrice));//.setScale(2, BigDecimal.ROUND_UNNECESSARY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0";
    }

    /***/
    public String getTotalHoldingByPrice(String numberOfShare, String lastKnownDelayPrice) {
        try {
            BigDecimal decimalNumberOfShare = new BigDecimal(numberOfShare);
            BigDecimal decimalLastKnownDelayPrice = new BigDecimal(lastKnownDelayPrice);
            return String.valueOf(decimalNumberOfShare.multiply(decimalLastKnownDelayPrice));//.setScale(2, BigDecimal.ROUND_UNNECESSARY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0";
    }


    /*
     * lastKnownDelayPrice -> 15 mins delay share price
     * purchasedSharePrice -> basecost
     * */
    public float getProfitOrLoss(Float lastKnownDelayPrice, Float purchasedSharePrice) {
        return ((lastKnownDelayPrice - purchasedSharePrice) / purchasedSharePrice) * 100;
    }

    /*
     * lastKnownDelayPrice -> 15 mins delay share price
     * purchasedSharePrice -> basecost
     * noOfSharePurchased -> no of share purchased
     * */

    public float getProfitLossForShare(Float lastKnownDelayPrice, Float purchasedSharePrice, Float noOfSharePurchased) {
        return (getProfitOrLoss(lastKnownDelayPrice, purchasedSharePrice) / noOfSharePurchased);
    }


    /*
    investedAmount -> already invested amount
    lastKnownDelayPrice -> 15 mins delay share price
    noOfSharePurchased -> no of share purchased
    * */
    public float getProfitLossForValue(Float investedAmount, Float lastKnownDelayPrice, Float noOfSharePurchased) {
        return (lastKnownDelayPrice * noOfSharePurchased) - investedAmount;
    }




    public DaoSession getDaoSession() {
        return daoSession;
    }


    public boolean isInvestedHigh(String holdingAmount, String investedAmount) {
        try {
            if (TextUtils.isEmpty(holdingAmount)) {
                holdingAmount = Constants.Common.DEFAULT_PRICE_ZERO;
            }

            if (TextUtils.isEmpty(investedAmount)) {
                investedAmount = Constants.Common.DEFAULT_PRICE_ZERO;
            }
            BigDecimal holding = new BigDecimal(holdingAmount);
            BigDecimal invested = new BigDecimal(investedAmount);
            return holding.compareTo(invested) == 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /***/
    public String formatWithoutRoundOff(Object valueToConvert) {
        Float floatVal;
        if (valueToConvert instanceof String) {
            floatVal = Float.parseFloat(TextUtils.isEmpty((String) valueToConvert) ? "0.0" : ((String) valueToConvert).replace(",", "."));
        } else {
            floatVal = (float) valueToConvert;
        }
//        DecimalFormat df = new DecimalFormat("#.##");
        Float result = (float) (Math.floor(floatVal * 100) / 100);
        return formatStringTo2Decimal(String.valueOf(result));
    }


    /***/
    public String convertPriceCentToRand(Object valueToConvert) {
        Float floatVal;
        if (valueToConvert instanceof String) {
            floatVal = Float.parseFloat(TextUtils.isEmpty((String) valueToConvert) ? "0.00" : (String) valueToConvert);
        } else {
            floatVal = (float) valueToConvert;
        }
        floatVal = floatVal / 100f;
        DecimalFormat df = new DecimalFormat("#.##");
        return formatStringTo2Decimal(df.format(floatVal));
    }

    /***/
    public String formatStringTo2Decimal(String stringVal) {
        Float floatVal = Float.parseFloat(TextUtils.isEmpty(stringVal) ? "0.00" : stringVal);
        return String.format("%.2f", floatVal);
    }

    public Float convertPriceCentToRand(Float price) {
        if (price == null) {
            return Float.valueOf(Constants.Common.DEFAULT_PRICE_ZERO);
        }
        return (price / 100);
    }

    public String getApiUrl() {
        // return  getString(R.string.api_url_dev);
        return getString(R.string.api_url_live);
    }

    /*public Double convertPriceCentToRand(Double price) {
        BigDecimal finalPrice = new BigDecimal(Constants.Common.DEFAULT_PRICE_ZERO);
        BigDecimal priceDecimal = new BigDecimal(price);
        finalPrice = priceDecimal.divide(new BigDecimal("100"), BigDecimal.ROUND_HALF_UP);
        return finalPrice.doubleValue();
    }*/


    /*public void setDelayAlarm() {
        Intent intent = new Intent(this, DelayPriceUpdaterReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), Constants.RequestCodes.REPEAT_ALARM_SCHEDULE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pendingIntent);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), Constants.Common.REFRESH_TIME_DELAY, pendingIntent);
    }*/


    /***/
    @Nullable
    public String compose(String doEncrypt) {
        try {
//            String key = getString(R.string.default_id);
            String key = "c789092e88f5fbbd5f9c7d51899a91a357a08e43cbeaf49993b7";
            Log.d(TAG, "compose: key" + key);
            // String iv = getString(R.string.default_subid);
            String iv = "a4e4f0746dd1354b8256c13b71d9b0c2166889b8e7bf7fd210230409b0982f1d4afd3fdc51bf2d8074d0a991acaac933be13e8e5af610260647656cff6e975dcd3e590836ca36bfb3fc3d5b84ef0231ad6f75b8efbd5c4c7c35861f9fd1734b11da77207d042d6707136941a1acde4572e514ff1efdb5d8e8b3a940bc431d3fd";
            Log.d(TAG, "compose: iv" + iv);
            CryptLib cryptLib = new CryptLib();

            return cryptLib.encryptSimple(doEncrypt, key, iv);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /***/
    @Nullable
    public String decompose(String encryptedString) {
        try {
            String key = getString(R.string.default_id);
            String iv = getString(R.string.default_subid);
            CryptLib cryptLib = new CryptLib();
            return cryptLib.decryptSimple(encryptedString, key, iv);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
