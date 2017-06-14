package com.indexer.weather.main;

import android.support.annotation.NonNull;
import com.indexer.weather.model.ForecastReturnObject;
import com.indexer.weather.model.UserInfo;
import com.indexer.weather.rest.RestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by indexer on 13/6/17.
 */

public class MainWeatherInfo implements MainPresenter {
  private MainView mainView;
  private UserInfo mUserInfo;

  MainWeatherInfo(MainView loginView) {
    this.mainView = loginView;
  }

  @Override public void signOut(MainActivity mainActivity) {
    mainView.updateProfile(null);
    mUserInfo = UserInfo.getInstance();
    mUserInfo.saveCach(mainActivity.getContext());
  }

  @Override public void getWeatherToday(final MainActivity mainActivity) {

    Call<ForecastReturnObject> weatherReturnObjectCall =
        RestClient.getService(mainActivity)
            .getWeatherForecastLocation(13.736717, 100.523186, 1);
    weatherReturnObjectCall.enqueue(new Callback<ForecastReturnObject>() {
      @Override
      public void onResponse(@NonNull Call<ForecastReturnObject> call,
          @NonNull Response<ForecastReturnObject> response) {
        if (response.isSuccessful()) {
          mainView.updateHeader(response.body());
        }
      }

      @Override
      public void onFailure(@NonNull Call<ForecastReturnObject> call, @NonNull Throwable t) {
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
