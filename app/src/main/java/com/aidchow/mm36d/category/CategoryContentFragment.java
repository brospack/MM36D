package com.aidchow.mm36d.category;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aidchow.entity.FindFlowerEntity;
import com.aidchow.mm36d.R;
import com.aidchow.mm36d.adpter.CategoryAdapter;
import com.aidchow.mm36d.home.HomeFragment;
import com.aidchow.mm36d.home.HomePresenter;
import com.aidchow.mm36d.ui.widget.ScrollChildSwipeRefreshLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

public class CategoryContentFragment extends Fragment implements CategoryContract.View, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener {

    public static CategoryContentFragment newInstance() {
        return new CategoryContentFragment();
    }

    private CategoryContract.Presenter mPresenter;
    private CategoryAdapter adapter;
    private List<FindFlowerEntity> findFlowerEntities;
    private LinearLayout mErrorLinearLayout;
    private ScrollChildSwipeRefreshLayout mScrollChildSwipeRefreshLayout;
    private int page = 1;
    private boolean refresh = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mPresenter == null) {
            new CategoryPresenter(this);
        }
        findFlowerEntities = new ArrayList<>(0);
        adapter = new CategoryAdapter(findFlowerEntities);

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category_content, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initEvent();
    }

    private void initView(View view) {
        mErrorLinearLayout = (LinearLayout) view.findViewById(R.id.linear_error_layout);
        TextView mTvReload = (TextView) view.findViewById(R.id.tv_reload);

        //if there some error happened  use this to reload
        mTvReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getLabels(1, true, false);
            }
        });

        RecyclerView mCategoryRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        StaggeredGridLayoutManager mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        mStaggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager
                .GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        mCategoryRecyclerView.setLayoutManager(mStaggeredGridLayoutManager);
        mCategoryRecyclerView.setAdapter(adapter);
        adapter.setOnLoadMoreListener(this, mCategoryRecyclerView);

        mScrollChildSwipeRefreshLayout = (ScrollChildSwipeRefreshLayout)
                view.findViewById(R.id.swipe_refresh);
        mScrollChildSwipeRefreshLayout.setScrollUpChild(mCategoryRecyclerView);
        mScrollChildSwipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
    }

    private void initEvent() {
        mScrollChildSwipeRefreshLayout.setOnRefreshListener(this);
        adapter.setOnItemClickListener(this);
    }


    @Override
    public void setPresenter(CategoryContract.Presenter presenter) {
        if (presenter != null) {
            mPresenter = presenter;
        }
    }

    @Override
    public void showCategories(List<FindFlowerEntity> list, boolean refresh, boolean loadMore) {
        mErrorLinearLayout.setVisibility(View.GONE);
        if (refresh) {
            adapter.setNewData(list);
        } else {
            adapter.addData(list);

        }
        adapter.loadMoreComplete();
    }

    @Override
    public void showNoCategories() {
        adapter.setEmptyView(R.layout.no_content_layout);
        mErrorLinearLayout.setVisibility(View.GONE);
    }

    @Override
    public void showLoadingCategories(final boolean isActive) {
        final ScrollChildSwipeRefreshLayout mScrollChildSwipeRefreshLayout = (ScrollChildSwipeRefreshLayout)
                getView().findViewById(R.id.swipe_refresh);
        mScrollChildSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mScrollChildSwipeRefreshLayout.setRefreshing(isActive);
            }
        });
    }

    @Override
    public void loadingFinish() {
        adapter.loadMoreEnd();
        showMessage(this.getString(R.string.loading_finish));
    }

    @Override
    public void showLoadingError(String errorMsg) {
        if (findFlowerEntities.size() > 0) {
            adapter.loadMoreFail();
        } else {
            mErrorLinearLayout.setVisibility(View.VISIBLE);
        }
        showMessage(errorMsg);
    }

    private void showMessage(String errorMsg) {
        Snackbar.make(getView(), errorMsg, Snackbar.LENGTH_LONG).show();
    }


    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void onRefresh() {
        mPresenter.getLabels(1, true, false);
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.getLabels(++page, false, true);
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        FindFlowerEntity findFlowerEntity = this.adapter.getItem(position);
        Intent intent = new Intent("to_label");
        intent.putExtra("category", findFlowerEntity.getCategory());
        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
        HomeFragment homeFragment = HomeFragment.newInstance(HomeFragment.HOME_TYPE.LBEL,
                findFlowerEntity.getToLabel());
        new HomePresenter(homeFragment, HomeFragment.HOME_TYPE.LBEL);
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame_content, homeFragment)
                .hide(this)
                .addToBackStack("TO_LABEL")
                .commit();
    }
}
