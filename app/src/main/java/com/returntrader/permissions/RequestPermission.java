package com.returntrader.permissions;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

/**
 * Created by guru on 2/27/2016
 */
public class RequestPermission implements IPermissionHandler {

    private String TAG = "RequestPermission";
    private PermissionProducer mPermissionProducer;

    public static IPermissionHandler newInstance(PermissionProducer permissionProducer) {
        RequestPermission requestPermission = new RequestPermission();
        requestPermission.mPermissionProducer = permissionProducer;
        return requestPermission;
    }


    @Override
    public void showPermissionExplainDialog(Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setPositiveButton("Okay", (dialog, id) -> {
                    //requestLocationPermission();
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void callFetchContactPermissionHandler() {
        if (ContextCompat.checkSelfPermission(mPermissionProducer.getActivity(),
                Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(mPermissionProducer.getActivity(),
                    Manifest.permission.READ_CONTACTS)) {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                ActivityCompat.requestPermissions(mPermissionProducer.getActivity(),
                        new String[]{Manifest.permission.READ_CONTACTS},
                        PERMISSIONS_REQUEST_ACCESS_CONTACT);
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(mPermissionProducer.getActivity(),
                        new String[]{Manifest.permission.READ_CONTACTS},
                        PERMISSIONS_REQUEST_ACCESS_CONTACT);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            mPermissionProducer.onReceivedPermissionStatus(PERMISSIONS_REQUEST_ACCESS_CONTACT, true);
        }
    }

    @Override
    public void callCameraPermissionHandler() {
        if (ContextCompat.checkSelfPermission(mPermissionProducer.getActivity(),
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(mPermissionProducer.getActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(mPermissionProducer.getActivity(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(mPermissionProducer.getActivity(),
                    Manifest.permission.CAMERA)) {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                ActivityCompat.requestPermissions(mPermissionProducer.getActivity(),
                        new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMISSIONS_REQUEST_CAMERA);
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(mPermissionProducer.getActivity(),
                        new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMISSIONS_REQUEST_CAMERA);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            mPermissionProducer.onReceivedPermissionStatus(PERMISSIONS_REQUEST_CAMERA, true);
        }
    }

    @Override
    public void callSmsPermissionHandler() {
        if (ContextCompat.checkSelfPermission(mPermissionProducer.getActivity(),
                Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(mPermissionProducer.getActivity(),
                        Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(mPermissionProducer.getActivity(),
                    Manifest.permission.RECEIVE_SMS)) {
                ActivityCompat.requestPermissions(mPermissionProducer.getActivity(),
                        new String[]{Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS},
                        PERMISSIONS_REQUEST_RECEIVE_SMS);
            } else {
                ActivityCompat.requestPermissions(mPermissionProducer.getActivity(),
                        new String[]{Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS},
                        PERMISSIONS_REQUEST_RECEIVE_SMS);
            }
        } else {
            mPermissionProducer.onReceivedPermissionStatus(PERMISSIONS_REQUEST_RECEIVE_SMS, true);
        }
    }


}
