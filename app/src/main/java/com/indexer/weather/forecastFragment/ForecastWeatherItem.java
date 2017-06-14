package com.indexer.weather.forecastFragment;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import com.indexer.weather.main.MainActivity;
import com.indexer.weather.model.ForecastReturnObject;
import com.indexer.weather.rest.RestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by indexer on 13/6/17.
 */

public class ForecastWeatherItem implements ForecastWeatherPresenter {
  ForecastWeatherView forecastWeatherItemView;

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
          forecastWeatherItemView.getWeatherList(response.body().getList());
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
