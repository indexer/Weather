package com.indexer.weather.forecastWeatherFragment;

/**
 * Created by indexer on 13/6/17.
 */

import android.content.Context;
import com.indexer.weather.model.Weather;
import java.util.ArrayList;

public interface ForecastWeatherFragmentView {

  void getWeatherList(ArrayList<Weather> forecastWeather);

  Context getContext();
}