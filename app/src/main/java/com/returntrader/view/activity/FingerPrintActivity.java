package com.returntrader.view.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.returntrader.R;
import com.returntrader.common.Constants;
import com.returntrader.library.Log;
import com.returntrader.presenter.FingerPrintPresenter;
import com.returntrader.presenter.ipresenter.IFingerPrintPresenter;
import com.returntrader.utils.FingerprintHandler;
import com.returntrader.view.iview.IFingerPrintView;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class FingerPrintActivity extends BaseActivity implements View.OnClickListener, IFingerPrintView {
    // Variable used for storing the key in the Android Keystore container

    private static final String KEY_NAME = "ReturnTraderKey";
    private Button vbtn;
    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;
    private TextView verrorText;
    private TextView vBtnSkip;
    private KeyStore keyStore;
    private Cipher cipher;
    private IFingerPrintPresenter iFingerPrintPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(getLayoutId());
        if (getCodeSnippet().isAboveMarshmallow()) {
            keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
            fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
        }

        verrorText = findViewById(R.id.errorText);
        vBtnSkip = findViewById(R.id.vBtnSkip);
        vBtnSkip.setOnClickListener(this);

        iFingerPrintPresenter = new FingerPrintPresenter(this);
        iFingerPrintPresenter.onCreatePresenter(getIntent().getExtras());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkingIsFingerPrintSensorAvailable();
        }
    }

    protected int getLayoutId() {
        return R.layout.activity_finger_print;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.vBtnSkip:
                navigateToThankYou();
                break;
        }
    }

    /***/
    private void navigateToThankYou() {
        Intent navIntent = null;
        switch (iFingerPrintPresenter.getEntryPoint()) {
            case Constants.FingerPrintNavigation.FROM_REGISTRATION:
                navIntent = new Intent(FingerPrintActivity.this, ThankYouActivity.class);
                break;

            case Constants.FingerPrintNavigation.FROM_LOGIN:
                Bundle bundle = iFingerPrintPresenter.getBundleData();
                navIntent = new Intent(FingerPrintActivity.this, OtpCodeVerifyActivity.class);
                navIntent.putExtras(bundle);
                break;
        }

        if (navIntent != null) {
            startActivity(navIntent);
            finish();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkingIsFingerPrintSensorAvailable() {
        if (fingerprintManager != null && getCodeSnippet().isAboveMarshmallow()) {
            // Check whether the device has a Fingerprint sensor.
            if (!fingerprintManager.isHardwareDetected()) {
                navigateToThankYou();
            } else {
                // Checks whether fingerprint permission is set on manifest
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                    verrorText.setText("Fingerprint authentication permission not enabled");
                } else {
                    // Check whether at least vEdtTxtone fingerprint is registered
                    if (!fingerprintManager.hasEnrolledFingerprints()) {
                        verrorText.setText("Register at least one fingerprint in Settings");
                        Toast.makeText(FingerPrintActivity.this, "Register at least one fingerprint in Settings", Toast.LENGTH_SHORT).show();
                    } else {
                        // Checks whether lock screen security is enabled or not
                        if (!keyguardManager.isKeyguardSecure()) {
                            verrorText.setText("Lock screen security not enabled in Settings");
                        } else {
                            try {
                                generateKey();
                                if (cipherInit()) {
                                    FingerprintManager.CryptoObject cryptoObject = new FingerprintManager.CryptoObject(cipher);
                                    FingerprintHandler helper = new FingerprintHandler(this, iFingerPrintResponseListener);
                                    helper.startAuth(fingerprintManager, cryptoObject);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        } else {
            navigateToThankYou();
            verrorText.setText("Your Device does not have a Fingerprint Sensor");
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    public boolean cipherInit() {
        try {
            cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get Cipher", e);
        }

        try {
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME,
                    null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (KeyPermanentlyInvalidatedException e) {
            return false;
        } catch (KeyStoreException | CertificateException | UnrecoverableKeyException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to init Cipher", e);
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    protected void generateKey() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
        } catch (Exception e) {
            e.printStackTrace();
        }

        KeyGenerator keyGenerator;
        try {
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new RuntimeException("Failed to get KeyGenerator instance", e);
        }

        try {
            keyStore.load(null);
            keyGenerator.init(new
                    KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                            KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());
            keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException |
                InvalidAlgorithmParameterException
                | CertificateException | IOException e) {
            //    throw new RuntimeException(e);
        }
    }


    /***/
    private FingerprintHandler.IFingerPrintResponseListener iFingerPrintResponseListener = new FingerprintHandler.IFingerPrintResponseListener() {
        @Override
        public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
            iFingerPrintPresenter.setFingerPrintPinEnabled(true);
            navigateToThankYou();
        }

        @Override
        public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
            updateError("Fingerprint Authentication help\n" + helpString);
        }

        @Override
        public void onAuthenticationError(int errMsgId, CharSequence errString) {
            updateError("Fingerprint Authentication error\n" + errString);
        }

        @Override
        public void onAuthenticationFailed() {
            updateError("Fingerprint Authentication failed.");
        }
    };


    private void updateError(String message) {
        verrorText.setText(message);
    }

}