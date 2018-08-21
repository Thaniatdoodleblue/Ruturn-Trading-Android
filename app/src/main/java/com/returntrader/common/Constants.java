package com.returntrader.common;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Guru karthik on 05-12-2016
 */

public interface Constants {

    interface Common {

        long REFRESH_TIME_DELAY = 300000L;
        long SPLASH_TIME_OUT = 3500;
        String NEWS_API_APP_ID = "d3745607";
        String NEWS_API_APP_KEY = "2c9981b927bd4602bf84b2155cdf8937";
        String DEFAULT_PRICE_ZERO = "0.00";
        String DEFAULT_MIN_PRICE = "0.01";
        String DEFAULT_MIN_PRICE_BUY = "150.00";
        DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#0.00");
        SimpleDateFormat DATE_FORMAT_NEWS_ACTUALUTC = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat DATE_FORMAT_NEWS_ACTUAL = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
        SimpleDateFormat DATE_FORMAT_NEWS_DATE = new SimpleDateFormat("EEE, MMM d yyyy", Locale.getDefault());
        SimpleDateFormat DATE_FORMAT_NEWS_TIME = new SimpleDateFormat("hh:mm aaa", Locale.getDefault());
        String DEFAULT_CASH_BALANCE_KEY = "EasyEquities";
        String DEFAULT_COUNTRY_ID = "710";
        String DEFAULT_COUNTRY_CODE = "+27";
        // String DEFAULT_CASH_BALANCE_KEY = "ZarReal";
        int MAX_RSA_ID_LENGTH = 13;
        int MAX_POSTAL_CODE_LENGTH = 4;
        int MAX_BRANCH_CODE_LENGTH = 6;
        int MAX_ACCOUNTNUMBER_LENGTH = 13;
        SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        /*2017-12-28 11:55:57*/
        SimpleDateFormat DATE_FORMATTER_REPORTS_ACTUAL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        SimpleDateFormat DATE_FORMATTER_REPORTS_EXPECTED = new SimpleDateFormat("yyyy-MM-dd hh:mm aaa", Locale.getDefault());
        SimpleDateFormat DATE_FORMATTER_REPORTS_EXPECTED_DDMMMYYYY = new SimpleDateFormat("dd-MMM-yyyy hh:mm aaa", Locale.getDefault());
        int FCM_DEVICE_TYPE = 1;
        String SMS_ORIGIN = "Ruturn Trading";
        String OTP_DELIMITER = ":";
        String DEFAULT_COUNTRY = "South Africa";
        String DEFAULT_ISO_COUNTRY_CODE = "ZA"; // for south africa
        String ENABLED_COMPANIES_STATUS = "Suspended";
        String NA = "NA";
    }

    interface InternalHttpCode {
        int SUCCESS_WITH_CONTENT = 200;
        int SUCCESS_WITH_ERROR = 201;
        int FAILURE_CODE = 0;
    }

    interface HttpErrorMessage {
        String INTERNAL_SERVER_ERROR = "Our server is under maintenance. We will reslove shortly!";
        String FORBIDDEN = "Seems like you haven't permitted to do this operation!";
        String SERVER_EXECUTION_ERROR = "Our execution partner, or the Exchange itself is currently experiencing technical difficulties. " +
                "Please bear with us while we resolve this as soon as possible.";

    }

    interface FileName {
        String BANK_LIST_FILE_NAME = "bank_list.json";
        String CONFIG_DATA_LIST_FILE_NAME = "config_data.json";
        String QUESTIONS_DATA = "question_data.json";
    }


    interface TransactionType {
        int TRANSACTION_BUY = 0;
        int TRANSACTION_SELL = 1;
        int RESPONSE_SUCCESS = 1;
        int RESPONSE_FAILURE = 2;
    }

    interface NetworkType {
        String WIFI = "Wi-Fi";
        String MOBILE = "Mobile";
    }

