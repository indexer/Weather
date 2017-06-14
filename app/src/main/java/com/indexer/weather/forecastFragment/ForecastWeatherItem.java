package com.indexer.weather.forecastFragment;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.text.format.Time;
import android.util.Log;
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
  ForecastWeatherView forecastWeatherItemView;
  long dateTime;
  ArrayList<Weather> weatherArrayList = new ArrayList<>();

  ForecastWeatherItem(ForecastWeatherView forecastWeatherItemView) {
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

  @Override public void getWeatherForecast(Activity mainActivity) {
    Call<ForecastReturnObject> weatherReturnObjectCall =
        RestClient.getService(mainActivity)
            .getWeatherForecastLocation(13.736717, 100.523186, 5);
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
          for (int i = 0; i < response.body().getList().size(); i++) {
            Weather mWeather;
            mWeather = (Weather) response.body().getList().get(i).getWeather().get(0);
            dateTime = dayTime.setJulianDay(julianStartDay + i);
            mWeather.date = dateTime;
            mWeather.humidity = response.body().getList().get(i).getHumidity();
            mWeather.speed = response.body().getList().get(i).getSpeed();
            weatherArrayList.add(mWeather);
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
