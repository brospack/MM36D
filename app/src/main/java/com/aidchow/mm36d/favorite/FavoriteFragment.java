package com.aidchow.mm36d.favorite;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.aidchow.entity.ImageDetailEntity;
import com.aidchow.mm36d.R;
import com.aidchow.mm36d.adpter.FavoriteAdapter;
import com.aidchow.mm36d.adpter.ImageDetailListAdapter;
import com.aidchow.mm36d.imagedetail.ImageDetailActivity;
import com.aidchow.mm36d.imagedetaillist.ImageDetailListPresenter;
import com.aidchow.mm36d.ui.widget.ScrollChildSwipeRefreshLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment implements FavoriteContract.View, BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    public static FavoriteFragment newInstance() {
        Bundle bundle = new Bundle();
        FavoriteFragment favoriteFragment = new FavoriteFragment();
        favoriteFragment.setArguments(bundle);
        return favoriteFragment;
    }

    private ScrollChildSwipeRefreshLayout mScrollChildSwipeRefreshLayout;
    private FavoriteContract.Presenter mPresenter;

    private FavoriteAdapter mFavoriteAdapter;
    private Drawable mSaveDrawable;
    private Drawable mDeleteDrawable;
    private TextView mEmptyTv;

    public final static String FAVORITE_CHANGE = "favorite_change";
    private FavoriteChangeReceiver mFavoriteChangeReceiver;

    @Override
    public void onResume() {
        super.onResume();
        if (getArguments().getString("IMAGE_LOAD") != null) {
            return;
        }
        if (mPresenter != null) {
            mPresenter.start();
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ActionBar ab = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setTitle(R.string.menu_favorite);
        }
        mEmptyTv = (TextView) view.findViewById(R.id.tv_empty);
        mScrollChildSwipeRefreshLayout = (ScrollChildSwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        mScrollChildSwipeRefreshLayout.setOnRefreshListener(this);
        RecyclerView mFavoriteRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mFavoriteRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mFavoriteAdapter = new FavoriteAdapter(new ArrayList<String>(0), mPresenter);
        mFavoriteRecyclerView.setAdapter(mFavoriteAdapter);
        mFavoriteAdapter.setOnItemChildClickListener(this);
        mFavoriteAdapter.setOnItemClickListener(this);
        mSaveDrawable = getActivity().getDrawable(R.drawable.ic_favorite);
        mDeleteDrawable = getActivity().getDrawable(R.drawable.ic_favorite_borde);
        mFavoriteChangeReceiver = new FavoriteChangeReceiver();
        IntentFilter intentFilter = new IntentFilter(FAVORITE_CHANGE);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mFavoriteChangeReceiver, intentFilter);
    }

    @Override
    public void setPresenter(FavoriteContract.Presenter presenter) {
        if (presenter != null) {
            mPresenter = presenter;
        }
    }

    @Override
    public void showImageDetail(ImageDetailEntity imageDetailEntity, boolean refresh) {
        if (!imageDetailEntity.getPicUrls().isEmpty()) {
            mEmptyTv.setVisibility(View.GONE);
            if (refresh) {
                mFavoriteAdapter.setNewData(imageDetailEntity.getPicUrls());
            } else {
                mFavoriteAdapter.addData(imageDetailEntity.getPicUrls());
            }
        } else {
            mEmptyTv.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showLoadError(String msg) {
        if (msg.equals("yet not favorite")) {
            mEmptyTv.setVisibility(View.VISIBLE);
            mFavoriteAdapter.notifyDataSetChanged();
            return;
        }
        Snackbar.make(getView(), msg, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showLoading(final boolean isShow) {
        mScrollChildSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mScrollChildSwipeRefreshLayout.setRefreshing(isShow);
            }
        });
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        String imageUrl = mFavoriteAdapter.getData().get(position);
        if (mPresenter.loadImage(imageUrl)) {
            mPresenter.delete(imageUrl);
            mFavoriteAdapter.remove(position);
            mPresenter.loadImages(true);
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent detailIntent = new Intent(getActivity(), ImageDetailActivity.class);
        detailIntent.putExtra("image_url", mFavoriteAdapter.getData().get(position));
        ImageView coverImageView = (ImageView) view.findViewById(R.id.image_view_mm);
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                .makeSceneTransitionAnimation(getActivity(), coverImageView, "cover");
        startActivity(detailIntent, optionsCompat.toBundle());
    }

    @Override
    public void onRefresh() {
        mPresenter.loadImages(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        getArguments().putString("IMAGE_LOAD", "image_load");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mFavoriteChangeReceiver);
    }

    private class FavoriteChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(FAVORITE_CHANGE)) {
                mPresenter.loadImages(true);
            }
        }
    }
}
