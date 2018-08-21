package com.returntrader.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.res.ResourcesCompat;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.format.Time;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.bluelinelabs.logansquare.LoganSquare;
import com.bluelinelabs.logansquare.NoSuchMapperException;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.returntrader.common.Constants;
import com.returntrader.common.ExceptionTracker;
import com.returntrader.common.TraderApplication;
import com.returntrader.model.common.ConfigData;
import com.returntrader.model.common.QuestionItem;
import com.returntrader.model.common.QuestionSet;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;


public class CodeSnippet {
    //Time AM or PM
    private String PM = "PM";
    private String AM = "AM";
    private String TAG = getClass().getSimpleName();
    private Context mContext;

    public CodeSnippet(Context mContext) {
        this.mContext = mContext;
    }

    public static String formatTime(String inputDate) {
        if (inputDate != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss.SSS'Z'");
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("HH:mm", Locale.getDefault());
            String formatDate = null;
            try {
                Date d = simpleDateFormat.parse(inputDate);
                formatDate = simpleDateFormat2.format(d);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return formatDate;
        }
        return null;
    }

    public static String formatTimeDate(String inputDate) {
        if (inputDate != null) {
//            2017-05-18T16:00:00.000Z
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
            String formatDate = null;
            try {
                Date d = simpleDateFormat.parse(inputDate);
                formatDate = simpleDateFormat2.format(d);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return formatDate;
        }
        return null;
    }

    public static Bitmap rotateImage(Bitmap bitmap, int rotate) {
        Bitmap rotateBitmap;
        if (rotate > 0) {
            Matrix matrix = new Matrix();
            matrix.postRotate(rotate);
            rotateBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        } else {
            rotateBitmap = bitmap;
        }
        return rotateBitmap;
    }

    @SuppressLint("NewApi")
    public static String getRealPathFromURI_API19(Context context, Uri uri) throws Exception {
        String filePath = "";
        Cursor cursor;
        int columnIndex;
        String[] column = {MediaStore.Images.Media.DATA};

        try {
            String wholeID = DocumentsContract.getDocumentId(uri);
            // Split at colon, use second item in the array
            String id = wholeID.split(":")[1];
            // where id is equal to
            String sel = MediaStore.Images.Media._ID + "=?";

            cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    column, sel, new String[]{id}, null);

        } catch (IllegalArgumentException e) {
            e.printStackTrace();

            String[] projection = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(uri, projection, null, null, null);
        }

        columnIndex = cursor.getColumnIndex(column[0]);
        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return filePath;
    }

    @SuppressLint("NewApi")
    public static String getRealPathFromURI_API11to18(Context context, Uri contentUri) throws Exception {
        String[] proj = {MediaStore.Images.Media.DATA};
        String result = null;

        CursorLoader cursorLoader = new CursorLoader(
                context,
                contentUri, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        if (cursor != null) {
            int column_index =
                    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            result = cursor.getString(column_index);
        }
        return result;
    }

    public static String getRealPathFromURI_BelowAPI11(Context context, Uri contentUri) throws Exception {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        int column_index
                = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private static void hideSoftKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManager =
                    (InputMethodManager) activity.getSystemService(
                            Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setupUI(View view, final Activity activity) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(activity);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView, activity);
            }
        }
    }

