package com.amatkivskiy.buyersguide.ui.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.amatkivskiy.buyersguide.R;
import com.amatkivskiy.buyersguide.model.Car;

public class FavouritesCarsOptionsDialogFragment extends DialogFragment {

  public interface OnRemoveFavouriteListener {
    void onRemoveFromFavourites(Car car);
  }

  public static final String SELECTED_CAR_EXTRA = "selected_car";

  public FavouritesCarsOptionsDialogFragment() {
  }

  public static FavouritesCarsOptionsDialogFragment newInstance(Car selected) {
    FavouritesCarsOptionsDialogFragment fragment = new FavouritesCarsOptionsDialogFragment();

    Bundle data = new Bundle();
    data.putParcelable(SELECTED_CAR_EXTRA, selected);
    fragment.setArguments(data);

    return fragment;
  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    String[] options = new String[]{
        getString(R.string.text_remove_from_favourites_option)
    };

    final Car selectedCar = getArguments().getParcelable(SELECTED_CAR_EXTRA);

    final OnRemoveFavouriteListener callback =
        (OnRemoveFavouriteListener) getTargetFragment();

    return new AlertDialog.Builder(getActivity())
        .setTitle(getString(R.string.text_options))
        .setItems(options,
                  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      callback.onRemoveFromFavourites(selectedCar);
                    }
                  })
        .create();
  }
}
