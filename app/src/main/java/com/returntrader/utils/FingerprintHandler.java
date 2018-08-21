package com.returntrader.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;

import com.returntrader.presenter.ipresenter.IFingerPrintPresenter;

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

@RequiresApi(api = Build.VERSION_CODES.M)
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {

    private Context mContext;
    private IFingerPrintResponseListener iFingerPrintResponseListener;
    private static final String KEY_NAME = "ReturnTraderKey";
    private CodeSnippet mCodeSnippet;
    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;
    private Cipher mCiper;
    private KeyStore mKeyStore;

    // Constructor
    public FingerprintHandler(Context context, IFingerPrintResponseListener iFingerPrintResponseListener) {
        this.mContext = context;
        this.mCodeSnippet = new CodeSnippet(context);
        this.iFingerPrintResponseListener = iFingerPrintResponseListener;
        if (getCodeSnippet().isAboveMarshmallow()) {
            keyguardManager = (KeyguardManager) mContext.getSystemService(Context.KEYGUARD_SERVICE);
            fingerprintManager = (FingerprintManager) mContext.getSystemService(Context.FINGERPRINT_SERVICE);
        }
    }

    private CodeSnippet getCodeSnippet() {
        if (mCodeSnippet == null) {
            mCodeSnippet = new CodeSnippet(mContext);
        }
        return mCodeSnippet;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject) {
        CancellationSignal cancellationSignal = new CancellationSignal();
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        iFingerPrintResponseListener.onAuthenticationError(errMsgId, errString);
    }

    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        iFingerPrintResponseListener.onAuthenticationHelp(helpMsgId, helpString);
    }

    @Override
    public void onAuthenticationFailed() {
        iFingerPrintResponseListener.onAuthenticationFailed();
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        iFingerPrintResponseListener.onAuthenticationSucceeded(result);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean isFingerPrintSensorAvailable() {

        if (mCodeSnippet.isAboveMarshmallow()) {

            if (fingerprintManager == null) {
                return false;
            }

            // Check whether the device has a Fingerprint sensor.
            if (!fingerprintManager.isHardwareDetected()) {
                return false;
            } else {
                // Checks whether fingerprint permission is set on manifest
                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                } else {
                    // Check whether at least vEdtTxtone fingerprint is registered
                    if (!fingerprintManager.hasEnrolledFingerprints()) {
                        return false;
                    } else {
                        // Checks whether lock screen security is enabled or not
                        if (!keyguardManager.isKeyguardSecure()) {
                            return false;
                        } else {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void doAuthenticate() {
        if (isFingerPrintSensorAvailable()) {
            generateKey();
            if (cipherInit()) {
                FingerprintManager.CryptoObject cryptoObject = new FingerprintManager.CryptoObject(mCiper);
                FingerprintHandler helper = new FingerprintHandler(mContext, iFingerPrintResponseListener);
                helper.startAuth(fingerprintManager, cryptoObject);
            }
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    public boolean cipherInit() {
        try {
            mCiper = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get Cipher", e);
        }
        try {
            mKeyStore.load(null);
            SecretKey key = (SecretKey) mKeyStore.getKey(KEY_NAME,
                    null);
            mCiper.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (KeyPermanentlyInvalidatedException e) {
            return false;
        } catch (KeyStoreException | CertificateException | UnrecoverableKeyException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            //    throw new RuntimeException("Failed to init Cipher", e);
            return false;
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    protected void generateKey() {
        try {
            mKeyStore = KeyStore.getInstance("AndroidKeyStore");
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
            mKeyStore.load(null);
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
                | CertificateException | IllegalStateException | IOException e) {
            // throw new RuntimeException(e);
        }
    }


    public interface IFingerPrintResponseListener {

        void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result);

        void onAuthenticationHelp(int helpMsgId, CharSequence helpString);

        void onAuthenticationError(int errMsgId, CharSequence errString);

        void onAuthenticationFailed();
    }


}
