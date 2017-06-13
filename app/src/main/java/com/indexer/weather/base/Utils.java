package com.indexer.weather.base;

import android.content.Context;
import android.text.format.Time;
import com.indexer.weather.R;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Created by indexer on 13/6/17.
 */

public class Utils {
  public static final String DATE_FORMAT = "yyyyMMdd";

  public static String getDayName(Context context, long dateInMillis) {
    // If the date is today, return the localized version of "Today" instead of the actual
    // day name.
    Time t = new Time();
    t.setToNow();
    int julianDay = Time.getJulianDay(dateInMillis, t.gmtoff);
    int currentJulianDay = Time.getJulianDay(System.currentTimeMillis(), t.gmtoff);
    if (julianDay == currentJulianDay) {
      return context.getString(R.string.today);
    } else if (julianDay == currentJulianDay + 1) {
      return context.getString(R.string.tomorrow);
    } else {
      Time time = new Time();
      time.setToNow();
      // Otherwise, the format is just the day of the week (e.g "Wednesday".
      SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
      return dayFormat.format(dateInMillis);
    }
  }

  public static String getFriendlyDayString(Context context, long dateInMillis) {
    // The day string for forecast uses the following logic:
    // For today: "Today, June 8"
    // For tomorrow:  "Tomorrow"
    // For the next 5 days: "Wednesday" (just the day name)
    // For all days after that: "Mon Jun 8"

    Time time = new Time();
    time.setToNow();
    long currentTime = System.currentTimeMillis();
    int julianDay = Time.getJulianDay(dateInMillis, time.gmtoff);
    int currentJulianDay = Time.getJulianDay(currentTime, time.gmtoff);

    // If the date we're building the String for is today's date, the format
    // is "Today, June 24"
    if (julianDay == currentJulianDay) {
      String today = context.getString(R.string.today);
      int formatId = R.string.format_full_friendly_date;
      return context.getString(formatId, today, getFormattedMonthDay(context, dateInMillis));
    } else if (julianDay < currentJulianDay + 7) {
      // If the input date is less than a week in the future, just return the day name.
      return getDayName(context, dateInMillis);
    } else {
      // Otherwise, use the form "Mon Jun 3"
      SimpleDateFormat shortenedDateFormat = new SimpleDateFormat("EEE MMM dd");
      return shortenedDateFormat.format(dateInMillis);
    }
  }

  public static String getFormattedMonthDay(Context context, long dateInMillis) {
    Time time = new Time();
    time.setToNow();
    SimpleDateFormat dbDateFormat = new SimpleDateFormat(Utils.DATE_FORMAT);
    SimpleDateFormat monthDayFormat = new SimpleDateFormat("MMMM dd");
    String monthDayString = monthDayFormat.format(dateInMillis);
    return monthDayString;
  }

  public static String getFormattedHumidity(Context context, double humidity) {
    return context.getString(R.string.format_humidity, humidity);
  }

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
