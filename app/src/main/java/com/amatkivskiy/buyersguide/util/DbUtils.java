package com.amatkivskiy.buyersguide.util;

import com.amatkivskiy.buyersguide.Constants.Database;

import java.util.Iterator;
import java.util.Set;

public class DbUtils {

  public static String getFavouritesQuery(Set<String> ids) {
    StringBuilder idsString = new StringBuilder();

    Iterator<String> idsIterator = ids.iterator();

    while (idsIterator.hasNext()) {
      idsString.append(idsIterator.next());
      if (idsIterator.hasNext()) {
        idsString.append(",");
      }
    }

    return String.format(Database.SelectFavouritesQuery, idsString.toString());
  }

}
