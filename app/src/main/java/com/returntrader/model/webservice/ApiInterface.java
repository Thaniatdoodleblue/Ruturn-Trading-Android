package com.returntrader.model.webservice;


import com.returntrader.common.Constants;
import com.returntrader.database.CompanyItemInfo;
import com.returntrader.model.common.ConfigData;
import com.returntrader.model.common.ShakeMakeGroupData;
import com.returntrader.model.dto.request.AddressRequest;
import com.returntrader.model.dto.request.BankDetailRequest;
import com.returntrader.model.dto.request.BaseRequest;
import com.returntrader.model.dto.request.BuyRequest;
import com.returntrader.model.dto.request.ContactSyncRequest;
import com.returntrader.model.dto.request.EditProfileRequest;
import com.returntrader.model.dto.request.EmailVerificationRequest;
import com.returntrader.model.dto.request.FavouriteStatusChangeRequest;
import com.returntrader.model.dto.request.FcmTokenRequest;
import com.returntrader.model.dto.request.ForgotPinRequest;
import com.returntrader.model.dto.request.FullRegistrationRequest;
import com.returntrader.model.dto.request.HistoryRequest;
import com.returntrader.model.dto.request.LoginRequest;
import com.returntrader.model.dto.request.OtpRequest;
import com.returntrader.model.dto.request.ProfileUpdateRequest;
import com.returntrader.model.dto.request.ReportProblemRequest;
import com.returntrader.model.dto.request.SearchHomeRequest;
import com.returntrader.model.dto.request.SettingPreferenceRequest;
import com.returntrader.model.dto.request.UpdatePasscodeRequest;
import com.returntrader.model.dto.request.UpdateSettingsNotifyRequest;
import com.returntrader.model.dto.request.WithDrawalRequest;
import com.returntrader.model.dto.response.BalanceResponse;
import com.returntrader.model.dto.response.BankVerifyResponse;
import com.returntrader.model.dto.response.BaseResponse;
import com.returntrader.model.dto.response.BuyResponse;
import com.returntrader.model.dto.response.CheckAddressStatusResponse;
import com.returntrader.model.dto.response.ContactSyncResponse;
import com.returntrader.model.dto.response.DayHistoryPriceResponse;
import com.returntrader.model.dto.response.DelayPriceResponse;
import com.returntrader.model.dto.response.FavouriteResponse;
import com.returntrader.model.dto.response.FicaDocumentUploadResponse;
import com.returntrader.model.dto.response.ForgotPasswordResponse;
import com.returntrader.model.dto.response.FullRegisterResponse;
import com.returntrader.model.dto.response.HistoryResponse;
import com.returntrader.model.dto.response.LoginResponse;
import com.returntrader.model.dto.response.NewsResponse;
import com.returntrader.model.dto.response.OtpResponse;
import com.returntrader.model.dto.response.ProfilePicUpdateResponse;
import com.returntrader.model.dto.response.ReportProblemResponse;
import com.returntrader.model.dto.response.SellResponse;
import com.returntrader.model.dto.response.ShakeMakeCompanyResponse;
import com.returntrader.model.dto.response.ShakeMakeGroupResponse;
import com.returntrader.model.dto.response.ShakeMakeMoneyResponse;
import com.returntrader.model.dto.response.StockHoldResponse;
import com.returntrader.model.dto.response.UpdatePasscodeResponse;
import com.returntrader.model.dto.response.WithDrawBalanceResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;


public interface ApiInterface {
    @POST("Dashboard_API/sms_otp")
    Call<OtpResponse> submitPhoneNumber(@Body OtpRequest request);

    @POST("Dashboard_API/phone_number_verify")
    Call<OtpResponse> verifyPhoneNumber(@Body OtpRequest request);

    @POST("Dashboard_API/send_email_otp")
    Call<OtpResponse> submitEmail(@Body EmailVerificationRequest request);

