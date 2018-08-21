package com.returntrader.view.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.returntrader.R;
import com.returntrader.common.Constants;
import com.returntrader.library.Log;
import com.returntrader.model.common.QuestionItem;
import com.returntrader.view.iview.IQuestionView;

/**
 * Created by moorthy on 12/4/2017.
 */

public class QuestionDialogFragment extends BaseDialogFragment implements IQuestionView {
    private String TAG = getClass().getSimpleName();
    private FragmentManager mFragmentManager = null;
    private QuestionItem mQuestionItem;
    private AnswerFragment mAnswerFragment;
    private int mProgressSuccess = -1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_egg;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mQuestionItem = getCodeSnippet().getRandomQuestion();
        loadFragment(Constants.QuestionFragmentType.FRAGMENT_QUESTION);
    }

    private Bundle getQuestionBundle() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.BundleKey.BUNDLE_QUESTIONS_ITEM, mQuestionItem);
        bundle.putInt(Constants.BundleKey.BUNDLE_PROGRESS_STATUS, mProgressSuccess);
        return bundle;
    }

    @Override
    public void loadFragment(int type) {
        if (mFragmentManager == null) {
            mFragmentManager = getChildFragmentManager();
        }
        Log.d(TAG, "getQuestionBundle : " + getQuestionBundle().getParcelable(Constants.BundleKey.BUNDLE_QUESTIONS_ITEM));
        switch (type) {
            case Constants.QuestionFragmentType.FRAGMENT_QUESTION:
                QuestionFragment questionFragment = new QuestionFragment();
                questionFragment.setArguments(getQuestionBundle());
                mFragmentManager.beginTransaction()
                        .replace(R.id.container_egg, questionFragment)
                        .commit();
                break;
            case Constants.QuestionFragmentType.FRAGMENT_ANSWER:
                mAnswerFragment = new AnswerFragment();
                mAnswerFragment.setArguments(getQuestionBundle());
                mFragmentManager.beginTransaction()
                        .replace(R.id.container_egg, mAnswerFragment)
                        .commit();
                break;
        }
    }


    @Override
    public void dismissDialog() {
        dismiss();
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        //showMessage("onDismiss");
    }

    @Override
    public void updateProgressStatus(int resultCode) {
        this.mProgressSuccess = resultCode;
        if (mAnswerFragment != null) {
            mAnswerFragment.onPostResult(resultCode);
        }
    }

    public interface QuestionProgressListener {
        void onPostResult(int resultCode);
    }
}
