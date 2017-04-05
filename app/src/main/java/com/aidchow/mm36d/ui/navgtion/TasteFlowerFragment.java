package com.aidchow.mm36d.ui.navgtion;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aidchow.mm36d.R;
import com.aidchow.mm36d.home.HomeFragment;
import com.aidchow.mm36d.home.HomePresenter;
import com.aidchow.mm36d.ui.BaseFragment;
import com.aidchow.mm36d.util.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AidChow on 2017/4/2.
 */

public class TasteFlowerFragment extends BaseFragment {

    public static TasteFlowerFragment newInstance() {
        return new TasteFlowerFragment();
    }

    private TasteFlowerPageAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d("FUCKFUCK", "onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frgment_tasetflower, container, false);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        Logger.d("FUCKFUCK", "onResume");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Logger.d("FUCKFUCK", "onViewCreated");
        Toolbar mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ActionBar ab = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setTitle(R.string.app_name);
        }
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        tabLayout.setupWithViewPager(viewPager);
        setupViewPager(viewPager);
    }

    @Override
    public void onStop() {
        super.onStop();
        Logger.d("FUCKFUCK", "onStop");
    }

    private void setupViewPager(ViewPager viewPager) {
        Logger.d("FUCKFUCK", "setupViewPager1");
        adapter = new TasteFlowerPageAdapter(getChildFragmentManager());
        adapter.addFragment(HomeFragment.newInstance(HomeFragment.HOME_TYPE.HOME),
                this.getString(R.string.home));
        adapter.addFragment(HomeFragment.newInstance(HomeFragment.HOME_TYPE.POP),
                this.getString(R.string.pop));
        adapter.addFragment(HomeFragment.newInstance(HomeFragment.HOME_TYPE.LIKE),
                this.getString(R.string.like));
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
    }


    private class TasteFlowerPageAdapter extends FragmentStatePagerAdapter {
        private final List<String> mTitles = new ArrayList<>();
        private final List<HomeFragment> mHomeFragments = new ArrayList<>();

        public TasteFlowerPageAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(HomeFragment homeFragment, String title) {
            mHomeFragments.add(homeFragment);
            mTitles.add(title);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            super.destroyItem(container, position, object);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                new HomePresenter(mHomeFragments.get(0), HomeFragment.HOME_TYPE.HOME);
            }
            if (position == 1) {
                new HomePresenter(mHomeFragments.get(1), HomeFragment.HOME_TYPE.POP);
            }
            if (position == 2) {
                new HomePresenter(mHomeFragments.get(2), HomeFragment.HOME_TYPE.LIKE);
            }
            return mHomeFragments.get(position);
        }

        @Override
        public int getCount() {
            return mTitles.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }
    }
}
