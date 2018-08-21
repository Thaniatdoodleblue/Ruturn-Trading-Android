package com.returntrader.view.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.returntrader.R;
import com.returntrader.common.Constants;
import com.returntrader.common.ExceptionTracker;
import com.returntrader.library.CustomException;
import com.returntrader.presenter.ipresenter.IPresenter;
import com.returntrader.sync.SyncAccountDetailService;
import com.returntrader.utils.CodeSnippet;
import com.returntrader.view.iview.IView;
import com.returntrader.view.widget.CustomProgressbar;

import java.io.File;


/**
 * Created by nirmal on 7/31/2017.
 */

public abstract class BaseDialogFragment extends DialogFragment implements IView {
    protected View mParentView;
    protected CodeSnippet mCodeSnippet;
    private IPresenter iPresenter;
    private CustomProgressbar mCustomProgressbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mParentView = View.inflate(getContext(), getLayoutId(), null);
        return mParentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    protected abstract int getLayoutId();

    @Override
    public void onStart() {
        super.onStart();
        if (iPresenter != null) iPresenter.onStartPresenter();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (iPresenter != null) iPresenter.onResumePresenter();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (iPresenter != null) iPresenter.onPausePresenter();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (iPresenter != null) iPresenter.onStopPresenter();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (iPresenter != null) iPresenter.onDestroyPresenter();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(int resId) {
        showMessage(getString(resId));
    }


    @Override
    public void showMessage(CustomException e) {
        if (e != null) {
            showMessage(e.getException());
        }
    }

    @Override
    public void showProgressbar() {
        // TODO have to menu_view_question_preference the custom progressbar
        getProgressBar().show();
    }

    @Override
    public void dismissProgressbar() {
        // TODO dismiss the progressbar
        getActivity().runOnUiThread(() -> {
            try {
                getProgressBar().dismissProgress();
            } catch (Exception e) {
                ExceptionTracker.track(e);
            }
        });
    }


    @Override
    public void showSnackBar(String message) {
        if (mParentView != null) {
            Snackbar snackbar = Snackbar.make(mParentView, message, Snackbar.LENGTH_LONG);
            snackbar.setActionTextColor(Color.RED);
            snackbar.show();
        }
    }

    @Override
    public void showSnackBar(@NonNull View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.RED);
        snackbar.show();
    }

    @Override
    public void showNetworkMessage() {
        getCodeSnippet().showNetworkSettings();
    }


    public void bindPresenter(IPresenter iPresenter) {
        this.iPresenter = iPresenter;
    }

    @Override
    public CodeSnippet getCodeSnippet() {
        if (mCodeSnippet == null) {
            mCodeSnippet = new CodeSnippet(getActivity());
            return mCodeSnippet;
        }
        return mCodeSnippet;
    }

    private CustomProgressbar getProgressBar() {
        if (mCustomProgressbar == null) {
            mCustomProgressbar = new CustomProgressbar(getActivity());
        }
        return mCustomProgressbar;
    }

    @Nullable
    @Override
    public View getView() {
        return mParentView;
    }

    @Override
    synchronized public void syncAccount(int syncType) {
        Intent updatePriceService = new Intent(getActivity(), SyncAccountDetailService.class);
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.AccountSync.BUNDLE_SYNC_TYPE, syncType);
        updatePriceService.putExtras(bundle);
        if (getActivity() != null){
            getActivity().startService(updatePriceService);
        }

    }
}
