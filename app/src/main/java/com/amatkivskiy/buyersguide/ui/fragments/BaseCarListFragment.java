package com.amatkivskiy.buyersguide.ui.fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amatkivskiy.buyersguide.R;
import com.amatkivskiy.buyersguide.ui.adapters.CarsAdapter;
import com.amatkivskiy.buyersguide.ui.views.EmptyRecyclerView;

public abstract class BaseCarListFragment extends Fragment implements
                                                           CarsAdapter.OnItemClickListener,
                                                           CarsAdapter.OnItemLongClickListener {

  protected EmptyRecyclerView mRecyclerView;
  protected SwipeRefreshLayout swipeRefreshLayout;
  private CarsAdapter mAdapter;

  private OnRefreshListener defaultRefreshListener = new OnRefreshListener() {
    @Override
    public void onRefresh() {
      handleRefresh();
    }
  };

  protected abstract boolean isRefreshEnabled();

  protected abstract void initDataSet();

  protected void handleRefresh() {
  }

  @Override
  public void onItemClicked(int position) {
  }

  @Override
  public boolean onItemLongClicked(int position) {
    return false;
  }

  public CarsAdapter getAdapter() {
    return mAdapter;
  }

  protected void setRefreshing(boolean isRefreshing) {
    swipeRefreshLayout.setRefreshing(isRefreshing);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_cars_list, container, false);

    mRecyclerView = (EmptyRecyclerView) root.findViewById(R.id.cars_recycler);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    mAdapter = new CarsAdapter(getActivity(), this, this);
    mRecyclerView.setAdapter(mAdapter);

    swipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.refresh_layout);
    swipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW);
    swipeRefreshLayout.setProgressViewOffset(false, 0,
                                             (int) TypedValue
                                                 .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24,
                                                                 this.getResources()
                                                                     .getDisplayMetrics()));
    if (isRefreshEnabled()) {
      swipeRefreshLayout.setOnRefreshListener(defaultRefreshListener);
      swipeRefreshLayout.setEnabled(true);
    } else {
      swipeRefreshLayout.setEnabled(false);
    }

    return root;
  }

  @Override
  public void onResume() {
    super.onResume();

    initDataSet();
  }
}
