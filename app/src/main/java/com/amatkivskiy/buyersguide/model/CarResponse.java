package com.amatkivskiy.buyersguide.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CarResponse {

  private int count;

  @SerializedName("data")
  private List<Car> cars;

  public int getCount() {
    return count;
  }

  public List<Car> getCars() {
    return cars;
  }
}