package com.amatkivskiy.buyersguide.ui.fragments;

import com.amatkivskiy.buyersguide.Constants.Database;
import com.amatkivskiy.buyersguide.model.Car;

import se.emilsjolander.sprinkles.CursorList;
import se.emilsjolander.sprinkles.ManyQuery;
import se.emilsjolander.sprinkles.Query;

public class FavouritesCarListFragment extends BaseCarListFragment {

  @Override
  protected boolean isRefreshEnabled() {
    return false;
  }

  @Override
  protected void initDataSet() {
    Query.many(Car.class, Database.SelectFavouritesQuery, 1)
        .getAsync(getLoaderManager(), new ManyQuery.ResultHandler<Car>() {
          @Override
          public boolean handleResult(CursorList<Car> cursorList) {
            getAdapter().setItems(cursorList.asList());
            cursorList.close();
            return false;
          }
        }, Car.class);
  }
}
