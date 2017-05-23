package com.aidchow.mm36d.imagedetaillist;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;

import com.aidchow.mm36d.R;
import com.aidchow.mm36d.ui.BaseActivity;

public class ImageDetailListActivity extends BaseActivity {

    public static void startActivity(Context contextl, String label) {
        Intent i = new Intent(contextl, ImageDetailListActivity.class);
        i.putExtra(ImageDetailListFragment.LABEL, label);
        contextl.startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
        String label = getIntent().getStringExtra("label");
        if (label != null) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ImageDetailListFragment imageDetailListFragment = ImageDetailListFragment.newInstance(label);
            new ImageDetailListPresenter(this, imageDetailListFragment);
            ft.add(R.id.frame_content, imageDetailListFragment)
                    .commit();
        }
    }


}
