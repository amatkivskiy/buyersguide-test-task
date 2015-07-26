package com.amatkivskiy.buyersguide.ui.fragments;

import android.app.Fragment;
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

import com.amatkivskiy.buyersguide.R;
import com.amatkivskiy.buyersguide.model.Car;
import com.amatkivskiy.buyersguide.model.CarResponse;
import com.amatkivskiy.buyersguide.network.ApiHelper;
import com.amatkivskiy.buyersguide.ui.adapters.CarsAdapter;

import se.emilsjolander.sprinkles.CursorList;
import se.emilsjolander.sprinkles.ManyQuery;
import se.emilsjolander.sprinkles.Query;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CarsListFragment extends Fragment {

  private static final String TAG = CarsListFragment.class.getSimpleName();

  protected RecyclerView mRecyclerView;
  protected SwipeRefreshLayout swipeRefreshLayout;
  protected CarsAdapter mAdapter;
  protected OnRefreshListener onRefreshListener = new OnRefreshListener() {
    @Override
    public void onRefresh() {
      ApiHelper.getCars(new Callback<CarResponse>() {
        @Override
        public void success(CarResponse carResponse, Response response) {
          swipeRefreshLayout.setRefreshing(false);
        }

        @Override
        public void failure(RetrofitError error) {
          swipeRefreshLayout.setRefreshing(false);
        }
      });
    }
  };

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_cars_list, container, false);
    rootView.setTag(TAG);

    mRecyclerView = (RecyclerView) rootView.findViewById(R.id.cars_recycler);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    mAdapter = new CarsAdapter(getActivity());
    mRecyclerView.setAdapter(mAdapter);

    swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.refresh_layout);
    swipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW);

    swipeRefreshLayout.setOnRefreshListener(onRefreshListener);

    swipeRefreshLayout.setProgressViewOffset(false, 0,
                                             (int) TypedValue
                                                 .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24,
                                                                 this.getResources()
                                                                     .getDisplayMetrics()));

    return rootView;
  }

  @Override
  public void onResume() {
    super.onResume();

    initCarsList();
  }

  private void initCarsList() {
    Query.all(Car.class).getAsync(getLoaderManager(), new ManyQuery.ResultHandler<Car>() {
      @Override
      public boolean handleResult(CursorList<Car> cursorList) {
        mAdapter.setItems(cursorList.asList());
        cursorList.close();

        return true;
      }
    }, Car.class);
  }
}