package com.amatkivskiy.buyersguide.ui.fragments;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.widget.Toast;

import com.amatkivskiy.buyersguide.CarDetailsActivity;
import com.amatkivskiy.buyersguide.R;
import com.amatkivskiy.buyersguide.model.Car;
import com.amatkivskiy.buyersguide.model.CarResponse;
import com.amatkivskiy.buyersguide.network.ApiHelper;
import com.amatkivskiy.buyersguide.ui.fragments.AllCarsOptionsDialogFragment.OnAddCarToFavouritesListener;
import com.amatkivskiy.buyersguide.ui.fragments.AllCarsOptionsDialogFragment.OnOpenCarListener;
import com.amatkivskiy.buyersguide.util.Prefs;

import se.emilsjolander.sprinkles.CursorList;
import se.emilsjolander.sprinkles.ManyQuery;
import se.emilsjolander.sprinkles.Query;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AllCarsListFragment extends BaseCarListFragment implements
                                                             OnAddCarToFavouritesListener,
                                                             OnOpenCarListener {

  @Override
  protected String getEmptyText() {
    return getString(R.string.text_no_cars);
  }

  @Override
  public void OnAddCarToFavourites(Car car) {
    Prefs.with(getActivity()).addToFavourites(String.valueOf(car.getCarId()));

    String text =
        String.format(getString(R.string.text_successfully_added_favourites), car.getName());
    Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
  }

  @Override
  public void onOpen(Car car) {
    Intent intent = CarDetailsActivity.getStartIntent(getActivity(), car);
    startActivity(intent);
  }

  @Override
  protected boolean isRefreshEnabled() {
    return true;
  }

  @Override
  public boolean onItemLongClicked(int position) {
    Car selected = getAdapter().getItems().get(position);

    AllCarsOptionsDialogFragment fragment = AllCarsOptionsDialogFragment.newInstance(selected);
    fragment.setTargetFragment(this, 1);
    fragment.show(getActivity().getFragmentManager(), "dialog");

    return false;
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
        if (getActivity() != null) {
          Snackbar
              .make(getView(), getString(R.string.text_error_check_internet), Snackbar.LENGTH_SHORT)
              .show(); // Don’t forget to show!
        }
      }
    });
  }

  @Override
  protected void initDataSet() {
    Query.all(Car.class).getAsync(getLoaderManager(), new ManyQuery.ResultHandler<Car>() {
      @Override
      public boolean handleResult(CursorList<Car> cursorList) {
        if (cursorList.size() == 0) {
          handleRefresh();
        }

        getAdapter().setItems(cursorList.asList());
        cursorList.close();

        return true;
      }
    }, Car.class);
  }
}