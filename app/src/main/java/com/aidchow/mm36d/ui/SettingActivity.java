package com.aidchow.mm36d.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.aidchow.mm36d.R;
import com.mikepenz.aboutlibraries.LibsBuilder;
import com.mikepenz.aboutlibraries.ui.LibsSupportFragment;


public class SettingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_category);
        initToolbar(getString(R.string.menu_setting));
        LibsSupportFragment fragment = new LibsBuilder()
                .withLibraries("crouton")
                .supportFragment();
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.frame_content, fragment).commit();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
