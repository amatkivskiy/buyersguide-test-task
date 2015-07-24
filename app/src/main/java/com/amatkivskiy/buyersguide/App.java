package com.amatkivskiy.buyersguide;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.amatkivskiy.buyersguide.Constants.Database;

import se.emilsjolander.sprinkles.Migration;
import se.emilsjolander.sprinkles.Sprinkles;

import timber.log.Timber;

public class App extends Application {

  @Override
  public void onCreate() {
    super.onCreate();

    this.initDatabase();
    this.initLogging();
  }

  private void initLogging() {
    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree());
    }
  }

  private void initDatabase() {
    Sprinkles sprinkles = Sprinkles.init(getApplicationContext());

    sprinkles.addMigration(new Migration() {
      @Override
      protected void onPreMigrate() {
      }

      @Override
      protected void doMigration(SQLiteDatabase db) {
        db.execSQL(Database.CarTableCreationQuery);
      }

      @Override
      protected void onPostMigrate() {
      }
    });
  }
}
