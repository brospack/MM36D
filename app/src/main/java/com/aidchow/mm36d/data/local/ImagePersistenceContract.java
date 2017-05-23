package com.aidchow.mm36d.data.local;

import android.provider.BaseColumns;

/**
 * Created by aidchow on 17-5-23.
 */

public final class ImagePersistenceContract {
    private ImagePersistenceContract() {
    }

    public static abstract class ImageEntry implements BaseColumns {
        public static final String TABLE_NAME = "mm36_image";
        public static final String COLUMN_NAME_IMAGE_URL = "image_url";
    }
}
