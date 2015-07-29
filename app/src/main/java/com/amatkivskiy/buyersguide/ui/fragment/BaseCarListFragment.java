package com.amatkivskiy.buyersguide.ui.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amatkivskiy.buyersguide.ui.activity.CarDetailsActivity;
import com.amatkivskiy.buyersguide.R;
import com.amatkivskiy.buyersguide.model.Car;
import com.amatkivskiy.buyersguide.ui.adapter.CarsAdapter;
import com.amatkivskiy.buyersguide.ui.view.EmptyRecyclerView;

import butterknife.Bind;
import butterknife.ButterKnife;

public abstract class BaseCarListFragment extends Fragment implements
                                                           CarsAdapter.OnItemClickListener,
                                                           CarsAdapter.OnItemLongClickListener {

  @Bind(R.id.cars_recycler)
  EmptyRecyclerView recyclerView;
  @Bind(R.id.empty_text)
  TextView emptyText;
  @Bind(R.id.refresh_layout)
  SwipeRefreshLayout swipeRefreshLayout;

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

  protected String getEmptyText() {
    return "";
  }

  protected void setEmptyText(String text) {
    emptyText.setText(text);
    recyclerView.setEmptyView(emptyText);
  }

  @Override
  public void onItemClicked(int position) {
    Car selected = getAdapter().getItems().get(position);
    Intent intent = CarDetailsActivity.getStartIntent(getActivity(), selected);

    startActivity(intent);
  }

  @Override
  public boolean onItemLongClicked(int position) {
    return false;
  }

  public CarsAdapter getAdapter() {
    return mAdapter;
  }

  public SwipeRefreshLayout getRefresher() {
    return swipeRefreshLayout;
  }

  protected void setRefreshing(boolean isRefreshing) {
    swipeRefreshLayout.setRefreshing(isRefreshing);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_cars_list, container, false);

    ButterKnife.bind(this, root);

    mAdapter = new CarsAdapter(getActivity(), this, this);
    recyclerView.setAdapter(mAdapter);

    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    if (isRefreshEnabled()) {
      recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
          super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
          int topRowVerticalPosition =
              (recyclerView == null
               || recyclerView.getChildCount() == 0) ? 0
                                                     : recyclerView
                  .getChildAt(0).getTop();
          swipeRefreshLayout.setEnabled(topRowVerticalPosition >= 0);
        }
      });
      swipeRefreshLayout.setOnRefreshListener(defaultRefreshListener);
      swipeRefreshLayout.setEnabled(true);
    } else {
      swipeRefreshLayout.setEnabled(false);
    }

    swipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW);
    swipeRefreshLayout.setProgressViewOffset(false, 0,
                                             (int) TypedValue
                                                 .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24,
                                                                 this.getResources()
                                                                     .getDisplayMetrics()));

    setEmptyText(getEmptyText());

    return root;
  }

  @Override
  public void onResume() {
    super.onResume();

    initDataSet();
  }
}
