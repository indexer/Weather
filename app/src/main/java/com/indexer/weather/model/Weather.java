package com.indexer.weather.model;

import android.content.SharedPreferences;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Weather implements Serializable {
  SharedPreferences.Editor save_cach;
  SharedPreferences read_cach;

  private static Weather mInstance = null;

  private Weather() {
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
