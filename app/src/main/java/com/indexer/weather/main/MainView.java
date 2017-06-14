package com.indexer.weather.main;

import android.content.Context;
import com.indexer.weather.model.ForecastReturnObject;
import com.indexer.weather.model.UserInfo;

public interface MainView {

  void updateHeader(ForecastReturnObject weatherData);

  void updateProfile(UserInfo userInfo);


  Context getContext();
}

