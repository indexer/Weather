package com.indexer.weather.main;

import android.support.annotation.NonNull;
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

  MainWeatherInfo(MainView loginView) {
    this.mainView = loginView;
  }

  @Override public void signOut(MainActivity mainActivity) {
    mainView.updateProfile(null);
  }

  @Override public void getWeatherToday(final MainActivity mainActivity) {
    //13.736717, 100.523186
    Call<WeatherData> weatherReturnObjectCall =
        RestClient.getService(mainActivity)
            .getWeatherForLocation(13.736717, 100.523186);
    weatherReturnObjectCall.enqueue(new Callback<WeatherData>() {
      @Override
      public void onResponse(@NonNull Call<WeatherData> call,
          @NonNull Response<WeatherData> response) {
        if (response.isSuccessful()) {
          mainView.updateHeader(response.body());
        }
      }

      @Override public void onFailure(@NonNull Call<WeatherData> call, @NonNull Throwable t) {
        mainView.updateHeader(null);
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
