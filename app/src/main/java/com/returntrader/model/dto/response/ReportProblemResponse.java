package com.returntrader.model.dto.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.returntrader.model.common.Report;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moorthy on 11/17/2017.
 */

@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class ReportProblemResponse extends BaseResponse implements Parcelable {

    @JsonField(name = "Reports")
    private List<Report> reports;

    public ReportProblemResponse() {

    }

    public List<Report> getReports() {
        return reports;
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
    }

    protected ReportProblemResponse(Parcel in) {
        if (in.readByte() == 0x01) {
            reports = new ArrayList<>();
            in.readList(reports, Report.class.getClassLoader());
        } else {
            reports = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (reports == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(reports);
        }
    }

    @SuppressWarnings("unused")
    public static final Creator<ReportProblemResponse> CREATOR = new Creator<ReportProblemResponse>() {
        @Override
        public ReportProblemResponse createFromParcel(Parcel in) {
            return new ReportProblemResponse(in);
        }

        @Override
        public ReportProblemResponse[] newArray(int size) {
            return new ReportProblemResponse[size];
        }
    };
}
