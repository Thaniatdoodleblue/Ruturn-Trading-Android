package com.returntrader.presenter;

import android.os.Bundle;

import com.returntrader.presenter.ipresenter.IShakeMakeInstructionPresenter;
import com.returntrader.view.iview.IShakeMakeInstructionView;

/**
 * Created by azeem on 05-Jul-18
 */
public class ShakeMakeInstructionPresenter extends BasePresenter implements IShakeMakeInstructionPresenter {

    private IShakeMakeInstructionView iShakeMakeInstructionView;

    public ShakeMakeInstructionPresenter(IShakeMakeInstructionView iView) {
        super(iView);
        this.iShakeMakeInstructionView = iView;
    }

    @Override
    public void onCreatePresenter(Bundle bundle) {

    }
}
