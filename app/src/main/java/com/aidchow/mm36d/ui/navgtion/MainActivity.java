package com.aidchow.mm36d.ui.navgtion;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import com.aidchow.mm36d.R;
import com.aidchow.mm36d.category.CategoryFragment;
import com.aidchow.mm36d.category.CategoryPresenter;
import com.aidchow.mm36d.favorite.FavoriteFragment;
import com.aidchow.mm36d.ui.BaseActivity;
import com.aidchow.mm36d.ui.SettingActivity;

import static com.aidchow.mm36d.R.id.drawer_layout;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private int mConcurrentPoi = -1;
    private final FragmentManager.OnBackStackChangedListener onBackStackChangedListener = new FragmentManager.OnBackStackChangedListener() {
        @Override
        public void onBackStackChanged() {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            } else {
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        if (savedInstanceState != null) {
            mConcurrentPoi = savedInstanceState.getInt("mConcurrentPoi");
            controlFragment(mConcurrentPoi);
        } else {
            controlFragment(0);
        }
        getSupportFragmentManager().addOnBackStackChangedListener(onBackStackChangedListener);
    }

    private void initView() {
        mDrawerLayout = (DrawerLayout) findViewById(drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        setupDrawerContent(mNavigationView);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getSupportFragmentManager().popBackStack();
                } else {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(navigationView.getMenu().getItem(0).getItemId());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Bundle extras = ActivityOptions.makeCustomAnimation(
                this, R.anim.fade_in, R.anim.fade_out).toBundle();
        switch (item.getItemId()) {
            case R.id.menu_taste_flower:
                controlFragment(0);
                break;
            case R.id.menu_find_flower:
                controlFragment(1);
                break;
            case R.id.menu_favorite:
                controlFragment(2);
                break;
            case R.id.menu_setting:
                startActivity(new Intent(this, SettingActivity.class), extras);
                break;
        }
        mDrawerLayout.closeDrawers();
        return true;
    }

    /**
     * control the fragment show or hide
     *
     * @param position the navigation view's menu item
     */
    private void controlFragment(int position) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment oldFragment = fm.findFragmentByTag(makeTag(mConcurrentPoi));
        if (oldFragment != null) {
            ft.hide(oldFragment);
        }
        mConcurrentPoi = position;
        Fragment mConcurrentFragment = fm.findFragmentByTag(makeTag(position));
        if (mConcurrentFragment != null) {
            ft.show(mConcurrentFragment);
        } else {
            ft.add(R.id.frame_layout, getFragment(position), makeTag(position));
        }
        ft.commitAllowingStateLoss();
    }

    /**
     * get the fragment instance
     *
     * @param position concurrent click navigation view menu item
     * @return fragment instance
     */
    private Fragment getFragment(int position) {
        Fragment fragment = null;
        if (position == 0) {
            fragment = TasteFlowerFragment.newInstance();
        } else if (position == 1) {
            fragment = CategoryFragment.newInstance();
        } else if (position == 2) {
            fragment = FavoriteFragment.newInstance();
        }
        return fragment;
    }

    private String makeTag(int position) {
        return "MM36D" + position;
    }

    @Override
    public void onBackPressed() {
        //当drawerLayout开启时，按backUp键时关闭drawerLayout
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawers();
        } else if (mConcurrentPoi != 0 && getSupportFragmentManager().getBackStackEntryCount() == 0) {
            //if mConcurrentPoi !=0 make the navigation to first
            controlFragment(0);
            mNavigationView.setCheckedItem(R.id.menu_taste_flower);
        } else if (mConcurrentPoi != 0 && getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("mConcurrentPoi", mConcurrentPoi);

    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mConcurrentPoi = savedInstanceState.getInt("mConcurrentPoi");
        controlFragment(mConcurrentPoi);
    }
}
