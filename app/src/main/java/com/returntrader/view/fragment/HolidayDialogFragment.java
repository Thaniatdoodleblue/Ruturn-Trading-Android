package com.returntrader.view.fragment;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.returntrader.R;
import com.returntrader.common.Constants;
import com.returntrader.view.activity.WebViewActivity;

/**
 * Created by nirmal on 3/30/2018.
 */

public class HolidayDialogFragment extends BaseDialogFragment implements View.OnClickListener {
    private TextView holidayHyperLink;
    private TextView emailLink;
    private TextView tvAlertOk;

    @Override
    protected int getLayoutId() {
        return R.layout.dilogfragment_holiday;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        holidayHyperLink = view.findViewById(R.id.tv_notice1);
        emailLink = view.findViewById(R.id.tv_notice3);
        tvAlertOk = view.findViewById(R.id.tv_alert_ok);
        holidayHyperLink.setMovementMethod(LinkMovementMethod.getInstance());
        tvAlertOk.setOnClickListener(this);
        holidayHyperLink.setOnClickListener(this);
        emailLink.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_alert_ok) {
            dismiss();
        } else if (v.getId() == R.id.tv_notice1) {
            showFAQ();
        } else if (v.getId() == R.id.tv_notice3) {
            showEmail();
        }
    }

    /***/
    private void showEmail() {
        Intent mailIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        mailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailLink.getText().toString().trim()});
        mailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
        mailIntent.putExtra(Intent.EXTRA_TEXT, "");
        //it.setType("text/plain");
        mailIntent.setType("message/rfc822");
        startActivity(mailIntent);
    }

    /***/
    private void showFAQ() {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BundleKey.BUNDLE_WEB_VIEW_URL, getActivity().getString(R.string.url_help));
        bundle.putString(Constants.BundleKey.BUNDLE_WEB_VIEW_TITLE, getActivity().getString(R.string.txt_menu_help_qa));
        Intent intent = new Intent(getActivity(), WebViewActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
