package com.amatkivskiy.buyersguide.ui.fragments;

import android.widget.Toast;

import com.amatkivskiy.buyersguide.R;
import com.amatkivskiy.buyersguide.model.Car;
import com.amatkivskiy.buyersguide.util.DbUtils;
import com.amatkivskiy.buyersguide.util.Prefs;

import se.emilsjolander.sprinkles.CursorList;
import se.emilsjolander.sprinkles.ManyQuery;
import se.emilsjolander.sprinkles.Query;

import java.util.Collections;

import static com.amatkivskiy.buyersguide.ui.fragments.FavouritesCarsOptionsDialogFragment.*;

public class FavouritesCarListFragment extends BaseCarListFragment implements
                                                                   OnRemoveFavouriteListener {

  @Override
  protected String getEmptyText() {
    return getString(R.string.text_empty_favourites);
  }

  @Override
  public void onRemoveFromFavourites(Car car) {
    Prefs.with(getActivity()).removeFromFavourites(String.valueOf(car.getCarId()));

    String text =
        String.format(getString(R.string.text_successfully_removed_favourites), car.getName());
    Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();

    initDataSet();
  }

  @Override
  protected boolean isRefreshEnabled() {
    return false;
  }

  @Override
  public boolean onItemLongClicked(int position) {
    Car selected = getAdapter().getItems().get(position);

    FavouritesCarsOptionsDialogFragment
        fragment =
        newInstance(selected);
    fragment.setTargetFragment(this, 1);

    fragment.show(getActivity().getFragmentManager(), "dialog");

    return false;
  }

  @Override
  protected void initDataSet() {
    Prefs prefs = Prefs.with(getActivity());

    if (prefs.getFavourites().size() == 0) {
      getAdapter().setItems(Collections.<Car>emptyList());
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
