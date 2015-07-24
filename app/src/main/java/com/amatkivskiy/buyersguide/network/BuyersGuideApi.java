package com.amatkivskiy.buyersguide.network;

import com.amatkivskiy.buyersguide.model.CarResponse;

import retrofit.Callback;
import retrofit.http.GET;

public interface BuyersGuideApi {
  @GET("/api/feed/?mode=json&q=make")
  void getCars(Callback<CarResponse> callback);
}
