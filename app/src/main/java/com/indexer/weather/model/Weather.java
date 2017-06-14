package com.indexer.weather.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Weather {

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
}
