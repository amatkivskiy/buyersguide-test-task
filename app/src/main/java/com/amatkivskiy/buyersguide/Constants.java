package com.amatkivskiy.buyersguide;

public class Constants {
  public static final String AppTag = "Buyers Guide";
  public static class Database {

    public final static String CarsTableName = "Cars";
    public final static String CarIdColumn = "carId";
    public final static String CarNameColumn = "name";
    public final static String CarMakeIconColumn = "makeIcon";
    public final static String CarUrlColumn = "url";

    public final static String CarTableCreationQuery = "CREATE TABLE " + CarsTableName +
                                                       " (" +
                                                       CarIdColumn + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                       CarNameColumn + " TEXT," +
                                                       CarMakeIconColumn + " TEXT," +
                                                       CarUrlColumn + " TEXT," +
                                                       ")";
  }

}
