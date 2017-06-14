package com.indexer.weather.forecastFragment;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.text.format.Time;
import android.util.Log;
import com.indexer.weather.base.Config;
import com.indexer.weather.main.MainActivity;
import com.indexer.weather.model.ForecastReturnObject;
import com.indexer.weather.model.Weather;
import com.indexer.weather.rest.RestClient;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by indexer on 13/6/17.
 */

public class ForecastWeatherItem implements ForecastWeatherPresenter {
  private ForecastWeatherView forecastWeatherItemView;
  private long dateTime;
  private ArrayList<Weather> weatherArrayList = new ArrayList<>();

  public ForecastWeatherItem(ForecastWeatherView forecastWeatherItemView) {
    this.forecastWeatherItemView = forecastWeatherItemView;
  }

  @Override public void resume() {

  }

  @Override public void pause() {

  }

  @Override public void start() {

  }

  @Override public void stop() {

  }

  @Override public void destroy() {

  }

  @Override public void getWeatherForecast(Activity mainActivity, int day) {
    SharedPreferences mSharedPreferences = PreferenceManager.
        getDefaultSharedPreferences(mainActivity.getApplicationContext());
    float lat = mSharedPreferences.getFloat(Config.LAST_LATITUDE, 0);
    float lng = mSharedPreferences.getFloat(Config.LAST_LONGITUDE, 0);

    Call<ForecastReturnObject> weatherReturnObjectCall =
        RestClient.getService(mainActivity)
            .getWeatherForecastLocation(lat, lng, day, Config.unit);
    weatherReturnObjectCall.enqueue(new Callback<ForecastReturnObject>() {
      @Override public void onResponse(@NonNull Call<ForecastReturnObject> call,
          @NonNull Response<ForecastReturnObject> response) {
        if (response.isSuccessful()) {
          Time dayTime = new Time();
          dayTime.setToNow();
          // we start at the day returned by local time. Otherwise this is a mess.
          int julianStartDay = Time.getJulianDay(System.currentTimeMillis(), dayTime.gmtoff);
          // now we work exclusively in UTC
          dayTime = new Time();
          int i = 0;
          while (i < response.body().getList().size()) {
            Weather mWeather;
            mWeather = response.body().getList().get(i).getWeather().get(0);
            dateTime = dayTime.setJulianDay(julianStartDay + i);
            mWeather.date = dateTime;
            mWeather.city = response.body().getCity().getName();
            mWeather.temp = response.body().getList().get(i).getTemp().getDay();
            mWeather.humidity = response.body().getList().get(i).getHumidity();
            mWeather.speed = response.body().getList().get(i).getSpeed();
            weatherArrayList.add(mWeather);
            i++;
          }
          forecastWeatherItemView.getWeatherList(weatherArrayList);
        }
      }

      @Override
      public void onFailure(@NonNull Call<ForecastReturnObject> call, @NonNull Throwable t) {
        Log.e("Responze error", "=" + t.getMessage());
      }
    });
  }

  @Override public void onError(String message) {

  }
}
