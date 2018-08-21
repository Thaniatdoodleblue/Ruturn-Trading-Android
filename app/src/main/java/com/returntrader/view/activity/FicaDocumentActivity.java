package com.returntrader.view.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.returntrader.R;
import com.returntrader.common.Constants;
import com.returntrader.presenter.FicaDocumentPresenter;
import com.returntrader.presenter.ipresenter.IFicaDocumentPresenter;
import com.returntrader.view.iview.IFicaDocumentView;

import static com.returntrader.common.Constants.BundleKey.BUNDLE_BANKSTATUS;
import static com.returntrader.common.Constants.BundleKey.BUNDLE_IDCARDSTATUS;
import static com.returntrader.common.Constants.BundleKey.BUNDLE_VERIFICATIONSTATUS;

/**
 * Created by moorthy on 8/2/2017
 */

public class FicaDocumentActivity extends BaseActivity implements View.OnClickListener, IFicaDocumentView {
    private IFicaDocumentPresenter iFicaDocumentPresenter;
    private TextView ficaVerifyStatus;
    private CheckBox checkboxRsaId, checkboxBankStatement;
    private TextView tvMailus;
    private TextView ficaVerifyStatusPendingNote;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fica_document);
        bindActivity();
        iFicaDocumentPresenter = new FicaDocumentPresenter(this);
        iFicaDocumentPresenter.onCreatePresenter(getIntent().getExtras());
    }

    /***/
    private void bindActivity() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView toolbarTitle = toolbar.findViewById(R.id.tv_toolbar_title);
        toolbarTitle.setText(R.string.txt_menu_fica);
        getSupportActionBar().setTitle(R.string.txt_empty_string);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        checkboxRsaId = findViewById(R.id.checkbox_rsa_id);
        checkboxBankStatement = findViewById(R.id.checkbox_bank_statement);
        ficaVerifyStatus = findViewById(R.id.tv_status_verification_pending);
        tvMailus = findViewById(R.id.tvMailus);
        ficaVerifyStatusPendingNote = findViewById(R.id.tv_status_verification_pending_note);
        ficaVerifyStatus.setVisibility(View.GONE);
        ficaVerifyStatusPendingNote.setVisibility(View.GONE);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_upload_bank_statement:
                showUploadDialog(0);
                break;
            case R.id.layout_upload_rsa_id:
                showUploadDialog(1);
                break;
            case R.id.tvMailus:
                redirectToEmail();
                break;
        }
    }

    /***/
    private void redirectToEmail() {
        Intent mailIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        mailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{tvMailus.getText().toString().trim()});
        mailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
        mailIntent.putExtra(Intent.EXTRA_TEXT, "");
        //it.setType("text/plain");
        mailIntent.setType("message/rfc822");
        startActivity(mailIntent);
    }

    /***/
    private void showUploadDialog(int from) {
//        if (verificationStatus) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_upload_doc);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.show();
        Window window = dialog.getWindow();
        assert window != null;
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView tvTitle = dialog.findViewById(R.id.tv_alert_title);
        TextView tvGreenCard = dialog.findViewById(R.id.tv_green_card);
        TextView tvIdCard = dialog.findViewById(R.id.tv_id_card);
        switch (from) {
            case 0:
                tvTitle.setText(R.string.txt_title_bank_statement);
                tvIdCard.setVisibility(View.GONE);
                tvGreenCard.setText(R.string.txt_upload_bank_statement);
                break;
        }
        tvGreenCard.setOnClickListener(view -> {
            dialog.dismiss();
            Bundle bundle = new Bundle();
            if (from == 0) {
                bundle.putInt(Constants.BundleKey.BUNDLE_CONTENT_TYPE, Constants.FicaContentType.CONTENT_BANK_STATEMENT);
            } else {
                bundle.putInt(Constants.BundleKey.BUNDLE_CONTENT_TYPE, Constants.FicaContentType.CONTENT_GREEN_CARD_ID);
            }
            navigateToUpload(bundle);
        });

        tvIdCard.setOnClickListener(view -> {
            dialog.dismiss();
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.BundleKey.BUNDLE_CONTENT_TYPE, Constants.FicaContentType.CONTENT_ID_CARD);
            navigateToUpload(bundle);
        });
//        } else {
//            showMessage("Please wait until your previous document get verified");
//        }
    }

    /***/
    private void navigateToUpload(Bundle bundle) {
        Intent intent = new Intent(getActivity(), UploadActivity.class);
        bundle.putBoolean(BUNDLE_IDCARDSTATUS, idCardStatus);
        bundle.putBoolean(BUNDLE_BANKSTATUS, bankStatus);
        bundle.putBoolean(BUNDLE_VERIFICATIONSTATUS, verificationStatus);
        intent.putExtras(bundle);
        startActivityForResult(intent, Constants.RequestCodes.NAVIGATE_FICA_TO_UPLOAD);
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getCodeSnippet().hideKeyboard(FicaDocumentActivity.this);
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /***/
    private String getText(boolean isUploaded) {
        if (isUploaded) {
            return getString(R.string.status_complete);
        }
        return getString(R.string.status_incomplete);
    }

    private boolean idCardStatus = false;
    private boolean bankStatus = false;
    private boolean verificationStatus = true;

    @Override
    public void setIdCardStatus(boolean isUploaded) {
        idCardStatus = isUploaded;
        checkboxRsaId.setChecked(isUploaded);
        checkboxRsaId.setText(getText(isUploaded));
    }

    @Override
    public void setBankStatementStatus(boolean isUploaded) {
        bankStatus = isUploaded;
        checkboxBankStatement.setChecked(isUploaded);
        checkboxBankStatement.setText(getText(isUploaded));
    }

    @Override
    public void setUploadStatusText(int statusText) {
        ((TextView) findViewById(R.id.tv_upload_status_id_card)).setText(statusText);
        ((TextView) findViewById(R.id.tv_upload_status_bank)).setText(statusText);
    }


    @Override
    public void showStatusPending(boolean isPendingOrVerified) {
        if (checkboxRsaId.isChecked() && checkboxBankStatement.isChecked()) {
            ficaVerifyStatus.setVisibility(View.VISIBLE);

            iFicaDocumentPresenter.updateFICAStatus(true);
        }
        String status;
        //isPendingOrVerified = false;
        if (isPendingOrVerified) {
            status = getString(R.string.txt_verification_success);
        } else {
            ficaVerifyStatusPendingNote.setVisibility(View.VISIBLE);
            status = getString(R.string.txt_verification_pending);
        }
        ficaVerifyStatus.setText(status);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        iFicaDocumentPresenter.onActivityResultPresenter(requestCode, resultCode, data);
    }
}
