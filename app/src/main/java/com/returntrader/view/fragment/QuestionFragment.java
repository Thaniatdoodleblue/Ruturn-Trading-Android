package com.returntrader.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.returntrader.R;
import com.returntrader.common.Constants;
import com.returntrader.model.common.QuestionItem;

/**
 * Created by moorthy on 8/26/2017
 */

public class QuestionFragment extends BaseFragment implements View.OnClickListener  {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rowItem = inflater.inflate(R.layout.fragment_questions, container, false);
        bindFragment(rowItem);
        return rowItem;
    }

    private void bindFragment(View rowItem) {
        rowItem.findViewById(R.id.btn_submit).setOnClickListener(this);
        rowItem.findViewById(R.id.btn_skip).setOnClickListener(this);
        TextView questions =  rowItem.findViewById(R.id.tv_question);
        Bundle bundle = getArguments();
        if (bundle != null) {
            QuestionItem questionItem = bundle.getParcelable(Constants.BundleKey.BUNDLE_QUESTIONS_ITEM);
            Log.d(TAG, "questionItem :" + questionItem);
            if (questionItem != null) {
                questions.setText(questionItem.getQuestion());
            }
        }
    }

    private void loadAnswerFragment() {
        QuestionDialogFragment questionDialogFragment = (QuestionDialogFragment) getParentFragment();
        assert questionDialogFragment != null;
        questionDialogFragment.loadFragment(Constants.QuestionFragmentType.FRAGMENT_ANSWER);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_submit:
                loadAnswerFragment();
                break;
            case R.id.btn_skip:
                loadAnswerFragment();
                break;
        }
    }




}