    interface BundleKey {
        String BROADCAST_MESSAGE = "broadcastAction";
        String BUNDLE_COMPANY_ITEM = "company";
        String BUNDLE_COMPANY_ITEM_ID = "Id";
        String BUNDLE_COMPANY_ITEM_POSITION = "companyItemPosition";
        String BUNDLE_SHARE_AMOUNT = "amount";
        String BUNDLE_TRANSACTION_COST_FROM = "transactionCostFor";
        String BUNDLE_WEB_VIEW_TITLE = "title";
        String BUNDLE_WEB_VIEW_URL = "url";
        String BUNDLE_BANNER_ITEM = "bannerItem";
        String BUNDLE_CONTENT_TYPE = "contentType";
        String BUNDLE_PIN = "pin";
        String BUNDLE_ENTRY_TYPE = "entryType";
        String BUNDLE_STOCK_ITEM = "stockItem";
        String BUNDLE_OTP_SAMPLE = "otp";
        String BUNDLE_OTP_VERIFY_FROM = "otpVerifyFrom";
        String BUNDLE_COMPANY_FROM = "companyDetailFrom";
        String BUNDLE_USER_INFO = "userInfo";
        String BUNDLE_EMAIL_ID = "email";
        String BUNDLE_REGISTER_REQUEST = "registerRequest";
        String BUNDLE_AUTHENTICATION_FROM = "authenticationFrom";
        String BUNDLE_LOGIN_REQUEST = "loginRequest";
        String BUNDLE_FINGER_PRINT_NAVIGATION_FROM = "fingerPrintNavigationFrom";
        String BUNDLE_QUESTIONS_ITEM = "questionItem";
        String BUNDLE_PROGRESS_STATUS = "progressStatus";
        String BUNDLE_EDIT_REQUEST = "editRequest";
        String BUNDLE_VIEW_REPORTS = "ViewReports";
        String BUNDLE_OTPLOGIN = "OTPLogin";
        String BUNDLE_RSA = "RSA";
        String BUNDLE_IDCARDSTATUS = "IDCARDSTATUS";
        String BUNDLE_BANKSTATUS = "BANKSTATUS";
        String BUNDLE_VERIFICATIONSTATUS = "VERIFICATIONSTATUS";
        String BUNDLE_IMAGEPATH = "IMAGEVIEWER_PATH";
        String BUNDLE_TOOLBAR_TITLE = "TOOLBAR_TITLE";
        String BUNDLE_SHAKE_MAKE_COMPANY_DATA = "BUNDLE_SHAKE_MAKE_COMPANY_DATA";
        String BUNDLE_SHAKE_MAKE_PAYMENT_STATUS = "PAYMENT_STATUS";
    }


    interface AccountSync {
        String BUNDLE_SYNC_TYPE = "syncType";
        int SYNC_BALANCE = 1;
        int SYNC_HOLDING = 2;
        int CHECK_ADDRESS_STATUS = 3;
        int CHECK_FICA_STATUS = 4;
        int CHECK_TRUSTED_ACCOUNT_STATUS = 5;
    }

    interface OtpVerifyNavigation {
        int FROM_LOGIN = 1;
        int FROM_REGISTRATION = 2;
    }


    interface FingerPrintNavigation {
        int FROM_LOGIN = 1;
        int FROM_REGISTRATION = 2;
    }

    interface FicaContentType {
        int CONTENT_GREEN_CARD_ID = 0;
        int CONTENT_ID_CARD = 1;
        int CONTENT_BANK_STATEMENT = 2;


        int FRONT_IMAGE = 0;
        int BACK_IMAGE = 1;

        int FICA_NOT_UPLOAD_COMPLETE = 0;
        int FICA_UPLOAD_COMPLETE = 1;
        int FICA_DOC_VERIFIED = 2;

    }

    interface GraphFilter {
        int FILTER_TYPE_TODAY = 0; // today
        int FILTER_TYPE_LAST_WEEK = 1; // this refer last 30 days of reacords
        int FILTER_TYPE_LAST_MONTH = 2; // this refer last 30 days of reacords
        int FILTER_TYPE_ONE_YEAR = 3;
        int FILTER_TYPE_THREE_YEARS = 4;
    }

    interface TaxInfo {
        String DEFAULT_TRADER_COST = "5.00";
        String TAX_TRADER_COST = "0.00";
        String TAX_BROKER_COMMISSION = "0.005";//"0.50"
        String TAX_SECURITY_TRANSFER_ADMINISTRATION = "0.0025"; //"0.25"
        String TAX_SETTLE_MENT_ADMINISTRATION = "0.00075"; //"0.075"
        String TAX_IPL = "0.000002";
        String TAX_VAT = "0.15";//"0.14"
    }


    interface LoadCompanyList {
        int LOAD_DAY = 0;
        int LOAD_TOP_FORTY = 1;
        int LOAD_FAVOURITE = 2;
        int LOAD_SEARCH = 3;
        int LOAD_A_Z = 4;
        int LOAD_REIT = 5;
        int LOAD_BIG_BRANDS = 6;
        int LOAD_ALTX = 7;
        int LOAD_MOST_POPULAR = 8;
        int LOAD_LAST_KNOWN_LIST = 9;

    }

    interface RequestCodes {
        int KEY_REQUEST_CODE_COMPANY_DETAILS = 101;
        int KEY_REQUEST_OPEN_CONTACTS = 102;
        int REQUEST_INVITE = 103;
        int NAVIGATE_BASIC_INFO_TO_OTP_VERIFICATION = 104;
        int NAVIGATE_EMAIL_TO_VERIFY_EMAIL = 105;
        int NAVIGATE_FICA_TO_UPLOAD = 106;

