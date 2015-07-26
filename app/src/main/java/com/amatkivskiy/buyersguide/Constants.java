package com.amatkivskiy.buyersguide;

public class Constants {

  public static class Database {

    public final static String CarsTableName = "cars";
    public final static String CarIdColumn = "carId";
    public final static String CarNameColumn = "name";
    public final static String CarMakeIconColumn = "makeIcon";
    public final static String CarUrlColumn = "url";
    public final static String CarFavouritesColumn = "isFavourite";

    public final static String CarTableCreationQuery =
        "CREATE TABLE " + CarsTableName +
        " (" +
        CarIdColumn + " INTEGER PRIMARY KEY," +
        CarNameColumn + " TEXT," +
        CarMakeIconColumn + " TEXT," +
        CarUrlColumn + " TEXT," +
        CarFavouritesColumn + " INTEGER," +
        "UNIQUE (" + CarIdColumn
        + ") ON CONFLICT REPLACE" +
        ")";

    public final static String SelectFavouritesQuery =
        "select * from " + CarsTableName +
        " where " + CarFavouritesColumn + "=?";
  }
}
