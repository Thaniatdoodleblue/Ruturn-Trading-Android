package com.returntrader.model.dto.request;

/**
 * Created by moorthy on 11/15/2017.
 */

public class SettingPreferenceRequest extends BaseRequest {


    public SettingPreferenceRequest(String userId) {
        super(userId);
    }

    private int breakNewsEnabled;

    private int companyNotificationEnabled;

    private int jseNotificationEnabled;


    public int getBreakNewsEnabled() {
        return breakNewsEnabled;
    }

    public void setBreakNewsEnabled(int breakNewsEnabled) {
        this.breakNewsEnabled = breakNewsEnabled;
    }

    public int getCompanyNotificationEnabled() {
        return companyNotificationEnabled;
    }

    public void setCompanyNotificationEnabled(int companyNotificationEnabled) {
        this.companyNotificationEnabled = companyNotificationEnabled;
    }

    public int getJseNotificationEnabled() {
        return jseNotificationEnabled;
    }

    public void setJseNotificationEnabled(int jseNotificationEnabled) {
        this.jseNotificationEnabled = jseNotificationEnabled;
    }
}
