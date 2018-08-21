package com.returntrader.view.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.returntrader.R;
import com.returntrader.library.Log;
import com.returntrader.presenter.AddMoneyPresenter;

/**
 * Created by moorthy on 8/2/2017.
 */

public class FicaActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        bindActivity();
    }

    private void bindActivity() {
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView toolbarTitle =  toolbar.findViewById(R.id.tv_toolbar_title);
        toolbarTitle.setText(R.string.txt_menu_fica);

        getSupportActionBar().setTitle(R.string.txt_empty_string);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_send_mail:
                sendDocuments();
                break;
        }
    }


    public void sendDocuments() {
        try {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            Uri uri = Uri.parse("mailto:" + getString(R.string.txt_email_report_bug));
            intent.setData(uri);
            intent.putExtra(Intent.EXTRA_SUBJECT, "FICA Documents");
            startActivity(Intent.createChooser(intent, "Send via"));
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected int getLayoutId() {
        return R.layout.activity_fica;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getCodeSnippet().hideKeyboard(FicaActivity.this);
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
