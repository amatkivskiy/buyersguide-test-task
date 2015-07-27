package com.amatkivskiy.buyersguide.ui.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.amatkivskiy.buyersguide.CarDetailsActivity;
import com.amatkivskiy.buyersguide.R;
import com.amatkivskiy.buyersguide.model.Car;
import com.amatkivskiy.buyersguide.util.Prefs;

public class AllCarsOptionsDialogFragment extends DialogFragment {

  public static final String SELECTED_CAR_EXTRA = "selected_car";

  public AllCarsOptionsDialogFragment() {
  }

  public static AllCarsOptionsDialogFragment newInstance(Car selected) {
    AllCarsOptionsDialogFragment fragment = new AllCarsOptionsDialogFragment();

    Bundle data = new Bundle();
    data.putParcelable(SELECTED_CAR_EXTRA, selected);
    fragment.setArguments(data);

    return fragment;
  }

  private void openCarDetailsActivity(Car car) {
    Intent intent = CarDetailsActivity.getStartIntent(getActivity(), car);
    startActivity(intent);
  }

  private void addCarToFavourites(Car car) {
    Prefs.with(getActivity()).addToFavourites(String.valueOf(car.getCarId()));

    String text =
        String.format(getString(R.string.text_successfully_added_favourites), car.getName());
    Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    String[] options = new String[]{
        getString(R.string.text_open_option),
        getString(R.string.text_add_to_favourites_option)
    };

    final Car selectedCar = getArguments().getParcelable(SELECTED_CAR_EXTRA);

    return new AlertDialog.Builder(getActivity())
        .setTitle(getString(R.string.text_options))
        .setItems(options,
                  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      switch (which) {
                        case 0:
                          openCarDetailsActivity(selectedCar);
                          break;
                        case 1:
                          addCarToFavourites(selectedCar);
                          break;
                      }
                    }
                  })
        .create();
  }
}
