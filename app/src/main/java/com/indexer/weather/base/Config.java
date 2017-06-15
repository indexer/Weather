package com.indexer.weather.base;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by indexer on 3/21/16.
 */
public class Config {
  //Preferences TAG
  public static final String BASE_URL = "http://api.openweathermap.org";
  public static final String LAST_LATITUDE = "last_latitude";
  public static final String LAST_LONGITUDE = "last_longitude";
  public static final String LOCATION_URL = "/data/2.5/weather";
  //http://api.openweathermap.org/data/2.5/forecast/daily?q=London&units=metric&cnt=7
  public static final String LOCATION_FORECAST = "/data/2.5/forecast/daily";
  public static final String API_KEY = "c439d507efe743e30d330569ee3ac15a";
  public final static String USER_INFO = "user_info";
  public final static String no_internet = "no_internet";

  //user's profile fields
  public static String USER_NAME = "user_name";
  public static String USER_BDAY = "user_bday";
  public static String USER_EMAIL = "user_email";
  public static String USER_PFILE = "user_profile";
  public static String unit = "metric";

  //weather cache
  public static String weather_cache = "weather";
  public static String weather_list="weather_list";
}

