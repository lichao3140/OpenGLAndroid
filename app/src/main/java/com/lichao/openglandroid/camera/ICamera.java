package com.lichao.openglandroid.camera;

import android.graphics.Point;
import android.graphics.SurfaceTexture;

/**
 * Created by ChaoLi on 2018/5/13 0013 - 16:43
 * Email: lichao3140@gmail.com
 * Version: v1.0
 */
public interface ICamera {
    boolean open(int cameraId);
    void setConfig(Config config);
    boolean preview();
    boolean switchTo(int cameraId);
    void takePhoto(TakePhotoCallback callback);
    boolean close();
    void setPreviewTexture(SurfaceTexture texture);

    Point getPreviewSize();
    Point getPictureSize();

    void setOnPreviewFrameCallback(PreviewFrameCallback callback);

    class Config{
        float rate; //宽高比
        int minPreviewWidth;
        int minPictureWidth;
    }

    interface TakePhotoCallback{
        void onTakePhoto(byte[] bytes, int width, int height);
    }

    interface PreviewFrameCallback{
        void onPreviewFrame(byte[] bytes, int width, int height);
    }
}
