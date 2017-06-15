package com.indexer.weather.forecastWeatherFragment;

import android.app.Activity;
import com.indexer.weather.base.BasePresenter;

/**
 * Created by indexer on 13/6/17.
 */

public interface ForecastWeatherFragmentPresenter extends BasePresenter {

  void getWeatherForecast(Activity mainActivity, int day);

  void onError(String message);
}
