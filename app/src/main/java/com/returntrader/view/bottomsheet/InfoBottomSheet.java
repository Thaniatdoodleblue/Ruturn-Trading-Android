package com.returntrader.view.bottomsheet;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.returntrader.R;
import com.returntrader.model.common.MoneyInfo;

import java.util.List;

public class InfoBottomSheet  extends BottomSheetDialog implements View.OnClickListener {

     private RecyclerView vRvInfo;
     private Button vBtnClose;
     private List<MoneyInfo> moneyInfoList;

    public InfoBottomSheet(@NonNull Context context, List<MoneyInfo> moneyInfoList) {
        super(context);
        this.moneyInfoList = moneyInfoList;
        View sheetView = this.getLayoutInflater().inflate(R.layout.bottom_sheet_profit_loss, null);
        vRvInfo = sheetView.findViewById(R.id.rv_info);
        vBtnClose = sheetView.findViewById(R.id.close);
        vBtnClose.setOnClickListener(this);
        setAdapter();
        setContentView(sheetView);
    }

    private void setAdapter() {

    }

    @Override
    public void onClick(View v) {

    }
}
