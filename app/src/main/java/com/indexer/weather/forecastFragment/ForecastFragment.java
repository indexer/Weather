package com.indexer.weather.forecastFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.indexer.weather.R;
import com.indexer.weather.adapter.WeatherForecastAdapter;
import com.indexer.weather.model.Weather;
import java.util.ArrayList;

/**
 * Created by indexer on 14/6/17.
 */

public class ForecastFragment extends Fragment implements ForecastWeatherView {
  @BindView(R.id.weather_forecast_list) RecyclerView mRecyclerView;
  @BindView(R.id.m_Progress) ProgressBar mProgress;

  public ForecastFragment() {
    // Required empty public constructor
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.forecast_fragment, container, false);
    ButterKnife.bind(this, v);
    ForecastWeatherItem forecastWeatherItem = new ForecastWeatherItem(this);
    forecastWeatherItem.getWeatherForecast(getActivity(), 5);
    return v;
  }

  @Override public void getWeatherList(ArrayList<Weather> forecastWeather) {
    mProgress.setVisibility(View.GONE);
    mRecyclerView.setVisibility(View.VISIBLE);
    WeatherForecastAdapter mWeatherForecastAdapter = new WeatherForecastAdapter();
    mWeatherForecastAdapter.setItems(forecastWeather);
    mRecyclerView.setAdapter(mWeatherForecastAdapter);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
  }
}
