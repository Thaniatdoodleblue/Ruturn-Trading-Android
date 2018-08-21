package com.returntrader.adapter.viewholder;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.returntrader.R;
import com.returntrader.common.Constants;
import com.returntrader.library.Log;
import com.returntrader.model.common.Report;
import com.returntrader.utils.CodeSnippet;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by moorthy on 7/26/2017.
 */

public class ReportViewHolder extends BaseViewHolder<Report> {
    private TextView mTvContent;
    private TextView mTvTime;
    private CodeSnippet mCodeSnippet;

    public ReportViewHolder(View itemView, CodeSnippet codeSnippet) {
        super(itemView);
        this.mCodeSnippet = codeSnippet;
        bindHolder();
    }

    private void bindHolder() {
        mTvContent =  itemView.findViewById(R.id.tv_content);
        mTvTime =  itemView.findViewById(R.id.tv_time);
    }

    @Override
    void populateData(Report data) {
        if (!(TextUtils.isEmpty(data.getContent()))) {
            mTvContent.setText(data.getContent());
        }

        if (mCodeSnippet != null){
            if (!(TextUtils.isEmpty(data.getCreatedAt()))) {
                String displayTime = mCodeSnippet.getFormattedNewsDate(data.getCreatedAt(),
                        Constants.Common.DATE_FORMATTER_REPORTS_ACTUAL, Constants.Common.DATE_FORMATTER_REPORTS_EXPECTED_DDMMMYYYY);
               Calendar calendar  = mCodeSnippet
                       .getCalendarToStandard(displayTime,
                               Constants.Common.DATE_FORMATTER_REPORTS_EXPECTED_DDMMMYYYY,
                       TimeZone.getDefault());
                Log.d(TAG,"displayTime : "  +displayTime);

               String displayDate = Constants.Common.DATE_FORMATTER_REPORTS_EXPECTED_DDMMMYYYY.format(calendar.getTime());
                Log.d(TAG,"displayDate : "  +displayDate);
                if (!(TextUtils.isEmpty(displayDate))){
                    mTvTime.setText(displayDate);
                }
            }
        }
    }
}
