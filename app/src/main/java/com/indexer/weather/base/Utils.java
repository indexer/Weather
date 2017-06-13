package com.indexer.weather.base;

import android.content.Context;
import com.indexer.weather.R;

/**
 * Created by indexer on 13/6/17.
 */

public class Utils {
  public static String getFormattedWind(Context context, float windSpeed, float degrees) {
    int windFormat;
    windFormat = R.string.format_wind_kmh;
    // From wind direction in degrees, determine compass direction as a string (e.g NW)
    // You know what's fun, writing really long if/else statements with tons of possible
    // conditions.  Seriously, try it!
    String direction = "Unknown";
    if (degrees >= 337.5 || degrees < 22.5) {
      direction = "N";
    } else if (degrees >= 22.5 && degrees < 67.5) {
      direction = "NE";
    } else if (degrees >= 67.5 && degrees < 112.5) {
      direction = "E";
    } else if (degrees >= 112.5 && degrees < 157.5) {
      direction = "SE";
    } else if (degrees >= 157.5 && degrees < 202.5) {
      direction = "S";
    } else if (degrees >= 202.5 && degrees < 247.5) {
      direction = "SW";
    } else if (degrees >= 247.5 && degrees < 292.5) {
      direction = "W";
    } else if (degrees >= 292.5 || degrees < 22.5) {
      direction = "NW";
    }
    return String.format(context.getString(windFormat), windSpeed, direction);
  }
}
