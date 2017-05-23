package com.aidchow.mm36d.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.aidchow.entity.ImageDetailEntity;
import com.aidchow.mm36d.data.ImageDetailListDataSource;
import com.aidchow.mm36d.ui.navgtion.TasteFlowerFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aidchow on 17-5-23.
 */

public class ImageLocalDataSource implements ImageDetailListDataSource {

    private static ImageLocalDataSource INSTANCE;
    private ImageDbHelper dbHelper;

    private ImageLocalDataSource(Context context) {
        dbHelper = new ImageDbHelper(context);
    }

    public static synchronized ImageLocalDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new ImageLocalDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public void getImageDetail(String label, int page, LoadImageDetailCall loadImageDetailCall) {
        //nothing ,pass this methods
    }

    @Override
    public void saveImages(String imageUrl) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ImagePersistenceContract.ImageEntry.COLUMN_NAME_IMAGE_URL, imageUrl);
        db.insert(ImagePersistenceContract.ImageEntry.TABLE_NAME, null, values);
        db.close();
    }

    @Override
    public void deleteImage(String imageUrl) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String projection = ImagePersistenceContract.ImageEntry.COLUMN_NAME_IMAGE_URL + " == ?";
        String[] selectionArgs = {imageUrl};
        db.delete(ImagePersistenceContract.ImageEntry.TABLE_NAME, projection, selectionArgs);
        db.close();
    }

    @Override
    public void loadLocalImages(LoadImageDetailCall loadImageDetailCall) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                ImagePersistenceContract.ImageEntry._ID,
                ImagePersistenceContract.ImageEntry.COLUMN_NAME_IMAGE_URL
        };
        ImageDetailEntity imageDetailEntity = new ImageDetailEntity();
        List<String> imageUrls = new ArrayList<>();
        Cursor c = db.query(ImagePersistenceContract.ImageEntry.TABLE_NAME,
                projection, null, null, null, null, null);
        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                String imageUrl = c.getString(c.getColumnIndexOrThrow(
                        ImagePersistenceContract.ImageEntry.COLUMN_NAME_IMAGE_URL));
                imageUrls.add(imageUrl);
            }
        }
        if (c != null) {
            c.close();
        }
        db.close();
        if (imageUrls.isEmpty()) {
            loadImageDetailCall.onImageDetailLoadError("yet not favorite");
        } else {
            imageDetailEntity.setPicUrls(imageUrls);
            loadImageDetailCall.onImageDetailLoad(imageDetailEntity);
        }
    }

    @Override
    public boolean loadImage(String url) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] projection = {ImagePersistenceContract.ImageEntry.COLUMN_NAME_IMAGE_URL};
        String selection = ImagePersistenceContract.ImageEntry.COLUMN_NAME_IMAGE_URL + " == ?";
        String[] selectionArgs = {url};
        Cursor cursor = db.query(ImagePersistenceContract.ImageEntry.TABLE_NAME,
                projection, selection, selectionArgs, null, null, null);
        boolean have = false;
        if (cursor.getCount() > 0) {
            have = true;
        }
        db.close();
        cursor.close();
        return have;
    }
}
