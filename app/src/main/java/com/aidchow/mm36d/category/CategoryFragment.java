package com.aidchow.mm36d.category;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aidchow.mm36d.R;

public class CategoryFragment extends Fragment {

    public static CategoryFragment newInstance() {
        return new CategoryFragment();
    }

    private ActionBar ab;
    private LocalBroadcastManager localBroadcastManager;
    private ABTitleChangeBroadcastReceiver abTitleChangeBroadcastReceiver;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ab = ((AppCompatActivity) getActivity()).getSupportActionBar();
        setupActionBar();
        CategoryContentFragment categoryContentFragment = CategoryContentFragment.newInstance();
        new CategoryPresenter(categoryContentFragment);
        final FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.frame_content, categoryContentFragment);
        ft.commit();
        fm.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (fm.getBackStackEntryCount() == 0) {
                    setupActionBar();
                }
            }
        });
        localBroadcastManager = LocalBroadcastManager.getInstance(getContext());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("to_label");
        abTitleChangeBroadcastReceiver = new ABTitleChangeBroadcastReceiver();
        localBroadcastManager.registerReceiver(abTitleChangeBroadcastReceiver, intentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(abTitleChangeBroadcastReceiver);
    }


    private void setupActionBar() {
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setTitle(R.string.menu_find_flower);
        }
    }

    class ABTitleChangeBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String abTitle = intent.getStringExtra("category");
            ab.setTitle(abTitle);
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setHomeAsUpIndicator(null);
        }
    }

}
