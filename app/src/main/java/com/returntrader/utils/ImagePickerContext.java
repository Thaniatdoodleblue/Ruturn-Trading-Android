package com.returntrader.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;

import com.returntrader.common.Constants;
import com.returntrader.common.TraderApplication;
import com.returntrader.library.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.returntrader.common.Constants.RequestCodes.PICK_CAMERA_IMAGE_REQUEST_CODE;

/**
 * Created by moorthy on 7/15/2016
 */

public class ImagePickerContext {

    private String TAG = getClass().getSimpleName();
    private String cameraFileName;
    private String selectedImagePath;
    private Uri mImageCaptureUri;
    private Context mContext;


    public ImagePickerContext() {
    }

    public ImagePickerContext(Context context) {
        this.mContext = context;
    }


    public String getSelectedImagePath() {
        return selectedImagePath;
    }

    public Uri getImageCaptureUri() {
        return mImageCaptureUri;
    }

    public void showChooserDialog(final FragmentActivity activity) {
        final String[] items = {"Take Photo", "Choose from Library"};
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Add Photo!");
        builder.setItems(items, (dialog, which) -> {
            switch (which) {
                case 0:
                    openCamera(activity);
                    break;
                case 1:
                    choiceAvatarFromGallery(activity);
                    break;
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    /***/
    private void choiceAvatarFromGallery(FragmentActivity context) {
        Intent intent;
        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        context.startActivityForResult(intent, Constants.RequestCodes.PICK_GALLERY_IMAGE_REQUEST_CODE);
        selectedImagePath = Environment.getExternalStorageDirectory() + "/" + "avatar" + System.currentTimeMillis() + ".jpg";
        mImageCaptureUri = Uri.fromFile(new File(selectedImagePath));
    }


    /***/
    private void openCamera(FragmentActivity activity) {
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                File photoFile = createImageFile();
                if (photoFile != null) {
                    mImageCaptureUri = FileProvider.getUriForFile(TraderApplication.getInstance(),
                            TraderApplication.getInstance().getPackageName(),
                            photoFile);
                }
            } else {
                selectedImagePath = Environment.getExternalStorageDirectory() + "/" + "avatar" + System.currentTimeMillis() + ".jpg";
                mImageCaptureUri = Uri.fromFile(new File(selectedImagePath));
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
            activity.startActivityForResult(intent, PICK_CAMERA_IMAGE_REQUEST_CODE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***/
    private File createImageFile() throws IOException {
        File storageDir = TraderApplication.getInstance().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                "avatar" + System.currentTimeMillis(),  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        selectedImagePath = image.getAbsolutePath();
        return image;
    }

    /***/
    public File storeBitmapInSDCard(Bitmap bitmap, String path) {
        if (bitmap != null) {
            Bitmap out = Bitmap.createScaledBitmap(bitmap, 320, 480, false);
            File file = new File(path);
            FileOutputStream fOut;
            try {
                fOut = new FileOutputStream(file);
                out.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                fOut.flush();
                fOut.close();
                bitmap.recycle();
                out.recycle();
                return file;
            } catch (Exception e) {
                //Log.d(TAG, "storeBitmapInSDCard " + e.toString());
                return null;
            }
        }
        return null;
    }


    /***/
    public File getFileFromBitmap(Bitmap bitmap) throws IOException {
        File f = new File(TraderApplication.getInstance().getCacheDir(), "profile_pic.jpg");
        if (f.exists()) {
            f.delete();
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bos);
        byte[] bitmapdata = bos.toByteArray();
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(bitmapdata);
        fos.flush();
        fos.close();
        return f;
    }

    /***/
    public Bitmap getRotators(String uri) {
        Bitmap avatar = BitmapFactory.decodeFile(uri);
        ExifInterface ei;
        try {
            ei = new ExifInterface(uri);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    return rotateImage(avatar, 90);
                case ExifInterface.ORIENTATION_ROTATE_180:
                    return rotateImage(avatar, 180);
                case ExifInterface.ORIENTATION_ROTATE_270:
                    return rotateImage(avatar, 270);
                default:
                    return rotateImage(avatar, 0);

            }
        } catch (IOException | OutOfMemoryError e) {
            e.printStackTrace();
        }
        return null;
    }

    /***/
    public Bitmap rotateImage(Bitmap bitmap, int degree) throws OutOfMemoryError {
        Log.d("Rotation Degree", "" + degree);
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        /*Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() / 2,
                bitmap.getHeight() / 2, true);*/
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
    }
}
