package com.returntrader.model.dto.response;

import java.io.File;

/**
 * Created by nirmal on 4/20/2018.
 */

public class FICAPreviewData {
    private String imageUrl;
    private String title;
    private File imageFile;

    public FICAPreviewData() {

    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public File getImageFile() {
        return imageFile;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
