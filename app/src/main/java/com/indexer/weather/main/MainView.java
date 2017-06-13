package com.indexer.weather.main;

import android.content.Context;
import com.indexer.weather.model.UserInfo;
import com.indexer.weather.model.WeatherData;

public interface MainView {

  void updateHeader(WeatherData weatherData);

  void updateProfile(UserInfo userInfo);

  Context getContext();
}