    @POST("Dashboard_API/otp_verify")
    Call<OtpResponse> verifyPhoneNumberWithOTP(@Body OtpRequest request);

    @POST("Dashboard_API/rsa_verify")
    Call<OtpResponse> verifyRSA(@Body EmailVerificationRequest request);

    @POST("Dashboard_API/email_otp_verify")
    Call<OtpResponse> verifyEmail(@Body EmailVerificationRequest request);

    @POST("Dashboard_API/instrument")
    Call<List<CompanyItemInfo>> callInstrument(@Body SearchHomeRequest request);

    @POST("Dashboard_API/instrument_search")
    Call<List<CompanyItemInfo>> callInstrumentSearch(@Body SearchHomeRequest request);

    @POST("Dashboard_API/top_instrument")
    Call<List<CompanyItemInfo>> callInstrumentTop40(@Body SearchHomeRequest request);

    @POST("Dashboard_API/favourite_list")
    Call<List<CompanyItemInfo>> getFavouriteList(@Body SearchHomeRequest request);

    @Headers(
            {"Accept: application/json, text/xml",
                    "Content-Type: application/x-www-form-urlencoded",
                    "X-AYLIEN-NewsAPI-Application-ID:" + Constants.Common.NEWS_API_APP_ID,
                    "X-AYLIEN-NewsAPI-Application-Key:" + Constants.Common.NEWS_API_APP_KEY
            }
    )
    @GET()
    Call<NewsResponse> callNewsData(@Url String url);

    @GET("articles")
    Call<NewsResponse> callNewsApi(@Query("source") String source, @Query("sortBy") String sortBy, @Query("apiKey") String apiKey);


    @POST("Dashboard_API/favourite")
    Call<FavouriteResponse> setFavouriteStatus(@Body FavouriteStatusChangeRequest favouriteStatusChangeRequest);


    @POST("Dashboard_API/instrument_price")
    Call<HistoryResponse> getHistoryData(@Body HistoryRequest historyRequest);


    @POST("Dashboard_API/invite_user")
    Call<ContactSyncResponse> getContactSync(@Body ContactSyncRequest syncRequest);


    @POST("Dashboard_API/balance")
    Call<BalanceResponse> getBalance(@Body BaseRequest syncRequest);


    @POST("Dashboard_API/holding")
    Call<StockHoldResponse> getHolding(@Body BaseRequest syncRequest);


    @POST("Dashboard_API/static_data")
    Call<ConfigData> getConfigData();


    @POST("Dashboard_API/buy")
    Call<BuyResponse> doBuyStock(@Body BuyRequest buyRequest);


    @POST("Dashboard_API/sell")
    Call<SellResponse> doSellStock(@Body BuyRequest buyRequest);

    @POST("Dashboard_API/delay_price_api")
    Call<DelayPriceResponse> getDelayPriceList(@Body BaseRequest baseRequest);

    @POST("Dashboard_API/per_day_price_api")
    Call<DayHistoryPriceResponse> getDayHistoryPrice(@Body HistoryRequest historyRequest);

    @POST("Dashboard_API/per_day_price_home_api")
    Call<DayHistoryPriceResponse> getDayHistoryPriceAtMarketTime();

    @POST("Dashboard_API/user_register")
    Call<FullRegisterResponse> doFullRegistration(@Body FullRegistrationRequest fullRegistrationRequest);

    @POST("Dashboard_API/registered_email_otp")
    Call<ForgotPasswordResponse> sendForgotPasswordRequestForValidUser(@Body ForgotPinRequest request);

    @POST("Dashboard_API/email_otp")
    Call<ForgotPasswordResponse> sendForgotPasswordRequestForPartialUser(@Body ForgotPinRequest request);

    @POST("Dashboard_API/email_verify")
    Call<ForgotPasswordResponse> checkForgotPinOtp(@Body ForgotPinRequest request);

