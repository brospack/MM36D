package com.aidchow.mm36d.imagedetail;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.aidchow.entity.ImageDetailEntity;
import com.aidchow.mm36d.App;
import com.aidchow.mm36d.R;
import com.aidchow.mm36d.ui.BaseActivity;
import com.aidchow.mm36d.util.Logger;
import com.aidchow.mm3d.OnRequestListener;

import java.util.ArrayList;
import java.util.List;

public class ImageDetailActivity extends BaseActivity {

    public static void startActivity(Context contextl, String label) {
        Intent i = new Intent(contextl, ImageDetailActivity.class);
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
            new ImageDetailPresenter(imageDetailListFragment);
            ft.add(R.id.frame_content, imageDetailListFragment)
                    .commit();
        }
    }


}
