package com.indexer.weather.main;

import android.support.annotation.NonNull;
import android.util.Log;
import com.indexer.weather.model.WeatherData;
import com.indexer.weather.rest.RestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by indexer on 13/6/17.
 */

public class MainWeatherInfo implements MainPresenter {
  private MainView mainView;

  public MainWeatherInfo(MainView loginView) {
    this.mainView = loginView;
  }

  @Override public void signOut(MainActivity mainActivity) {
    mainView.updateProfile(null);
  }

  @Override public void getWeatherToday(final MainActivity mainActivity) {
    Call<WeatherData> weatherReturnObjectCall =
        RestClient.getService(mainActivity)
            .getWeatherForLocation(16.799999, 96.150002);
    weatherReturnObjectCall.enqueue(new Callback<WeatherData>() {
      @Override
      public void onResponse(@NonNull Call<WeatherData> call,
          @NonNull Response<WeatherData> response) {
        if (response.isSuccessful()) {
          mainView.updagteHeader(response.body());
        }
      }

      @Override public void onFailure(@NonNull Call<WeatherData> call, @NonNull Throwable t) {
      }
    });
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

  @Override public void onError(String message) {

  }
}
