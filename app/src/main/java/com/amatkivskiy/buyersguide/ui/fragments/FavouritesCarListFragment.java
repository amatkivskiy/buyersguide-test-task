package com.amatkivskiy.buyersguide.ui.fragments;

import com.amatkivskiy.buyersguide.model.Car;
import com.amatkivskiy.buyersguide.util.DbUtils;
import com.amatkivskiy.buyersguide.util.Prefs;

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
    Prefs prefs = Prefs.with(getActivity());

    if (prefs.getFavourites().size() == 0) {
      return;
    }

    String query = DbUtils.getFavouritesQuery(prefs.getFavourites());

    Query.many(Car.class, query)
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
