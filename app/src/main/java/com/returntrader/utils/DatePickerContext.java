package com.returntrader.utils;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;

import com.returntrader.common.Constants;

import java.util.Calendar;

/**
 * Created by moorthy on 12/1/2016.
 */
public class DatePickerContext {


    String TAG = getClass().getSimpleName();
    private Context mContext;
    private String TAG_TIME_PICKER = "TimePicker";
    private String TAG_DATE_PICKER = "DatePicker";
    private int TIME_PICKER_INTERVAL_HOUR = 1;
    private int TIME_PICKER_INTERVAL_MIN = 30;
    private CodeSnippet mCodeSnippet;


    public DatePickerContext(Context context) {
        this.mContext = context;
        this.mCodeSnippet = new CodeSnippet(context);
    }


    private CodeSnippet getCodeSnippet() {
        return mCodeSnippet;
    }

    public void showDatePicker(Context context, final OnDateSelectedListener tOnDateSelectedListener, Calendar currentCalender) {
        if (currentCalender == null) {
            currentCalender = Calendar.getInstance();
        }
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, (view, year, monthOfYear, dayOfMonth) -> {
            Calendar newDate = Calendar.getInstance();
            newDate.set(year, monthOfYear, dayOfMonth);
            tOnDateSelectedListener.getSelectedDate(Constants.Common.DATE_FORMATTER.format(newDate.getTime()), newDate.getTimeInMillis());

        }, currentCalender.get(Calendar.YEAR), currentCalender.get(Calendar.MONTH), currentCalender.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setOnCancelListener(dialog -> tOnDateSelectedListener.dialogDismiss());
        datePickerDialog.show();
    }


    public void showDatePicker(Context context, final OnDateSelectedListener tOnDateSelectedListener, boolean isMinEnabled, boolean isMaxEnabled) {

        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, (view, year, monthOfYear, dayOfMonth) -> {
            Calendar newDate = Calendar.getInstance();
            newDate.set(year, monthOfYear, dayOfMonth);
            tOnDateSelectedListener.getSelectedDate(Constants.Common.DATE_FORMATTER.format(newDate.getTime()), newDate.getTimeInMillis());
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        if (isMinEnabled) {
            //datePickerDialog.getDatePicker().setMinDate(CodeSnippet.getCurrentDate().getTime());
            datePickerDialog.getDatePicker().setMinDate(newCalendar.getTime().getTime());
        }
        if (isMaxEnabled) {
            datePickerDialog.getDatePicker().setMaxDate(getCodeSnippet().getCurrentDate().getTime());
        }
        datePickerDialog.setOnCancelListener(dialog -> tOnDateSelectedListener.dialogDismiss());
        datePickerDialog.show();
    }

    public void showDatePicker(Context context, final OnDateSelectedListener tOnDateSelectedListener, boolean isMinEnabled, boolean isMaxEnabled, long timeInMillSec) {

        Calendar newCalendar = Calendar.getInstance();
        if (timeInMillSec != 0) {
            newCalendar.setTimeInMillis(timeInMillSec);
        }

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, (view, year, monthOfYear, dayOfMonth) -> {
            Calendar newDate = Calendar.getInstance();
            newDate.set(year, monthOfYear, dayOfMonth);
            tOnDateSelectedListener.getSelectedDate(Constants.Common.DATE_FORMATTER.format(newDate.getTime()), newDate.getTimeInMillis());
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        if (isMinEnabled) {
            //datePickerDialog.getDatePicker().setMinDate(CodeSnippet.getCurrentDate().getTime());
            datePickerDialog.getDatePicker().setMinDate(newCalendar.getTime().getTime());
        }
        if (isMaxEnabled) {
            datePickerDialog.getDatePicker().setMaxDate(getCodeSnippet().getCurrentDate().getTime());
        }
        datePickerDialog.setOnCancelListener(dialog -> tOnDateSelectedListener.dialogDismiss());
        datePickerDialog.show();
    }


    public void showTimePicker(Context context, final OnTimeSelectedListener tOnDateSelectedListener) {

        Calendar newCalendar = Calendar.getInstance();

        TimePickerDialog timePickerDialog = new TimePickerDialog(context, (view, hourOfDay, minute) -> tOnDateSelectedListener.getTime(showTime(hourOfDay, minute), hourOfDay, minute), newCalendar.get(Calendar.HOUR_OF_DAY), newCalendar.get(Calendar.MINUTE), false);

        timePickerDialog.setOnCancelListener(dialog -> tOnDateSelectedListener.dialogDismiss());

        timePickerDialog.show();
    }

    private String showTime(int hour, int min) {
        String format = "";
        if (hour == 0) {
            hour += 12;
            format = "AM";
        } else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }

        String minutes = "";
        if (min < 10)
            minutes = "0" + min;
        else
            minutes = String.valueOf(min);


        String tempHour = "";

        if (hour > 0 && hour < 10) {
            tempHour = "0" + hour;
        } else {
            tempHour = String.valueOf(hour);
        }

        return tempHour + ":" + minutes +
                " " + format;
    }


    public interface OnDateSelectedListener {

        void getSelectedDate(String date, long dateInMilliSeconds);

        void dialogDismiss();
    }

    public interface OnTimeSelectedListener {

        void getTime(String time, int hour, int min);

        void dialogDismiss();
    }
}
