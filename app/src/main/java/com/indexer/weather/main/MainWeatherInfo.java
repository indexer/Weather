package com.indexer.weather.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.text.format.Time;
import android.util.Log;
import com.indexer.weather.base.Config;
import com.indexer.weather.base.Utils;
import com.indexer.weather.model.Coord;
import com.indexer.weather.model.ForecastReturnObject;
import com.indexer.weather.model.UserInfo;
import com.indexer.weather.model.Weather;
import com.indexer.weather.rest.RestClient;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.channels.FileChannel;
import javax.net.ssl.SSLHandshakeException;
import org.afinal.simplecache.ACache;
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
    SharedPreferences mSharedPreferences = PreferenceManager.
        getDefaultSharedPreferences(mainActivity.getContext());

    float lat = mSharedPreferences.getFloat(Config.LAST_LATITUDE, 0);
    float lng = mSharedPreferences.getFloat(Config.LAST_LONGITUDE, 0);

    Call<ForecastReturnObject> weatherReturnObjectCall =
        RestClient.getService(mainActivity)
            .getWeatherForecastLocation(lat, lng, 1, Config.unit);
    weatherReturnObjectCall.enqueue(new Callback<ForecastReturnObject>() {
      @Override
      public void onResponse(@NonNull Call<ForecastReturnObject> call,
          @NonNull Response<ForecastReturnObject> response) {
        if (response.isSuccessful()) {
          Weather mWeather;
          mWeather = response.body().getList().get(0).getWeather().get(0);
          mWeather.temp = response.body().getList().get(0).getTemp().getDay();
          mWeather.speed = response.body().getList().get(0).getSpeed();
          mWeather.humidity = response.body().getList().get(0).getHumidity();
          mWeather.degree = response.body().getList().get(0).getDeg();
          mWeather.city = response.body().getCity().getName();
          mWeather.date = response.body().getList().get(0).getDt();
          mainView.updateHeader(mWeather);
        }
      }

      @Override
      public void onFailure(@NonNull Call<ForecastReturnObject> call, @NonNull Throwable t) {
        try {
          throw (t.getCause());
        } catch (SocketTimeoutException sock) {
          mainView.updateHeader(null);
        } catch (UnknownHostException e) {
          // unknown host
          mainView.updateHeader(null);
        } catch (SSLHandshakeException e) {
          // ssl handshake exception
        } catch (Exception e) {
          // unknown error
        } catch (Throwable throwable) {
          throwable.printStackTrace();
        }
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
