package com.indexer.weather.main;

import com.indexer.weather.base.BasePresenter;

public interface MainPresenter extends BasePresenter {

  void signOut(MainActivity mainActivity);

  void getWeatherToday(MainActivity mainActivity);

  void onError(String message);
}