        int NAVIGATE_CREATE_PIN_TO_CONFIRM_PIN = 107;


        int PICK_GALLERY_IMAGE_REQUEST_CODE = 200;
        int PICK_CAMERA_IMAGE_REQUEST_CODE = 201;

        int COMPANY_DETAILS_BUY_TO_AUTHENTICATE = 202;

        int NAVIGATE_TO_BUY = 203;
        int NAVIGATE_TO_SELL = 204;

        int NAVIGATE_REGISTRATION_PHASE_TWO = 205;

        int NAVIGATE_REGISTRATION_PHASE_ONE = 206;
        int NAVIGATE_FORGOT_PIN_TO_VERIFY_PIN = 207;
        int PLACE_AUTOCOMPLETE_REQUEST_CODE = 208;
        int NAVIGATE_REGISTRATION_PHASE_THREE = 209;
        int NAVIGATE_EDIT_PROFILE_PHASE_TWO = 210;
        int NAVIGATE_TO_ADDRESS = 211;
        int WITHDRAWMONEY_AUTHREQUEST = 212;
        int NAVIGATE_TO_ADDBANKDETAILS = 213;

        int TASKID_DOFULLREGISTRATION = 214;
        int TASKID_DOUPDATEPASSCODE = 215;
        int TASKID_VERIFYBANK = 216;
        int TASKID_LOGIN = 217;
        int TASKID_PINVERIFY = 218;

        int SHAKE_MAKE_SHAKE_AGAIN_REFRESH = 289;
    }

    interface Settings {

        //General
        int ADD_MONEY = 0;
        int PRIVACY_POLICY = 1;
        int WITHDRAW_MONEY = 2;
        int FICA = 3;
        int SARS = 4;
        int LANGUAGE = 5;
        int REPORT_PROBLEM = 6;
        int HELP_QA = 7;
        int TERMS_CONDITIONS = 8;
        int CHANGE_PASSWORD = 9;

        // Social

        int INVITE_EMAIL_FRIENDS = 10;
        int INVITE_TWITTER_FRIENDS = 11;
        int INVITE_PHONE_CONTACTS = 12;
        int INVITE_FACEBOOK_FRIENDS = 13;
        int SEND_MONEY = 14;
        int SEND_SHARES = 15;

        //settings

        int SOUND = 16;
        int FRIEND = 17;
        int SOCIAL = 18;
        int BREAKING_NEWS = 19;
        int COMPANY_NOTIFICATION = 20;
        int JSE_NOTIFICATION = 21;
        int NAVIGATE_TO_TWITTER_FOLLOWER = 22;
        int LOGOUT = 23;


        int EDIT_PROFILE = 24;
        int MESSAGES = 25;
        int UPDATE_PROFILEPICTURE = 26;

        int REPORT_PROBLEMS = 27;
        int VIEW_REPORTS = 28;
        int UPDATESETTINGS_NOTIFY = 29;
    }

    interface BroadCastKey {
        String ACTION_DELAY_PRICE_UPDATE_COMPLETED = "com.ruturntrader.ACTION.DELAY_PRICE_UPDATED";
        String ACTION_BALANCE_UPDATE_COMPLETED = "com.ruturntrader.ACTION.BALANCE_UPDATED";
        String ACTION_HOLDING_UPDATEINFO = "com.ruturntrader.ACTION_HOLDING_UPDATEINFO";
    }

    interface SharedPrefKey {
        String IS_INITIAL_DATA_SYNC_DONE = "isInitialDataSyncDone";
        String IS_BANK_DETAILS_COMPLETED = "isBankDetailsCompleted";
        String IS_BALANCE_UPDATED = "isBalanceUpdated";
        String IS_PARTIAL_REGISTER_COMPLETED = "isPartialRegisterCompleted";
        String IS_ACCOUNT_VERIFIED = "isAccountVerified";
        String IS_CONFIG_DATA_UPDATED = "isConfigurationDataUpdated";
        String IS_FICA_DETAILS_COMPLETED = "isFicaCompleted";
        String IS_FICA_DOC_VERIFIED = "isFicaDocsVerified";
        String IS_FICA_VERIFIED_POP_UP_SHOWN = "isFicaVerifiedStatusPopUpShown";
        String IS_ADDRESS_COMPLETED = "isAddressCompleted";
        String IS_DESPOIT_COMPLETED = "isDepositCompleted";
        String IS_FINGER_PRINT_ENABLED = "isFingerPrintEnabled";
        String USER_ID = "userId";
        String TEMP_USER_INFO = "tempUserInfo";
        String USER_INFO = "userInfo";
        String FICA_DOC_STATUS = "ficaDocStatus";
        String CONFIG_DATA = "configData";
        String TEMP_FULL_REGISTER_USER = "tempFullRegistrationUser";
        String PIN = "pinNunber";
        String LAST_KNOWN_BALANCE = "lastKnownBalance";
        String LAST_KNOWN_INVESTED_AMOUNT = "lastKnownInvestedAmount";
        String LAST_KNOWN_HOLDING_AMOUNT = "lastKnownHoldingAmount";
        String LAST_KNOWN_SHARES = "lasKnownShares";
        String IS_EGG_AUTHENTICATION_REQUIRED = "isEggAuthenticationRequired";
        String IS_MARKET_AVAILABLE = "isMarketAvailable";
        String TRUST_ACCOUNT_TYPE = "trustAccountType";
        String EFT_REFERENCE_NUMBER = "eftReferenceNumber";
        String IS_JSE_NOTIFICATION_ENABLED = "isJseEnabled";
        String IS_COMPANY_NOTIFICATION_ENABLED = "isCompanyNewsEnabled";
        String IS_BREAKING_NEWS_NOTIFICATION_ENABLED = "isBreakingNewsEnabled";
        String UPDATED_PROFILEPIC_URL = "UpdatedProfilePicUrl";
        String CAN_SHOW_FINGERPRINT = "FingerPrintScreen";
        String HOLDING_DATA = "HoldingData";
        String PASSCODE_DATA = "PassCode";

