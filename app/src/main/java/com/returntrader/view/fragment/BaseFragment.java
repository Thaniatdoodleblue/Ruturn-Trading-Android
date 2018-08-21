package com.returntrader.view.fragment;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;

import com.returntrader.library.CustomException;
import com.returntrader.presenter.ipresenter.IPresenter;
import com.returntrader.utils.CodeSnippet;
import com.returntrader.view.iview.IView;


/**
 * Created by guru on 6/29/2016.
 */
public abstract class BaseFragment extends Fragment implements IView {

    protected String TAG = getClass().getSimpleName();

    @Override
    public void bindPresenter(IPresenter iPresenter) {
        // nothing to implement here!
    }

    @Override
    public void showMessage(String message) {
        if (((IView) getActivity()) != null)
            ((IView) getActivity()).showMessage(message);
    }


    @Override
    public void showMessage(int resId) {
        ((IView) getActivity()).showMessage(resId);
    }

    @Override
    public void showMessage(CustomException e) {
        if (getActivity() != null)
            ((IView) getActivity()).showMessage(e.getException());
    }

    @Override
    public void showProgressbar() {
        if (((IView) getActivity()) != null)
            ((IView) getActivity()).showProgressbar();
    }

    @Override
    public void dismissProgressbar() {
        try {
            if (getActivity() != null) {
                ((IView) getActivity()).dismissProgressbar();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showSnackBar(String message) {
        ((IView) getActivity()).showSnackBar(message);
    }

    @Override
    public void showNetworkMessage() {
        ((IView) getActivity()).showNetworkMessage();
    }


    @Override
    public CodeSnippet getCodeSnippet() {
        // Log.d(TAG, "getActivity : " + (getActivity()));
        //   Log.d(TAG, "IView getActivity : " + String.valueOf(((IView) getActivity()) != null));
        if (((IView) getActivity()) != null) {
            return ((IView) getActivity()).getCodeSnippet();
        }
        return null;
    }

    @Override
    public void showSnackBar(@NonNull View view, String message) {
        ((IView) getActivity()).showSnackBar(view, message);
    }

    @Override
    public void syncAccount(int syncType) {
        if (((IView) getActivity()) != null) {
            ((IView) getActivity()).syncAccount(syncType);
        }
    }
}
