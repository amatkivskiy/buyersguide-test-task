package com.amatkivskiy.buyersguide.network;

import com.amatkivskiy.buyersguide.BuildConfig;
import com.amatkivskiy.buyersguide.model.CarResponse;

import se.emilsjolander.sprinkles.ModelList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import timber.log.Timber;

public class ApiHelper {

  public static void getCars(Callback<CarResponse> responseCallback) {
    RestAdapter.Builder builder = new RestAdapter.Builder()
        .setEndpoint("http://buyersguide.caranddriver.com");

    if (BuildConfig.DEBUG) {
      builder.setLog(new RestAdapter.Log() {
        @Override
        public void log(String message) {
          Timber.d(message);
        }
      });
    }

    BuyersGuideApi service = builder.build().create(BuyersGuideApi.class);

    service.getCars(wrapCallback(responseCallback));
  }

  private static Callback<CarResponse> wrapCallback(final Callback<CarResponse> inner) {
    return new Callback<CarResponse>() {
      @Override
      public void success(CarResponse carResponse, Response response) {
        Timber.d("Received %d cars from server", carResponse.getCount());

        new ModelList<>(carResponse.getCars()).saveAllAsync();

        if (inner != null) {
          inner.success(carResponse, response);
        }
      }

      @Override
      public void failure(RetrofitError error) {
        if (inner != null) {
          inner.failure(error);
        }
      }
    };
  }
}
