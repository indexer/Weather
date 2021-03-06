package com.indexer.weather.forecastFragment;

/**
 * Created by indexer on 13/6/17.
 */

import android.content.Context;

import com.indexer.weather.model.List;
import com.indexer.weather.model.Weather;
import java.util.ArrayList;

public interface ForecastWeatherView {

  void getWeatherList(ArrayList<Weather> forecastWeather);

  Context getContext();
}