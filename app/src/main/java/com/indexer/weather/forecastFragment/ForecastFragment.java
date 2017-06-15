package com.indexer.weather.forecastFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.indexer.weather.R;
import com.indexer.weather.adapter.WeatherForecastAdapter;
import com.indexer.weather.base.Utils;
import com.indexer.weather.model.Weather;
import java.util.ArrayList;

/**
 * Created by indexer on 14/6/17.
 */

public class ForecastFragment extends Fragment implements ForecastWeatherView {
  @BindView(R.id.weather_forecast_list) RecyclerView mRecyclerView;
  @BindView(R.id.m_Progress) ProgressBar mProgress;
  ForecastWeatherItem forecastWeatherItem;
  @BindView(R.id.m_coordinatorLayout) CoordinatorLayout coordinatorLayout;

  public ForecastFragment() {
    // Required empty public constructor
  }

  @Override public void onResume() {
    super.onResume();
    if (Utils.isNetworkAvaliable(getActivity())) {
      forecastWeatherItem =
          new ForecastWeatherItem(this);
      forecastWeatherItem.getWeatherForecast(getActivity(), 5);
    } else {
      getWeatherList(null);
      noInternetAction();
    }
  }

  private void noInternetAction() {

    Snackbar snackbar = Snackbar
        .make(coordinatorLayout, "There is no Network Connection", Snackbar.LENGTH_LONG)
        .setAction("Open", new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
          }
        });

    snackbar.show();
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.forecast_fragment, container, false);
    ButterKnife.bind(this, v);
    if (!Utils.isNetworkAvaliable(getActivity())) {
      mProgress.setVisibility(View.GONE);
      mRecyclerView.setVisibility(View.GONE);
      noInternetAction();
    } else {
      forecastWeatherItem = new ForecastWeatherItem(this);
      forecastWeatherItem.getWeatherForecast(getActivity(), 5);
    }
    return v;
  }

  @Override public void getWeatherList(ArrayList<Weather> forecastWeather) {
    if (forecastWeather != null) {
      mProgress.setVisibility(View.GONE);
      mRecyclerView.setVisibility(View.VISIBLE);
      WeatherForecastAdapter mWeatherForecastAdapter = new WeatherForecastAdapter();
      mWeatherForecastAdapter.setItems(forecastWeather);
      mRecyclerView.setAdapter(mWeatherForecastAdapter);
      mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    } else {
      mRecyclerView.setVisibility(View.GONE);
      mProgress.setVisibility(View.GONE);
    }
  }
}
