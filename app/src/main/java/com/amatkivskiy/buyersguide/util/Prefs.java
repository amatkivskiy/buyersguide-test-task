package com.amatkivskiy.buyersguide.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.amatkivskiy.buyersguide.Constants.Preferences.FavouriteCarsKey;

/**
 * @author Alejandro Rodriguez (https://github.com/Alexrs95/Prefs)
 *
 *         Wrapper over the Android Preferences which provides a fluid syntax
 */
public class Prefs {

  static Prefs singleton = null;

  static SharedPreferences preferences;

  static SharedPreferences.Editor editor;

  Prefs(Context context) {
    preferences = PreferenceManager.getDefaultSharedPreferences(context);
    editor = preferences.edit();
  }

  Prefs(Context context, String name, int mode) {
    preferences = context.getSharedPreferences(name, mode);
    editor = preferences.edit();
  }

  public static Prefs with(Context context) {
    if (singleton == null) {
      singleton = new Builder(context, null, -1).build();
    }
    return singleton;
  }

  public static Prefs with(Context context, String name, int mode) {
    if (singleton == null) {
      singleton = new Builder(context, name, mode).build();
    }
    return singleton;
  }

  public void addToFavourites(String cardId) {
    Set<String> ids = getFavourites();
    ids.add(cardId);

    saveFavourites(ids);
  }

  public void removeFromFavourites(String cardId) {
    Set<String> ids = getFavourites();
    ids.remove(cardId);

    saveFavourites(ids);
  }

  public void saveFavourites(Set<String> ids) {
    save(FavouriteCarsKey, ids);
  }

  public Set<String> getFavourites() {
    return getStringSet(FavouriteCarsKey, new HashSet<String>());
  }

  public void save(String key, boolean value) {
    editor.putBoolean(key, value).apply();
  }

  public void save(String key, String value) {
    editor.putString(key, value).apply();
  }

  public void save(String key, int value) {
    editor.putInt(key, value).apply();
  }

  public void save(String key, float value) {
    editor.putFloat(key, value).apply();
  }

  public void save(String key, long value) {
    editor.putLong(key, value).apply();
  }

  public void save(String key, Set<String> value) {
    editor.putStringSet(key, value).apply();
  }

  public boolean getBoolean(String key, boolean defValue) {
    return preferences.getBoolean(key, defValue);
  }

  public String getString(String key, String defValue) {
    return preferences.getString(key, defValue);
  }

  public int getInt(String key, int defValue) {
    return preferences.getInt(key, defValue);
  }

  public float getFloat(String key, float defValue) {
    return preferences.getFloat(key, defValue);
  }

  public long getLong(String key, long defValue) {
    return preferences.getLong(key, defValue);
  }

  public Set<String> getStringSet(String key, Set<String> defValue) {
    return preferences.getStringSet(key, defValue);
  }

  public Map<String, ?> getAll() {
    return preferences.getAll();
  }

  public void remove(String key) {
    editor.remove(key).apply();
  }

  public void removeAll() {
    editor.clear().apply();
  }

  public void contains(String key) {
    preferences.contains(key);
  }

  private static class Builder {

    private final Context context;
    private final int mode;
    private final String name;

    public Builder(Context context, String name, int mode) {
      if (context == null) {
        throw new IllegalArgumentException("Context must not be null.");
      }
      this.context = context.getApplicationContext();
      this.name = name;
      this.mode = mode;
    }

    /**
     * Method that creates an instance of Prefs
     *
     * @return an instance of Prefs
     */
    public Prefs build() {
      if (mode == -1 || name == null) {
        return new Prefs(context);
      }
      return new Prefs(context, name, mode);
    }
  }
}