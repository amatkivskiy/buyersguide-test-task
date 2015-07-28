package com.amatkivskiy.buyersguide.ui.fragment;

import android.widget.Toast;

import com.amatkivskiy.buyersguide.ui.activity.MainActivity.OnSearchActionsListener;
import com.amatkivskiy.buyersguide.R;
import com.amatkivskiy.buyersguide.model.Car;
import com.amatkivskiy.buyersguide.util.DbUtils;
import com.amatkivskiy.buyersguide.util.ToolbarSearchView;
import com.amatkivskiy.buyersguide.util.Prefs;

import se.emilsjolander.sprinkles.CursorList;
import se.emilsjolander.sprinkles.ManyQuery;
import se.emilsjolander.sprinkles.Query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.amatkivskiy.buyersguide.ui.fragment.FavouritesCarsOptionsDialogFragment.OnRemoveFavouriteListener;
import static com.amatkivskiy.buyersguide.ui.fragment.FavouritesCarsOptionsDialogFragment.newInstance;

public class FavouritesCarListFragment extends BaseCarListFragment implements
                                                                   OnRemoveFavouriteListener,
                                                                   OnSearchActionsListener {
  private List<Car> originalCars = Collections.emptyList();

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
            originalCars = cursorList.asList();

            getAdapter().setItems(cursorList.asList());
            cursorList.close();

            return false;
          }
        }, Car.class);
  }

  private void performFiltering(String query) {
    if (query.isEmpty()) {
      getAdapter().setItems(originalCars);
    }

    List<Car> filtered = new ArrayList<>();

    for (Car car : originalCars) {
      if (car.getName().toLowerCase().contains(query)) {
        filtered.add(car);
      }
    }

    getAdapter().setItems(filtered);
  }

  @Override
  public void onCancelSearch() {
    this.performFiltering("");
  }

  @Override
  public void onSearch(ToolbarSearchView toolbarSearchView, CharSequence constraint) {
    this.performFiltering(constraint.toString());
  }

  @Override
  public void onSearchHint(ToolbarSearchView toolbarSearchView, CharSequence constraint) {
  }
}
