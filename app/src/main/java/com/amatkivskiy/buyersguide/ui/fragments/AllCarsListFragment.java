package com.amatkivskiy.buyersguide.ui.fragments;

import com.amatkivskiy.buyersguide.model.Car;
import com.amatkivskiy.buyersguide.model.CarResponse;
import com.amatkivskiy.buyersguide.network.ApiHelper;

import se.emilsjolander.sprinkles.CursorList;
import se.emilsjolander.sprinkles.ManyQuery;
import se.emilsjolander.sprinkles.Query;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AllCarsListFragment extends BaseCarListFragment {

  @Override
  protected boolean isRefreshEnabled() {
    return true;
  }

  @Override
  protected void handleRefresh() {
    ApiHelper.getCars(new Callback<CarResponse>() {
      @Override
      public void success(CarResponse carResponse, Response response) {
        setRefreshing(false);
      }

      @Override
      public void failure(RetrofitError error) {
        setRefreshing(false);
      }
    });
  }

  @Override
  protected void initDataSet() {
    Query.all(Car.class).getAsync(getLoaderManager(), new ManyQuery.ResultHandler<Car>() {
      @Override
      public boolean handleResult(CursorList<Car> cursorList) {
        getAdapter().setItems(cursorList.asList());
        cursorList.close();

        return true;
      }
    }, Car.class);
  }
}