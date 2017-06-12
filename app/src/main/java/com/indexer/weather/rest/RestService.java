package com.indexer.weather.rest;

import com.indexer.weather.base.Config;
import com.indexer.weather.model.WeatherData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface RestService {
  @Headers("x-api-key: " + Config.API_KEY)
  @GET(Config.LOCATION_URL)
  Call<WeatherData> getWeatherForLocation(@Query("lat") double latitude,
      @Query("lon") double longitude);

  @Headers("x-api-key: " + Config.API_KEY)
  @GET(Config.LOCATION_FORECAST)
  Call<WeatherData> getWeatherForecastLocation(@Query("lat") double latitude,
      @Query("lon") double longitude, @Query("app_id") String appId);
}




