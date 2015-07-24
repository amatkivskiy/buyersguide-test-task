package com.amatkivskiy.buyersguide.model;

import com.google.gson.annotations.SerializedName;

import com.amatkivskiy.buyersguide.Constants.Database;

import se.emilsjolander.sprinkles.Model;
import se.emilsjolander.sprinkles.annotations.AutoIncrement;
import se.emilsjolander.sprinkles.annotations.Column;
import se.emilsjolander.sprinkles.annotations.Key;
import se.emilsjolander.sprinkles.annotations.Table;

@Table(Database.CarsTableName)
public class Car extends Model{
  @Key
  @AutoIncrement
  @Column(Database.CarIdColumn)
  private int carId;

  @Column(Database.CarNameColumn)
  private String name;

  @SerializedName("make_icon")
  @Column(Database.CarMakeIconColumn)
  private String makeIcon;
  @Column(Database.CarUrlColumn)
  private String url;

  public int getCarId() {
    return carId;
  }

  public String getName() {
    return name;
  }

  public String getMakeIcon() {
    return makeIcon;
  }

  public String getUrl() {
    return url;
  }
}