    @Multipart()
    @POST("Dashboard_API/FicaDocuments_greencard")
    Call<FicaDocumentUploadResponse> submitGreenCard(@Part("user_id") RequestBody userId, @Part MultipartBody.Part frontFile, @Part MultipartBody.Part backFile);

    @Multipart()
    @POST("Dashboard_API/FicaDocuments_photo")
    Call<FicaDocumentUploadResponse> submitIdCard(@Part("user_id") RequestBody userId, @Part MultipartBody.Part frontFile);

    @Multipart()
    @POST("Dashboard_API/FicaDocuments_address")
    Call<FicaDocumentUploadResponse> submitBankStatement(@Part("user_id") RequestBody userId, @Part MultipartBody.Part frontFile);

    @POST("Dashboard_API/user_login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @POST("Dashboard_API/login_otp")
    Call<LoginResponse> checkUserLogin(@Body LoginRequest request);

    @POST("Dashboard_API/passcode_verify")
    Call<LoginResponse> checkUserPinLogin(@Body LoginRequest request);

    @POST("Dashboard_API/fcm")
    Call<BaseResponse> updateFcmToken(@Body FcmTokenRequest request);

    @POST("Dashboard_API/update_setting")
    Call<BaseResponse> updatePreference(@Body SettingPreferenceRequest request);

    @POST("Dashboard_API/help_line")
    Call<ReportProblemResponse> reportProblem(@Body ReportProblemRequest request);

    @POST("Dashboard_API/helpline_list")
    Call<ReportProblemResponse> getReports(@Body BaseRequest request);

    @POST("Dashboard_API/address")
    Call<CheckAddressStatusResponse> checkAddressStatus(@Body BaseRequest request);

    @POST("Dashboard_API/verify_fica")
    Call<CheckAddressStatusResponse> checkFicaStatus(@Body BaseRequest request);

    @POST("Dashboard_API/trust_account")
    Call<CheckAddressStatusResponse> checkTrustedAccountStatus(@Body BaseRequest request);

    @POST("Dashboard_API/repost_address")
    Call<BaseResponse> updateAddress(@Body AddressRequest request);

    @POST("Dashboard_API/bank")
    Call<BaseResponse> addBankDetails(@Body BankDetailRequest request);

    @POST("Dashboard_API/user_update")
    Call<LoginResponse> editProfile(@Body EditProfileRequest request);

    @POST("Dashboard_API/edit_request")
    Call<BaseResponse> requestProfileUpdate(@Body ProfileUpdateRequest request);

    @POST("Dashboard_API/withdrawals")
    Call<BaseResponse> withDrawals(@Body WithDrawalRequest request);

    @Multipart
    @POST("Dashboard_API/profile_picture")
    Call<ProfilePicUpdateResponse> updateProfilePicture(@Part("user_id") RequestBody user_id,
                                                        @Part MultipartBody.Part profile);

    @POST("Dashboard_API/update_notification_status")
    Call<BaseResponse> updateSettingsNotify(@Body UpdateSettingsNotifyRequest request);

    @POST("Dashboard_API/update_passcode")
    Call<UpdatePasscodeResponse> updatePasscode(@Body UpdatePasscodeRequest request);

    @POST("Dashboard_API/bank_verify")
    Call<List<BankVerifyResponse>> verifyBank(@Body BaseRequest request);

    @GET("ShakenMake_API/snmGroups")
    Call<ShakeMakeGroupResponse> getShakeMakeGroup();

    @GET("ShakenMake_API/snmAmount")
    Call<ShakeMakeMoneyResponse> getShakeMakeMoney();

    @POST("ShakenMake_API/snmCompany")
    Call<ShakeMakeCompanyResponse> getShakeMakeCompany(@Body ShakeMakeGroupData shakeMakeGroupData);

    @POST("Dashboard_API/withdrawals_report")
    Call<WithDrawBalanceResponse> withDrawBalance(@Body BaseRequest request);
}
