package com.returntrader.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.returntrader.R;
import com.returntrader.presenter.FingerPrintAuthFragmentPresenter;
import com.returntrader.presenter.ipresenter.IFingerPrintAuthFragmentPresenter;
import com.returntrader.view.iview.IFingerPrintAuthFragmentView;

/**
 * Created by nirmal on 2/8/2018.
 */

public class FingerPrintAuthFragment extends BaseFragment implements IFingerPrintAuthFragmentView, View.OnClickListener {
    private IFingerPrintAuthFragmentPresenter iFingerPrintAuthFragmentPresenter;
    private TextView vBtnSkip;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fingerprint, container, false);
        iFingerPrintAuthFragmentPresenter = new FingerPrintAuthFragmentPresenter(this);
        iFingerPrintAuthFragmentPresenter.onCreatePresenter(getArguments());
        vBtnSkip = rootView.findViewById(R.id.vBtnSkip);
        vBtnSkip.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.vBtnSkip:
                getActivity().getSupportFragmentManager().popBackStack();
                break;
        }
    }
}
