package com.indexer.weather.rest;

import com.indexer.weather.base.Config;
import com.indexer.weather.model.ForecastReturnObject;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface RestService {
  @Headers("x-api-key: " + Config.API_KEY)
  @GET(Config.LOCATION_URL)
  Call<ForecastReturnObject> getWeatherForLocation(@Query("lat") double latitude,
      @Query("lon") double longitude);

  @Headers("x-api-key: " + Config.API_KEY)
  @GET(Config.LOCATION_FORECAST)
  Call<ForecastReturnObject> getWeatherForecastLocation(@Query("lat") double latitude,
      @Query("lon") double longitude, @Query("cnt") int cnt,@Query("units") String units);
}




