package com.ljb.utils;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by longjinbin on 2018/7/22.
 */

public class VolleyBitmapCache implements ImageLoader.ImageCache {

    private LruCache<String, Bitmap> mCache;

    public VolleyBitmapCache() {
        int maxMemorySize = 1024 * 1024 * 10;
        mCache = new LruCache<String, Bitmap>(maxMemorySize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes() * bitmap.getHeight();
            }
        };
    }

    @Override
    public Bitmap getBitmap(String key) {
        return mCache.get(key);
    }

    @Override
    public void putBitmap(String key, Bitmap bitmap) {
        mCache.put(key, bitmap);
    }
}
