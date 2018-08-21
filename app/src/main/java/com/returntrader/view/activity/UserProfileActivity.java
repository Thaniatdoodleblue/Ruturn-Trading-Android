package com.returntrader.view.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.returntrader.R;
import com.returntrader.utils.IPermissionHandler;

public class UserProfileActivity extends BaseActivity implements View.OnClickListener {


    private EditText vEdtFirstName, vEdtSurname;
    private Button btn;
    private LinearLayout vPhotoeditLay;
    private IPermissionHandler iPermissionHandler;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        btn = (Button) findViewById(R.id.next);
        btn.setOnClickListener(this);
        vPhotoeditLay = (LinearLayout) findViewById(R.id.vPhotoeditLay);
        vPhotoeditLay.setOnClickListener(this);
        vEdtFirstName = (EditText) findViewById(R.id.vEdtFirstName);
        vEdtSurname = (EditText) findViewById(R.id.vEdtSurname);

        vEdtSurname.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        vEdtFirstName.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
    }

    protected int getLayoutId() {
        return R.layout.activity_profile;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getCodeSnippet().hideKeyboard(UserProfileActivity.this);
                onBackPressed();
                break;
        }
        return true;
    }


    @Override
    public void onClick(View view) {


        int id = view.getId();
        switch (id) {
            case R.id.vPhotoeditLay:
                showMessage("image clicked");
                break;
            case R.id.next:
                startActivity(new Intent(getApplicationContext(), EmailAckActivity.class));
                break;
        }

    }






}