    public boolean hasNetworkConnection() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) { // connected to the internet
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi
                return true;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                // connected to the mobile provider's data plan
                return true;
            }
        }
        return false;
    }

    public Bitmap getCompressedImage(Bitmap imageBitmap) {


        return null;
    }

    public File getFileFromBitmap(Context mContext, Bitmap bitmap) throws IOException {
        File f = new File(mContext.getCacheDir(), "" + System.currentTimeMillis() + "_" + "profile_pic.png");


        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        bitmap= compressBitmap(bitmap, 300);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100 /*ignored for PNG*/, bos);
        byte[] bitmapdata = bos.toByteArray();
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(bitmapdata);
        fos.flush();
        fos.close();
        return f;
    }

    public boolean isTodayLieInBetween(String str1, String str2) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String todayStr = formatter.format(Calendar.getInstance().getTime());
            Date todayDate = formatter.parse(todayStr);
            Date date1 = formatter.parse(str1);
            Date date2 = formatter.parse(str2);

            return date1.compareTo(todayDate) <= 0 && date2.compareTo(todayDate) >= 0;
        } catch (Exception e) {
            ExceptionTracker.track(e);
        }
        return false;
    }

    public Calendar getCalendarTime(String time) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(formatter.parse(time));
            return calendar;
        } catch (Exception e) {
            ExceptionTracker.track(e);
        }
        return null;
    }

    public Calendar getCalendarTimeForHasty(String time) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS'Z'", Locale.getDefault());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(formatter.parse(time));
            return calendar;
        } catch (Exception e) {
            ExceptionTracker.track(e);
        }
        return null;
    }

    public String getCalendarTime(Calendar time) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
            return formatter.format(time);
        } catch (Exception e) {
            ExceptionTracker.track(e);
        }
        return null;
    }

    public Calendar getCalendarForYear(String time) {
        try {

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy", Locale.getDefault());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(formatter.parse(time));
            return calendar;
        } catch (Exception e) {
            ExceptionTracker.track(e);
        }
        return null;
    }

    // changed date format for hastyparking
    public Calendar getCalendarToStandard(String time) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(formatter.parse(time));
            return calendar;
        } catch (Exception e) {
            ExceptionTracker.track(e);
        }
        return null;
    }


    public Calendar getCalendarToStandard(String date, SimpleDateFormat simpleDateFormat, TimeZone timeZone) {
        try {
            Calendar calendar = Calendar.getInstance(Locale.getDefault());
            calendar.setTime(simpleDateFormat.parse(date));
            return calendar;
        } catch (Exception e) {
            ExceptionTracker.track(e);
        }
        return null;
    }


    public Calendar getCalendarWithTimeOnly(String time) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("hh:mm aa", Locale.getDefault());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(formatter.parse(time));
            return calendar;
        } catch (Exception e) {
            ExceptionTracker.track(e);
            SimpleDateFormat formatter = new SimpleDateFormat("hh : mm aa", Locale.getDefault());
            Calendar calendar = Calendar.getInstance();
            try {
                calendar.setTime(formatter.parse(time));
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            return calendar;
        }
    }

    public String getDayOfMonthMonthAndYear(Calendar calendar) {
        //TODO mention
        String dateString = "";
        dateString = calendar.get(Calendar.DAY_OF_MONTH) + " " + monthNameFromInt(calendar.get(Calendar.MONTH)) + ", " + calendar.get(Calendar.YEAR);
        return dateString;
    }

    public String getDayOfMonthMonthAndYearStd(Calendar calendar) {
        String dateString = "";
        int month = calendar.get(Calendar.MONTH) + 1;
        DecimalFormat formatter = new DecimalFormat("00");
        dateString = formatter.format(calendar.get(Calendar.DAY_OF_MONTH)) + "-" + formatter.format(month) + "-" + calendar.get(Calendar.YEAR);
        return dateString;
    }

    private String monthNameFromInt(int monthInt) {
        String month = "";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (monthInt >= 0 && monthInt <= 11) {
            month = months[monthInt];
        }
        return month.substring(0, 4);
    }

    public String getPastTimerString(Calendar calendar) {
        long time = System.currentTimeMillis() - calendar.getTimeInMillis();
        long mins = time / 60000;
        if (mins > 59L) {
            long hours = mins / 60;
            if (hours > 24) {
                long days = hours / 24;
                if (days > 1) {
                    return days + " days";
                } else {
                    return days + " day";
                }
            } else {
                return hours + " hours ago";
            }
        } else {
            return "less than a minute";
        }
    }

    public Date getTimeFromMilisecond(long timeStamp) {
        return new Date((long) timeStamp * 1000);
    }

