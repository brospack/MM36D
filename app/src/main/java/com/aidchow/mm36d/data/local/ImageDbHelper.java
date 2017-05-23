package com.aidchow.mm36d.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by aidchow on 17-5-23.
 */

public class ImageDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MM36D.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String BOOLEAN_TYPE = " INTEGER";

    private static final String COMMA_SEP = " ,";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ImagePersistenceContract.ImageEntry.TABLE_NAME + " ("
                    + ImagePersistenceContract.ImageEntry._ID + TEXT_TYPE + " PRIMARY KEY,"
                    + ImagePersistenceContract.ImageEntry.COLUMN_NAME_IMAGE_URL + TEXT_TYPE
                    + ")";

    public ImageDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
