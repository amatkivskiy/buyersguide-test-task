package com.amatkivskiy.buyersguide.ui.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.amatkivskiy.buyersguide.R;
import com.amatkivskiy.buyersguide.model.Car;

public class AllCarsOptionsDialogFragment extends DialogFragment {

  public interface OnAddCarToFavouritesListener {
    void OnAddCarToFavourites(Car car);
  }
  public interface OnOpenCarListener {
    void onOpen(Car car);
  }

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

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    String[] options = new String[]{
        getString(R.string.text_open_option),
        getString(R.string.text_add_to_favourites_option)
    };

    final Car selectedCar = getArguments().getParcelable(SELECTED_CAR_EXTRA);

    final OnAddCarToFavouritesListener favCallback =
        (OnAddCarToFavouritesListener) getTargetFragment();
    final OnOpenCarListener openCallback =
        (OnOpenCarListener) getTargetFragment();

    return new AlertDialog.Builder(getActivity())
        .setTitle(getString(R.string.text_options))
        .setItems(options,
                  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      switch (which) {
                        case 0:
                          openCallback.onOpen(selectedCar);
                          break;
                        case 1:
                          favCallback.OnAddCarToFavourites(selectedCar);
                          break;
                      }
                    }
                  })
        .create();
  }
}