//    public boolean checkPlayServices(Context context, OnGooglePlayServiceListener googlePlayServiceListener) {
//        int status = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context);
//        if (status != ConnectionResult.SUCCESS) {
//            if (GoogleApiAvailability.getInstance().isUserResolvableError(status)) {
//                showGooglePlayDialog(context, googlePlayServiceListener);
//            } else {
//                googlePlayServiceListener.onCancelServiceInstallation();
//            }
//            return false;
//        }
//        return true;
//    }

    public String getOrdinaryTime(Time time) {
        if (time.hour > 12) {
            return formatTime(time.hour - 12) + ":" + formatTime(time.minute)
                    + " " + getAMorPM(time);
        }

        return (time.hour == 0 ? String.valueOf(12) : formatTime(time.hour)) + ":"
                + formatTime(time.minute) + " " + getAMorPM(time);
    }

    public String getOrdinaryDate(Calendar calendar) {

        int month = calendar.get(Calendar.MONTH) + 1;
        DecimalFormat formatter = new DecimalFormat("00");
        Log.d(TAG, "getOrdinaryDate : " + formatter.format(month));
        return formatter.format(calendar.get(Calendar.DAY_OF_MONTH)) + "/" + formatter.format(month) + "/" + calendar.get(Calendar.YEAR);
    }

    public String getOrdinaryTime(Calendar calendar) {

        int min = calendar.get(Calendar.MINUTE);
        //Log.d(TAG,"hours :"+calendar.get(Calendar.HOUR_OF_DAY));
        String meridian = "AM";
        if (calendar.get(Calendar.HOUR_OF_DAY) > 11) {
            meridian = "PM";
        }
        DecimalFormat formatter = new DecimalFormat("00");
        return formatter.format(calendar.get(Calendar.HOUR)) + ":" + formatter.format(min) + " " + meridian;
    }

    public String getOrdinaryDateWithFipe(Time date) {

        return date.monthDay + " | " + date.month + " | " + date.year;
    }

    private String formatTime(int time) {
        if (String.valueOf(time).length() < 2)
            return "0" + time;
        else
            return String.valueOf(time);
    }

    private String getAMorPM(Time time) {
        if (time.hour > 11) {
            return PM;
        } else
            return AM;
    }

    private void showGooglePlayDialog(final Context context, final OnGooglePlayServiceListener googlePlayServiceListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Get Google Play Service");
        builder.setMessage("This app won't run without Google Play Services, which are missing from your phone");
        builder.setPositiveButton("Get Google Play Service",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        googlePlayServiceListener.onInstallingService();
                        context.startActivity(new Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://play.google.com/store/apps/details?"
                                        + "id=com.google.android.gms")));
                        dialog.dismiss();
                    }
                });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                googlePlayServiceListener.onCancelServiceInstallation();
            }
        });
        builder.setCancelable(false);
        AlertDialog alert = builder.create();
        alert.show();
    }

    private Intent getSettingsIntent(String settings) {
        return new Intent(settings);
    }

    private void startActivityBySettings(Context context, String settings) {
        context.startActivity(getSettingsIntent(settings));
    }

    private void startActivityBySettings(Context context, Intent intent) {
        context.startActivity(intent);
    }

    public void showGpsSettings(Context context) {
        startActivityBySettings(context, Settings.ACTION_LOCATION_SOURCE_SETTINGS);
    }

    public void showNetworkSettings() {
        Intent chooserIntent = Intent.createChooser(getSettingsIntent(Settings.ACTION_DATA_ROAMING_SETTINGS),
                "Complete action using");
        List<Intent> networkIntents = new ArrayList<>();
        networkIntents.add(getSettingsIntent(Settings.ACTION_WIFI_SETTINGS));
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, networkIntents.toArray(new Parcelable[]{}));
        startActivityBySettings(mContext, chooserIntent);
    }

    public boolean isSpecifiedDelay(long exisingTime, long specifiedDelay) {
        return specifiedDelay >= (Calendar.getInstance().getTimeInMillis() - exisingTime);
    }

    public void hideKeyboard(Activity activity) {
        try {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (null != activity.getCurrentFocus())
                imm.hideSoftInputFromWindow(activity.getCurrentFocus().getApplicationWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showKeyboard(Activity activity) {
        try {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (null != activity.getCurrentFocus())
                imm.showSoftInputFromInputMethod(activity.getCurrentFocus().getApplicationWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isNull(Object object) {
        return null == object || object.toString().compareTo("null") == 0;
    }

    public final boolean isValidEmail(CharSequence target) {
        if (target == null || target.length() == 0) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public boolean isAboveMarshmallow() {
        int currentapiVersion = Build.VERSION.SDK_INT;
        // Do something for marshmallow and above versions
// do something for phones running an SDK before marshmallow
        return currentapiVersion >= Build.VERSION_CODES.M;
    }


    public boolean isAboveLollipop() {
        int currentapiVersion = Build.VERSION.SDK_INT;
        // Do something for marshmallow and above versions
// do something for phones running an SDK before marshmallow
        return currentapiVersion >= Build.VERSION_CODES.LOLLIPOP;
    }

    /**
     * Fetch the drawable object for the given resource id.
     *
     * @param resourceId to which the value is to be fetched.
     * @return drawable object for the given resource id.
     */

    public Drawable getDrawable(int resourceId) {
        return ResourcesCompat.getDrawable(mContext.getResources(), resourceId, null);
    }

    /**
     * Returns the current date.
     *
     * @return Current date
     */

    public Date getCurrentDate() {
        return new Date(System.currentTimeMillis());
    }

    public static Date getDateTime(String utcTime) {
        try {
            SimpleDateFormat customFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            // customFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            String dateFormat = new SimpleDateFormat("dd yyyy MMM HH:mm", Locale.getDefault()).format(customFormat.parse(utcTime));
            android.util.Log.d("DateFormat", dateFormat);
            DateFormat df = new SimpleDateFormat("dd yyyy MMM HH:mm", Locale.getDefault());
            return df.parse(dateFormat);
        } catch (Exception e) {
            ExceptionTracker.track(e);
        }
        return null;
    }

    /**
     * Fetch the string value from a xml file returns the value.
     *
     * @param resId to which the value has to be fetched.
     * @return String value of the given resource id.
     */

    public String getString(int resId) {
        return mContext.getResources().getString(resId);
    }

    /**
     * Fetch the color value from a xml file returns the value.
     *
     * @param colorId to which the value has to be fetched.
     * @return Integer value of the given resource id.
     */

    public int getColor(int colorId) {
        return ContextCompat.getColor(mContext, colorId);
    }

    public String convertTimeStampForHasty(String Date, String Time) {

        String ConvertedValue = Date + "T" + Time + "Z";

        return ConvertedValue;
    }


    public boolean getStatus(int value) {
        return value == 1;
    }

    public String getDateFromRsaId(String dateOfBirthFormat) {
        String year = dateOfBirthFormat.substring(0, 2);
        String month = dateOfBirthFormat.substring(2, 4);
        String day = dateOfBirthFormat.substring(4, 6);
        if (TextUtils.isDigitsOnly(year)) {
            int convertedYear = Integer.parseInt(year);
            if (convertedYear < 30) {
                year = "20" + year;
            } else {
                year = "19" + year;
            }
        }
        return year + "/" + month + "/" + day;
    }

    public boolean isGreaterZero(String text) {
        if (TextUtils.isEmpty(text)) {
            return false;
        }
        BigDecimal amount = new BigDecimal(text);
        return amount.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean isGreaterThanBalance(String lastKnownBalance, String amountPurchased) {
        if (TextUtils.isEmpty(amountPurchased)) {
            return false;
        }
        BigDecimal lastKnownBal = new BigDecimal(lastKnownBalance);
        BigDecimal amount = new BigDecimal(amountPurchased);
        Log.d(TAG, "isGreaterThanBalance" + amount.compareTo(lastKnownBal));
        return lastKnownBal.compareTo(amount) > 0;
    }


    private interface OnGooglePlayServiceListener {
        void onInstallingService();

        void onCancelServiceInstallation();
    }

    private Calendar get30BeforeDateFromCurrent() {
        Date today = new Date();
        Calendar cal = new GregorianCalendar();
        cal.setTime(today);
        cal.add(Calendar.DAY_OF_MONTH, -30);
        return cal;
    }


    public Pair<Date, Date> getMonthFirstLastDate() {
        Date start, end;
        {
            Calendar calendar = get30BeforeDateFromCurrent();
            setTimeToBeginningOfDay(calendar);
            start = calendar.getTime();
        }
        {
            Calendar calendar = getCalendarForNow();
            setTimeToEndOfDay(calendar);
            end = calendar.getTime();
        }
        return Pair.create(start, end);
    }

    public Pair<Date, Date> getThreeYearFirstLastDate() {
        Date start, end;
        {
            Calendar calendar = getCalendarForNow();
            calendar.add(Calendar.YEAR, -3);
            setTimeToBeginningOfDay(calendar);
            start = calendar.getTime();
        }
        {
            Calendar calendar = getCalendarForNow();
            setTimeToEndOfDay(calendar);
            end = calendar.getTime();
        }
        return Pair.create(start, end);
    }


    public Pair<Date, Date> getLastYearFirstLastDate() {
        Date start, end;
        {
            Calendar calendar = getCalendarForNow();
            calendar.add(Calendar.YEAR, -1);
            setTimeToBeginningOfDay(calendar);
            start = calendar.getTime();
        }
        {
            Calendar calendar = getCalendarForNow();
            setTimeToEndOfDay(calendar);
            end = calendar.getTime();
        }
        return Pair.create(start, end);
    }

    private Calendar getCalendarForNow() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return calendar;
    }

    private void setTimeToBeginningOfDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    private void setTimeToEndOfDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
    }

    public long getCurrentWeekLastDay() {
        Calendar cal = Calendar.getInstance();
        Calendar first = (Calendar) cal.clone();
        first.add(Calendar.DAY_OF_WEEK, first.getFirstDayOfWeek() - first.get(Calendar.DAY_OF_WEEK));
        setTimeToBeginningOfDay(cal);
        Calendar last = (Calendar) first.clone();
        last.add(Calendar.DAY_OF_YEAR, 6);

        // print the result
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Log.d(TAG, "current week last date : " + df.format(first.getTime()));
        return first.getTimeInMillis();
    }

    public long getCurrentWeekFirstDay() {
        Calendar cal = Calendar.getInstance();
        Calendar first = (Calendar) cal.clone();
        first.add(Calendar.DAY_OF_WEEK, first.getFirstDayOfWeek() - first.get(Calendar.DAY_OF_WEEK));
        Calendar last = (Calendar) first.clone();
        last.add(Calendar.DAY_OF_YEAR, -6);
        setTimeToEndOfDay(cal);
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Log.d(TAG, "current week first date : " + df.format(last.getTime()));
        // print the result
        return last.getTimeInMillis();
    }


    public String getFormattedNewsDate(String dateString, SimpleDateFormat simpleDateFormat, SimpleDateFormat expectedFormat) {
        Date newDate = null;
        if (TextUtils.isEmpty(dateString)) {
            return null;
        }
        try {
            newDate = simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return expectedFormat.format(newDate);
    }

    /***/
    public String formatStringTo2Decimal(float floatVal) {
        return String.format("%.2f", floatVal);
    }

//    /***/
//    public String formatStringTo2DecimalWithoutRound(String stringVal) {
//        Float floatVal = Float.parseFloat(TextUtils.isEmpty(stringVal) ? "0.00" : stringVal);
//        DecimalFormat df = new DecimalFormat("#.##");
//        df.setRoundingMode(RoundingMode.FLOOR);
//        return df.format(floatVal);
//    }

    /***/
    public String formatStringTo2Decimal(String stringVal) {
        Float floatVal = Float.parseFloat(TextUtils.isEmpty(stringVal) ? "0.00" : doReplaceComma(stringVal));
        return String.format("%.2f", floatVal);
    }

    /***/
    public String getDateFromMilliSeconds(long milliSeconds, SimpleDateFormat formatter) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }


    public <T> T getObjectFromJsonStringRetro(String jsonString, Class<T> classType) {
        try {
            return LoganSquare.parse(jsonString, classType);
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }





    public InputFilter getNumberFilter() {
        return (source, start, end, dest, dstart, dend) -> {
            String filtered = "";
            for (int i = start; i < end; i++) {
                char character = source.charAt(i);
                if (!Character.isWhitespace(character) && Character.isLetter(character)) {
                    filtered += character;
                }
            }
            return filtered;
        };
    }

    public <T> T getObjectFromJson(String json, Class<T> classType) {
        try {
            Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
            return gson.fromJson(json, classType);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        return null;
    }


    public <T> String getJsonStringFromObject(T object, Class<T> classType) {
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
        //Log.i("Gson String", ""+gson.toJson(object,classType));
        return gson.toJson(object, classType);
        /*try {
            return LoganSquare.serialize(object);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        //   return null;
    }

    public <T> String getJsonStringFromObject(T obj) {
        try {
            return LoganSquare.serialize(obj);
        } catch (IOException | NullPointerException | NoSuchMapperException e) {
            e.printStackTrace();
        }
        return null;
    }


    public <T> List<T> getListFromJsonString(String jsonString, Class<T> classType) {
        try {
            return LoganSquare.parseList(jsonString, classType);
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String loadJSONFromAsset(String fileName) {
        String json = null;
        try {
            InputStream is = mContext.getAssets().open("source/" + fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    public String getContactName(final String phoneNumber) {
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
        String[] projection = new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME};
        String contactName = "";
        Cursor cursor = TraderApplication.getInstance().getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                contactName = cursor.getString(0);
            }
            cursor.close();
        }
        return contactName;
    }


    public void CopyTextIntoClipboard(String text) {
        int sdk = Build.VERSION.SDK_INT;
        if (sdk < Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) TraderApplication.getInstance().getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) TraderApplication.getInstance().getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
            clipboard.setPrimaryClip(clip);
        }
    }


    public String getDecimalPoint(float value) {
        return Constants.Common.DECIMAL_FORMAT.format(value);
    }

    /***/
    public String formatStringTo2DecimalWithoutRound(Object valueToConvert) {
        Float floatVal;
        if (valueToConvert instanceof String) {
            floatVal = Float.parseFloat(TextUtils.isEmpty((String) valueToConvert) ? "0.00" : doReplaceComma(((String) valueToConvert)));
        } else {
            floatVal = (float) valueToConvert;
        }
        DecimalFormat df = new DecimalFormat("#.##");
//        df.setRoundingMode(RoundingMode.HALF_EVEN);
        return formatStringTo2Decimal(df.format(floatVal));
    }

    /***/
    public String getDecimalPointWithMinus(float value) {
        String valMinus = Constants.Common.DECIMAL_FORMAT.format(value);
        if (valMinus.contains("-")) {
            valMinus = "(" + valMinus.replace("-", "") + ")";
        }
        return valMinus;
    }

    /***/
    public String getDecimalPointWithMinusSell(float value) {
        String valMinus = Constants.Common.DECIMAL_FORMAT.format(value);
        if (valMinus.contains("-")) {
            //valMinus = "(" + valMinus.replace("-", "") + ")";
            valMinus = "- (" + valMinus.replace("-", "") + ")";
        }
        return valMinus;
    }

    /***/
    public String getDecimalPointWithMinusOrNotWithR(float value) {
        String valMinus = Constants.Common.DECIMAL_FORMAT.format(value);
        if (valMinus.contains("-")) {
            valMinus = "R (" + valMinus.replace("-", "") + ")";
        } else {
            valMinus = "R " + valMinus;
        }
        return valMinus;
    }


    public String getDeviceId() {
        return Settings.Secure.getString(TraderApplication.getInstance().getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public float getDecimalValue(String value) {
        if (TextUtils.isEmpty(value)) {
            return 0.00F;
        }
        return Float.parseFloat(doReplaceComma(value));
    }


    public String getDecimalValueFromString(float value) {

        String valMinus = Constants.Common.DECIMAL_FORMAT.format(value);
        return valMinus;
    }




    /***/
    public String doReplaceComma(String replaceString) {
        return replaceString.replace(",", ".");
    }

    public String getDecimalPoint(String value) {
        if (TextUtils.isEmpty(value)) {
            return Constants.Common.DEFAULT_PRICE_ZERO;
        }
        return Constants.Common.DECIMAL_FORMAT.format(getDecimalValue(value));
    }


    public String getDecimalPointWithOneRoundOff(String value){
        if (value == null) {
            return Constants.Common.DEFAULT_PRICE_ZERO;
        }
        BigDecimal a = new BigDecimal(value);
        BigDecimal roundOff = a.setScale(1, BigDecimal.ROUND_HALF_EVEN);
       return getDecimalPoint(roundOff);
    }

    public String getDecimalPoint(int value) {
        return Constants.Common.DECIMAL_FORMAT.format(value);
    }

    public String getDecimalPoint(BigDecimal value) {
        if (value == null) {
            return Constants.Common.DEFAULT_PRICE_ZERO;
        }
        return Constants.Common.DECIMAL_FORMAT.format(value);
    }


    public String getDayText(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE");
        return simpleDateFormat.format(date);
    }

    public String getDayTimeText(Date date) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+02:00"));
        TimeZone tz = cal.getTimeZone();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        sdf.setTimeZone(tz);
        String localTime = sdf.format(new Date(date.getTime() * 1000));
        // I assume your timestamp is in seconds and you're converting to milliseconds?
        return localTime;
    }

    public String getNumberDayText(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM");
        return simpleDateFormat.format(date);
    }

    public String getYearText(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        return simpleDateFormat.format(date);
    }

    public String getMonthText(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM yy");
        return simpleDateFormat.format(date);
    }


    /***/
    public ConfigData getConfigData(boolean isDataSyncDone, String configData) {
        if (isDataSyncDone) {
            return loadConfigurationDataFromPreference(configData);
        } else {
            return loadConfigurationDataFromAsset();
        }
    }


    private ConfigData loadConfigurationDataFromAsset() {
        String configDataString = loadJSONFromAsset(Constants.FileName.CONFIG_DATA_LIST_FILE_NAME);
        if (TextUtils.isEmpty(configDataString)) {
            return null;
        }
        return getObjectFromJson(configDataString, ConfigData.class);
    }

    public QuestionItem getRandomQuestion() {
        String questionString = loadJSONFromAsset(Constants.FileName.QUESTIONS_DATA);
        //Log.d(TAG,"questionString : " + questionString);
        if (TextUtils.isEmpty(questionString)) {
            return null;
        }
        QuestionSet questionSet = getObjectFromJson(questionString, QuestionSet.class);
        if (questionSet != null) {
            Log.d(TAG, "questionSet.getQuestionItems() " + questionSet.getQuestionList());

            if (questionSet.getQuestionList() != null) {
                Random random = new Random();
                int result = random.nextInt(9 - 0 + 1);
                return questionSet.getQuestionList().get(result);
            }
        }
        return null;
    }

    private ConfigData loadConfigurationDataFromPreference(String configDataString) {
        if (TextUtils.isEmpty(configDataString)) {
            return null;
        }
        return getObjectFromJson(configDataString, ConfigData.class);
    }

    public void changeNotificationPreference(boolean isSubscribed, String fcmTopic) {
        if (isSubscribed) {
            FirebaseMessaging.getInstance().subscribeToTopic(fcmTopic);
        } else {
            FirebaseMessaging.getInstance().unsubscribeFromTopic(fcmTopic);
        }
    }


    public void setAllDefaultTopic() {
        changeNotificationPreference(true, Constants.PushNotificationTopicName.TOPIC_BREAKING_NEWS);
        changeNotificationPreference(true, Constants.PushNotificationTopicName.TOPIC_JSE_NEWS);
        changeNotificationPreference(true, Constants.PushNotificationTopicName.TOPIC_COMPANY_NOTIFICATION);
    }

    public boolean isAmountLesser(String amount) {
        if (TextUtils.isEmpty(amount)) {
            return true;
        }
        BigDecimal bigDecimal = new BigDecimal(amount);
        Log.d("BigDecimal.compareTo", "" + bigDecimal.compareTo(new BigDecimal(Constants.Common.DEFAULT_MIN_PRICE_BUY)));
        return bigDecimal.compareTo(new BigDecimal(Constants.Common.DEFAULT_MIN_PRICE_BUY)) == -1;
    }

    /***/
    public boolean isGreaterThanMaxTrade(String amount, float maxTrade) {
        BigDecimal bigDecimal = new BigDecimal(TextUtils.isEmpty(amount) ? "0.0" : doReplaceComma(amount));
        Log.d("BigDecimal.compareTo", "" + bigDecimal.compareTo(new BigDecimal(maxTrade)));
        return new BigDecimal(maxTrade).compareTo(bigDecimal) == -1;
    }

    /***/
    public boolean isAgeInRage(Date birthDate) {
        int years = 0;
        int months = 0;
        int days = 0;
        Calendar birthDay = Calendar.getInstance();
        birthDay.setTimeInMillis(birthDate.getTime());
        long currentTime = System.currentTimeMillis();
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(currentTime);
        years = now.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
        int currMonth = now.get(Calendar.MONTH) + 1;
        int birthMonth = birthDay.get(Calendar.MONTH) + 1;
        months = currMonth - birthMonth;
        if (months < 0) {
            years--;
            months = 12 - birthMonth + currMonth;
            if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
                months--;
        } else if (months == 0 && now.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
            years--;
            months = 11;
        }
        //Calculate the days
        if (now.get(Calendar.DATE) > birthDay.get(Calendar.DATE))
            days = now.get(Calendar.DATE) - birthDay.get(Calendar.DATE);
        else if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
            int today = now.get(Calendar.DAY_OF_MONTH);
            now.add(Calendar.MONTH, -1);
            days = now.getActualMaximum(Calendar.DAY_OF_MONTH) - birthDay.get(Calendar.DAY_OF_MONTH) + today;
        } else {
            days = 0;
            if (months == 12) {
                years++;
                months = 0;
            }
        }
        Log.d("Age Restriction", "" + years);
        return years >= 18;
    }

    /***/
    public void makeLinks(TextView textView, String[] links, ClickableSpan[] clickableSpans) {
        SpannableString spannableString = new SpannableString(textView.getText());
        for (int i = 0; i < links.length; i++) {
            ClickableSpan clickableSpan = clickableSpans[i];
            String link = links[i];
            int startIndexOfLink = textView.getText().toString().indexOf(link);
            spannableString.setSpan(clickableSpan, startIndexOfLink, startIndexOfLink + link.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setText(spannableString, TextView.BufferType.SPANNABLE);
    }

}
