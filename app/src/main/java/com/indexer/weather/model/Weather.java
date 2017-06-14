package com.indexer.weather.model;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.indexer.weather.base.Config;
import java.io.Serializable;

public class Weather implements Serializable {
  SharedPreferences.Editor save_cach;
  SharedPreferences read_cach;

  private static Weather mInstance = null;

  private Weather() {
  }

  public static Weather getInstance() {
    if (mInstance == null) {
      mInstance = new Weather();
    }
    return mInstance;
  }

  public void saveWeatherCach(Context context) {
    save_cach = context.getSharedPreferences(Config.USER_INFO, Context.MODE_PRIVATE).edit();
    save_cach.putString(Config.USER_CITY, city);
    save_cach.putString(Config.USER_CITY_TEMP, temp.toString());
    save_cach.putString(Config.USER_SPEED, speed.toString());
    save_cach.putInt(Config.USER_HUMIDITY, humidity);
    save_cach.putString(Config.USER_ICON, icon);
    save_cach.putFloat(Config.USER_DEGREE, degree);
    save_cach.putLong(Config.USER_DATE, date);
    save_cach.putString(Config.USER_MAIN, main);
    save_cach.putString(Config.USER_DESCRIPTION, description);
    save_cach.commit();
  }

  public void readWeatherCach(Context context) {
    read_cach = context.getSharedPreferences(Config.USER_INFO, Context.MODE_PRIVATE);
    main = read_cach.getString(Config.USER_MAIN, "NONE");
    icon = read_cach.getString(Config.USER_ICON, "NONE");
    date = read_cach.getLong(Config.USER_DATE, 0);
    degree = read_cach.getFloat(Config.USER_DEGREE, 0);
    speed = Double.parseDouble(read_cach.getString(Config.USER_SPEED, "0"));
    temp = Double.parseDouble(read_cach.getString(Config.USER_CITY_TEMP, "0"));
    description = read_cach.getString(Config.USER_DESCRIPTION, "NONE");
    city = read_cach.getString(Config.USER_CITY, "NONE");
    humidity = read_cach.getInt(Config.USER_HUMIDITY, 0);
  }

  @SerializedName("id")
  @Expose
  public Integer id;
  @SerializedName("main")
  @Expose
  public String main;
  @SerializedName("description")
  @Expose
  public String description;
  @SerializedName("icon")
  @Expose
  public String icon;
  public long date;
  public Double speed;
  public float degree;
  public Double temp;
  public Integer humidity;
  public String city;
}
