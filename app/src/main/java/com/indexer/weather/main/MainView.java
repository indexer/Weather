package com.indexer.weather.main;

import android.content.Context;
import com.indexer.weather.model.UserInfo;
import com.indexer.weather.model.Weather;

public interface MainView {

  void updateHeader(Weather weatherData);

  void updateProfile(UserInfo userInfo);


  Context getContext();
}

