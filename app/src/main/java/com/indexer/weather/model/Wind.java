package com.indexer.weather.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Wind {

  @SerializedName("speed")
  @Expose
  public Float speed;
  @SerializedName("deg")
  @Expose
  public Float deg;
}