        String IS_JSE_NOTIFY_ENABLED = "isJseEnabled";
        String IS_COMPANY_NOTIFY_ENABLED = "isCompanyNewsEnabled";
        String IS_BREAKING_NEWS_NOTIFY_ENABLED = "isBreakingNewsEnabled";
        String SHAKE_MAKE_GROUP_DATA = "shakeMakeGroupData";
        String SHAKE_MAKE_MONEY_DATA = "shakeMakMoneyData";
    }

    interface AuthenticationType {
        int LOAD_NEW_PIN_ENTRY = 0;
        int LOAD_CONFIRM_PIN_ENTRY = 1;
        int LOAD_FINGER_PRINT = 2;
        int LOAD_NEXT_PAGE = 3;
        int LOAD_AUTHENTICATE_PIN = 4;
        int LOAD_AUTHENTICATE_SUCCESS = 5;
        int LOAD_LOGINAUTH_PIN = 6;


        int FROM_FORGOT_PIN = 6;
        int FROM_REGISTRATION = 7;
        int FROM_LOGIN = 8;
        int FROM_CHANGE_PIN_VERIFICATION = 9;
        int FROM_CHANGE_REQUEST = 10;
        int FROM_EDIT_PROFILE = 11;

    }

    public interface BannerType {
        int ADD_DEPOSIT = 0;
        int FAQ = 1;
        int BANK_DETAILS = 2;
        int FICA = 3;
        int FULL_REGISTRATION = 4;
        int ADDRESS = 5;
        int SHAKE_MAKE = 6;
    }

    public interface SpinnerType {
        int TYPE_PHONE_NUMBER_COUNTRY_CODE = 0;
        int TYPE_RACE = 1;
        int TYPE_CITY_OF_BIRTH = 2;
        int TYPE_NATIONALITY = 3;
        int TYPE_COUNTRY_OF_RESIDENCE = 4;
        int TYPE_PROVINCE = 5;
        int TYPE_INCOME = 6;
        int TYPE_MARITAL_STATUS = 7;
    }


    interface OtpModelTaskId {
        int QUEUE_ID_SUBMIT_PHONE_NUMBER = 0;
        int QUEUE_ID_SUBMIT_EMAIL = 1;
        int QUEUE_ID_CHECK_PHONE_VERIFIED = 2;
        int QUEUE_ID_CHECK_EMAIL_VERIFIED = 3;
        int QUEUE_ID_CHECK_RSA_VERIFIED = 4;
        int QUEUE_ID_VERIFY_PHONE_NUMBER = 5;
    }

    interface SyncServiceTaskId {
        int SYNC_BALANCE = 201;
        int SYNC_CHECK_FICA_STATUS = 202;
        int SYNC_CHECK_TRUSTED_ACCOUNT_STATUS = 203;
        int SYNC_CHECK_ADDRESS_STATUS = 204;
    }

    interface PushNotificationTopicName {
        String TOPIC_BREAKING_NEWS = "androidBreakingNews";
        String TOPIC_JSE_NEWS = "androidJseNews";
        String TOPIC_COMPANY_NOTIFICATION = "androidCompanyNotification";
    }

    interface QuestionFragmentType {
        int FRAGMENT_QUESTION = 1;
        int FRAGMENT_ANSWER = 2;
    }

    public interface MediaPlayer {
        int CREATE = 101;
        int START = 102;
        int STOP = 103;
        int RESET = 104;
        int DESTROY = 105;
    }
}
