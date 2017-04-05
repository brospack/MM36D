package com.aidchow.mm36d.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aidchow.entity.ImageEntity;
import com.aidchow.mm36d.R;
import com.aidchow.mm36d.adpter.ImageAdapter;
import com.aidchow.mm36d.adpter.ImageDiffCallBack;
import com.aidchow.mm36d.ui.widget.ScrollChildSwipeRefreshLayout;
import com.aidchow.mm36d.util.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AidChow on 2017/4/2.
 */

public class HomeFragment extends Fragment implements HomeContract.View, SwipeRefreshLayout.OnRefreshListener {


    public enum HOME_TYPE {
        HOME,
        POP,
        LIKE
    }

    private HomeContract.Presenter mPresenter;

    private static final String TYPE_KEY = "type_key";

    private LinearLayout mErrorLinearLayout;
    private LinearLayout mNoContentLinearLayout;
    private RecyclerView mImageRecyclerView;
    private ScrollChildSwipeRefreshLayout mScrollChildSwipeRefreshLayout;
    private int type;
    private TextView mTvReload;

    private ImageAdapter imageAdapter;
    private List<ImageEntity> imageEntityList;

    public static HomeFragment newInstance(HOME_TYPE type) {
        Bundle bundle = new Bundle();
        if (type == HOME_TYPE.HOME) {
            bundle.putInt(TYPE_KEY, 0);
        } else if (type == HOME_TYPE.POP) {
            bundle.putInt(TYPE_KEY, 1);
        } else if (type == HOME_TYPE.LIKE) {
            bundle.putInt(TYPE_KEY, 2);
        }
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setArguments(bundle);
        return homeFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getInt(TYPE_KEY);
            imageEntityList = new ArrayList<>(0);
            imageAdapter = new ImageAdapter(imageEntityList);
        }
        Logger.d("FUCKFUCK", "HOME onCreate");

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter != null) {
            mPresenter.start();
        } else {
            switch (type) {
                case 0:
                    mPresenter = new HomePresenter(this, HOME_TYPE.HOME);
                    break;
                case 1:
                    mPresenter = new HomePresenter(this, HOME_TYPE.POP);
                    break;
                case 2:
                    mPresenter = new HomePresenter(this, HOME_TYPE.LIKE);
                    break;
            }
            mPresenter.start();
        }
        Logger.d("FUCKFUCK", "HOME onResume");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initEvent();
    }

    /**
     * init event
     */
    private void initEvent() {
        mScrollChildSwipeRefreshLayout.setOnRefreshListener(this);
        mTvReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefresh();
            }
        });
    }

    /**
     * init views
     *
     * @param view
     */
    private void initView(View view) {
        mErrorLinearLayout = (LinearLayout) view.findViewById(R.id.linear_error_layout);
        mNoContentLinearLayout = (LinearLayout) view.findViewById(R.id.linear_no_content_layout);
        mTvReload = (TextView) view.findViewById(R.id.tv_reload);
        mImageRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        StaggeredGridLayoutManager mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mStaggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        mImageRecyclerView.setLayoutManager(mStaggeredGridLayoutManager);
        mImageRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mImageRecyclerView.setAdapter(imageAdapter);
        mScrollChildSwipeRefreshLayout = (ScrollChildSwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        mScrollChildSwipeRefreshLayout.setScrollUpChild(mImageRecyclerView);
        mScrollChildSwipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));

    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        if (presenter != null) {
            mPresenter = presenter;
        }
        Logger.d("FUCKFUCK", "setPresenter");
    }

    @Override
    public void showImages(List<ImageEntity> list) {
        mImageRecyclerView.setVisibility(View.VISIBLE);
        mErrorLinearLayout.setVisibility(View.GONE);
        mNoContentLinearLayout.setVisibility(View.GONE);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new ImageDiffCallBack(imageEntityList, list), true);
        diffResult.dispatchUpdatesTo(imageAdapter);
        imageEntityList = list;
        imageAdapter.setDatas(list);
    }

    @Override
    public void showNoImages() {
        mImageRecyclerView.setVisibility(View.GONE);
        mErrorLinearLayout.setVisibility(View.GONE);
        mNoContentLinearLayout.setVisibility(View.VISIBLE);
        showMessage(this.getString(R.string.no_content));
    }

    @Override
    public void showLoadingImages(final boolean isActive) {
        final ScrollChildSwipeRefreshLayout mScrollChildSwipeRefreshLayout = (ScrollChildSwipeRefreshLayout) getView().findViewById(R.id.swipe_refresh);
        mScrollChildSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mScrollChildSwipeRefreshLayout.setRefreshing(isActive);
            }
        });
    }

    @Override
    public void showLoadingError(String errorMsg) {
        mNoContentLinearLayout.setVisibility(View.GONE);
        mErrorLinearLayout.setVisibility(View.VISIBLE);
        showMessage(errorMsg);
    }

    private void showMessage(String Msg) {
        Snackbar.make(getView(), Msg, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }


    @Override
    public void onRefresh() {
        if (type == 0) {
            mPresenter.loadHomeImages(1);
            Logger.d("FUCK", "home");
        }
        if (type == 1) {
            mPresenter.loadOthers("pop", 1);
            Logger.d("FUCK", "pop");
        }
        if (type == 2) {
            mPresenter.loadOthers("like", 1);
            Logger.d("FUCK", "like");
        }
    }
}
