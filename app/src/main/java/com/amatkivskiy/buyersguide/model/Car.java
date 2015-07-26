package com.amatkivskiy.buyersguide.model;

import com.google.gson.annotations.SerializedName;

import android.os.Parcel;
import android.os.Parcelable;

import com.amatkivskiy.buyersguide.Constants.Database;

import se.emilsjolander.sprinkles.Model;
import se.emilsjolander.sprinkles.annotations.Column;
import se.emilsjolander.sprinkles.annotations.DynamicColumn;
import se.emilsjolander.sprinkles.annotations.Key;
import se.emilsjolander.sprinkles.annotations.Table;

@Table(Database.CarsTableName)
public class Car extends Model implements Parcelable {

  @Key
  @Column(Database.CarIdColumn)
  @com.google.gson.annotations.SerializedName("id")
  private int carId;

  @Column(Database.CarNameColumn)
  private String name;

  @SerializedName("make_icon")
  @Column(Database.CarMakeIconColumn)
  private String makeIcon;

  @Column(Database.CarUrlColumn)
  private String url;

  @DynamicColumn(Database.CarFavouritesColumn)
  private boolean isFavourite;

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

  public boolean isFavourite() {
    return isFavourite;
  }

  public void markFavourite(boolean isFavourite) {
    this.isFavourite = isFavourite;
  }


  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.carId);
    dest.writeString(this.name);
    dest.writeString(this.makeIcon);
    dest.writeString(this.url);
    dest.writeByte(isFavourite ? (byte) 1 : (byte) 0);
  }

  public Car() {
  }

  protected Car(Parcel in) {
    this.carId = in.readInt();
    this.name = in.readString();
    this.makeIcon = in.readString();
    this.url = in.readString();
    this.isFavourite = in.readByte() != 0;
  }

  public static final Parcelable.Creator<Car> CREATOR = new Parcelable.Creator<Car>() {
    public Car createFromParcel(Parcel source) {
      return new Car(source);
    }

    public Car[] newArray(int size) {
      return new Car[size];
    }
  };
}
