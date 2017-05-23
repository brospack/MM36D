package com.aidchow.mm36d.imagedetaillist;

import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aidchow.entity.ImageDetailEntity;
import com.aidchow.mm36d.R;
import com.aidchow.mm36d.adpter.ImageDetailListAdapter;
import com.aidchow.mm36d.favorite.FavoriteFragment;
import com.aidchow.mm36d.imagedetail.ImageDetailActivity;
import com.aidchow.mm36d.ui.widget.ScrollChildSwipeRefreshLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;

/**
 * Created by AidChow on 2017/4/26.
 */

public class ImageDetailListFragment extends Fragment implements ImageDetailListContract.View,
        BaseQuickAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemChildClickListener {
    private ImageDetailListContract.Presenter mPresenter;
    private ImageDetailListAdapter mImageDetailListAdapter;
    private String label;
    public static final String LABEL = "label";
    private ActionBar actionBar;
    private LinearLayout mErrorLinearLayout;
    private ScrollChildSwipeRefreshLayout mScrollChildSwipeRefreshLayout;
    private Drawable mSaveDrawable;
    private Drawable mDeleteDrawable;

    public static ImageDetailListFragment newInstance(String label) {
        Bundle bundle = new Bundle();
        bundle.putString(LABEL, label);
        ImageDetailListFragment imageDetailListFragment = new ImageDetailListFragment();
        imageDetailListFragment.setArguments(bundle);
        return imageDetailListFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        label = getArguments().getString(LABEL);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (getArguments().getString("IMAGE_LOAD") != null) {
            return;
        }
        if (label != null) {
            if (mPresenter != null) {
                mPresenter.getImageDetail(label, 0, false);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext())
                .inflate(R.layout.fragment_image_detail_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toolbar mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        mScrollChildSwipeRefreshLayout = (ScrollChildSwipeRefreshLayout)
                view.findViewById(R.id.swipe_refresh);

        mErrorLinearLayout = (LinearLayout) view.findViewById(R.id.linear_error_layout);
        TextView mTvReload = (TextView) view.findViewById(R.id.tv_reload);

        //if there some error happened  use this to reload
        mTvReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getImageDetail(label, 0, false);
            }
        });
        RecyclerView imageListRecyclerview = (RecyclerView) view.
                findViewById(R.id.image_list_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        imageListRecyclerview.setLayoutManager(linearLayoutManager);
        mImageDetailListAdapter = new ImageDetailListAdapter(new ArrayList<String>(0), mPresenter);
        imageListRecyclerview.setAdapter(mImageDetailListAdapter);
        mImageDetailListAdapter.setOnItemClickListener(this);
        mScrollChildSwipeRefreshLayout.setOnRefreshListener(this);

        mImageDetailListAdapter.setOnItemChildClickListener(this);
        mSaveDrawable = getActivity().getDrawable(R.drawable.ic_favorite);
        mDeleteDrawable = getActivity().getDrawable(R.drawable.ic_favorite_borde);
    }

    @Override
    public void setPresenter(ImageDetailListContract.Presenter presenter) {
        if (presenter != null) {
            mPresenter = presenter;
        }
    }

    @Override
    public void showImageDetail(ImageDetailEntity imageDetailEntity, boolean refresh) {
        mErrorLinearLayout.setVisibility(View.GONE);
        actionBar.setTitle(imageDetailEntity.getTitle());
        if (refresh) {
            mImageDetailListAdapter.setNewData(imageDetailEntity.getPicUrls());
        } else {
            mImageDetailListAdapter.addData(imageDetailEntity.getPicUrls());
        }
        mImageDetailListAdapter.addFooterView(LayoutInflater.from(getContext())
                .inflate(R.layout.footer_item, (ViewGroup) getView(), false));

    }

    @Override
    public void showLoadError(String msg) {
        mErrorLinearLayout.setVisibility(View.VISIBLE);
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
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent detailIntent = new Intent(getActivity(), ImageDetailActivity.class);
        detailIntent.putExtra("image_url", mImageDetailListAdapter.getData().get(position));
        ImageView coverImageView = (ImageView) view.findViewById(R.id.image_view_mm);
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                .makeSceneTransitionAnimation(getActivity(), coverImageView, "cover");
        startActivity(detailIntent, optionsCompat.toBundle());
    }

    @Override
    public void onRefresh() {
        mPresenter.getImageDetail(label, 0, true);
    }

    @Override
    public void onPause() {
        super.onPause();
        getArguments().putString("IMAGE_LOAD", "image_load");
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        String imageUrl = mImageDetailListAdapter.getData().get(position);
        ImageButton mFabSaveButton = (ImageButton) view.findViewById(R.id.image_fab_save);
        if (mPresenter.loadImage(imageUrl)) {
            mPresenter.delete(imageUrl);
            mFabSaveButton.setImageDrawable(mDeleteDrawable);
        } else {
            mPresenter.saveImage(imageUrl);
            mFabSaveButton.setImageDrawable(mSaveDrawable);
        }
        Intent intent = new Intent(FavoriteFragment.FAVORITE_CHANGE);
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);

    }
}
