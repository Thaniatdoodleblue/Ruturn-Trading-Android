package com.returntrader.model.common;

import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class ShakeMakeGroupData {

    private String snmGroupId;
    private String groupName;
    private String imageUnselected;
    private String imageSelected;
    private String createDate;
    private String status;
    private boolean isSelected;

    public ShakeMakeGroupData(String snmGroupId, String groupName, String imageUnselected, String imageSelected, String createDate, String status, boolean isSelected) {
        this.snmGroupId = snmGroupId;
        this.groupName = groupName;
        this.imageUnselected = imageUnselected;
        this.imageSelected = imageSelected;
        this.createDate = createDate;
        this.status = status;
        this.isSelected = isSelected;
    }

    public ShakeMakeGroupData() {
    }

    public String getSnmGroupId() {
        return snmGroupId;
    }

    public void setSnmGroupId(String snmGroupId) {
        this.snmGroupId = snmGroupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getImageUnselected() {
        return imageUnselected;
    }

    public void setImageUnselected(String imageUnselected) {
        this.imageUnselected = imageUnselected;
    }

    public String getImageSelected() {
        return imageSelected;
    }

    public void setImageSelected(String imageSelected) {
        this.imageSelected = imageSelected;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
