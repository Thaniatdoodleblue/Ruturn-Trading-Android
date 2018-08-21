package com.returntrader.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.returntrader.R;
import com.returntrader.common.Constants;
import com.returntrader.model.common.QuestionItem;
import com.returntrader.view.activity.WebViewActivity;

/**
 * Created by moorthy on 8/26/2017
 */

public class AnswerFragment extends BaseFragment implements View.OnClickListener, QuestionDialogFragment.QuestionProgressListener {


    private TextView mTvStatus;
    private TextView mTvStatusFailure;
    private TextView mTvTryAgain;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rowItem = inflater.inflate(R.layout.fragment_answer, container, false);
        bindFragment(rowItem);
        return rowItem;
    }

    private void bindFragment(View rowItem) {
        rowItem.findViewById(R.id.tv_skip).setOnClickListener(this);
        rowItem.findViewById(R.id.tv_close).setOnClickListener(this);
        mTvStatus = rowItem.findViewById(R.id.tv_status);
        mTvStatusFailure = rowItem.findViewById(R.id.tv_status_failure);
        mTvTryAgain = rowItem.findViewById(R.id.tv_try_again);
        TextView questions = rowItem.findViewById(R.id.tv_question);
        CheckBox checkBox = rowItem.findViewById(R.id.check_answer);
        checkBox.setOnClickListener(v -> dismissAlert());
        /*as pre client asked its has been given as click*/
        //  checkBox.setEnabled(false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            QuestionItem questionItem = bundle.getParcelable(Constants.BundleKey.BUNDLE_QUESTIONS_ITEM);
            if (questionItem != null) {
                questions.setText(questionItem.getQuestion());
                checkBox.setText(questionItem.getAnswer());
                onPostResult(bundle.getInt(Constants.BundleKey.BUNDLE_PROGRESS_STATUS, -1));
            }
        }


    }


    private void navigateToFaq() {
        Intent intent = new Intent(getActivity(), WebViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BundleKey.BUNDLE_WEB_VIEW_URL, getString(R.string.url_help));
        bundle.putString(Constants.BundleKey.BUNDLE_WEB_VIEW_TITLE, getString(R.string.txt_menu_help_qa));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void dismissAlert() {
        QuestionDialogFragment questionDialogFragment = (QuestionDialogFragment) getParentFragment();
        assert questionDialogFragment != null;
        questionDialogFragment.dismissDialog();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.tv_close:
                dismissAlert();
                break;
            case R.id.tv_try_again:
                dismissAlert();
                break;
        }
    }


    @Override
    public void onPostResult(int resultCode) {
        switch (resultCode) {
            case Constants.TransactionType.RESPONSE_SUCCESS:
                mTvTryAgain.setVisibility(View.INVISIBLE);
                mTvStatusFailure.setVisibility(View.GONE);
                mTvStatus.setVisibility(View.VISIBLE);
                mTvStatus.setText(R.string.txt_congrats);
                break;
            case Constants.TransactionType.RESPONSE_FAILURE:
                //mTvTryAgain.setVisibility(View.VISIBLE);
                mTvStatus.setVisibility(View.GONE);
                mTvStatusFailure.setVisibility(View.VISIBLE);
                break;
            default:
                mTvTryAgain.setVisibility(View.INVISIBLE);
                mTvStatusFailure.setVisibility(View.GONE);
                mTvStatus.setVisibility(View.VISIBLE);
                mTvStatus.setText(R.string.txt_request_progress);
                break;
        }
    }
}
