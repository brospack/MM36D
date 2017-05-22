package com.aidchow.mm36d.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.aidchow.mm36d.imagedetail.ImageDetailListActivity;
import com.aidchow.mm36d.ui.widget.ScrollChildSwipeRefreshLayout;
import com.aidchow.mm36d.util.Logger;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AidChow on 2017/4/2.
 */

public class HomeFragment extends Fragment implements HomeContract.View, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener {


    public enum HOME_TYPE {
        HOME,
        POP,
        LIKE,
        LBEL
    }

    private HomeContract.Presenter mPresenter;

    private static final String TYPE_KEY = "type_key";
    private static final String CATEGORY = "category";
    private LinearLayout mErrorLinearLayout;
    private ScrollChildSwipeRefreshLayout mScrollChildSwipeRefreshLayout;
    private int type;
    private ImageAdapter imageAdapter;
    private List<ImageEntity> imageEntityList;
    private volatile int page = 1;
    private String category;


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

    public static HomeFragment newInstance(HOME_TYPE type, String category) {
        Bundle bundle = new Bundle();
        if (type == HOME_TYPE.HOME) {
            bundle.putInt(TYPE_KEY, 0);
        } else if (type == HOME_TYPE.POP) {
            bundle.putInt(TYPE_KEY, 1);
        } else if (type == HOME_TYPE.LIKE) {
            bundle.putInt(TYPE_KEY, 2);
        } else if (type == HOME_TYPE.LBEL) {
            bundle.putInt(TYPE_KEY, 3);
            bundle.putString(CATEGORY, category);
        }
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setArguments(bundle);
        return homeFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            Logger.d("FUCK", "savedInstanceState");
        }
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getInt(TYPE_KEY);
            category = bundle.getString(CATEGORY);
            imageEntityList = new ArrayList<>(0);
            imageAdapter = new ImageAdapter(imageEntityList);
        }


        Logger.d("FUCKFUCK", "HOME onCreate");

    }

    @Override
    public void onResume() {
        super.onResume();
        if (getArguments().get("save") == null) {
            if (mPresenter != null) {
                mPresenter.start();
            }
            if (mPresenter == null) {
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
                    case 3:
                        mPresenter = new HomePresenter(this, HOME_TYPE.LBEL);
                        break;
                }
                mPresenter.start();
            }
            if (category != null) {
                mPresenter.loadToLabel(category, 1, false, false);
            }
            Logger.d("FUCK", "start");
        } else {
            Logger.d("FUCK", "restart");
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

    @Override
    public void onPause() {
        super.onPause();
        getArguments().putString("save", "save");
    }

    /**
     * init event
     */
    private void initEvent() {
        mScrollChildSwipeRefreshLayout.setOnRefreshListener(this);
        imageAdapter.setOnItemClickListener(this);
    }

    /**
     * init views
     *
     * @param view
     */
    private void initView(View view) {
        mErrorLinearLayout = (LinearLayout) view.findViewById(R.id.linear_error_layout);
        TextView mTvReload = (TextView) view.findViewById(R.id.tv_reload);

        //if there some error happened  use this to reload
        mTvReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMore(1, true, false);
            }
        });
        RecyclerView mImageRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        StaggeredGridLayoutManager mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mStaggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        mImageRecyclerView.setLayoutManager(mStaggeredGridLayoutManager);
        mImageRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mImageRecyclerView.setAdapter(imageAdapter);
        imageAdapter.setOnLoadMoreListener(this, mImageRecyclerView);
        imageAdapter.openLoadAnimation();
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
    }


    @Override
    public synchronized void showImages(List<ImageEntity> list, boolean refresh, boolean loadMore) {
        if (refresh) {
            imageAdapter.setNewData(list);
        } else {
            imageAdapter.addData(list);
        }
        mErrorLinearLayout.setVisibility(View.GONE);
        imageAdapter.loadMoreComplete();
    }

    @Override
    public void showNoImages() {
        imageAdapter.setEmptyView(R.layout.no_content_layout);
        showMessage(this.getString(R.string.no_content));
    }

    @Override
    public void showLoadingImages(final boolean isActive) {
        mScrollChildSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mScrollChildSwipeRefreshLayout.setRefreshing(isActive);
            }
        });
    }

    @Override
    public void loadingFinish() {
        imageAdapter.loadMoreEnd();
        showMessage(this.getString(R.string.loading_finish));
    }

    @Override
    public void showLoadingError(String errorMsg) {
        if (imageEntityList.size() > 0) {
            imageAdapter.loadMoreFail();
        } else {
            mErrorLinearLayout.setVisibility(View.VISIBLE);
        }
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
        loadMore(1, true, false);
    }

    private void loadMore(int page, boolean refresh, boolean loadMore) {

        if (type == 0) {
            mPresenter.loadHomeImages(page, refresh, loadMore);
            Logger.d("FUCK", "home");
        }
        if (type == 1) {
            mPresenter.loadOthers("pop", page, refresh, loadMore);
            Logger.d("FUCK", "pop");
        }
        if (type == 2) {
            mPresenter.loadOthers("like", page, refresh, loadMore);
            Logger.d("FUCK", "like");
        }
        if (type == 3) {
            mPresenter.loadToLabel(category, page, refresh, loadMore);
            Logger.d("FUCK", category);
        }
    }

    @Override
    public void onLoadMoreRequested() {
        loadMore(++page, false, true);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ImageEntity imageEntity = imageAdapter.getItem(position);
        ImageDetailListActivity.startActivity(getActivity(), imageEntity.getLabelId());
    }
}
