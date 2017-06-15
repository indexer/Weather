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
import com.indexer.weather.base.Config;
import com.indexer.weather.base.Utils;
import com.indexer.weather.model.Weather;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.afinal.simplecache.ACache;

/**
 * Created by indexer on 14/6/17.
 */

public class ForecastFragment extends Fragment implements ForecastWeatherView {
  @BindView(R.id.weather_forecast_list) RecyclerView mRecyclerView;
  @BindView(R.id.m_Progress) ProgressBar mProgress;
  ForecastWeatherItem forecastWeatherItem;
  @BindView(R.id.m_coordinatorLayout) CoordinatorLayout coordinatorLayout;
  WeatherForecastAdapter mWeatherForecastAdapter;
  ACache mCache;
  ArrayList<Weather> mList = new ArrayList<>();

  public ForecastFragment() {
    // Required empty public constructor
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mWeatherForecastAdapter = new WeatherForecastAdapter();
  }

  @Override public void onResume() {
    super.onResume();
    if (Utils.isNetworkAvaliable(getActivity())) {
      forecastWeatherItem =
          new ForecastWeatherItem(this);
      forecastWeatherItem.getWeatherForecast(getActivity(), 5);
    } else {
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
    mCache = ACache.get(getActivity());
    LinearLayoutManager llm = new LinearLayoutManager(getActivity());
    llm.setOrientation(LinearLayoutManager.VERTICAL);
    mRecyclerView.setLayoutManager(llm);
    mRecyclerView.setAdapter(mWeatherForecastAdapter);
    mRecyclerView.setHasFixedSize(true);
    if (!Utils.isNetworkAvaliable(getActivity())) {
      mProgress.setVisibility(View.GONE);
      noInternetAction();
      List<Weather> testObject = mCache.getAsObjectList(Config.weather_list, Weather.class);
      if (testObject != null) {
        mRecyclerView.setVisibility(View.VISIBLE);
        mList.addAll(testObject);
        Log.e("user", "hello");
        mWeatherForecastAdapter.setItems(mList);
        mRecyclerView.setAdapter(mWeatherForecastAdapter);
        mWeatherForecastAdapter.notifyDataSetChanged();
      }
    }
    return v;
  }

  @Override public void getWeatherList(ArrayList<Weather> forecastWeather) {
    if (forecastWeather != null) {
      mProgress.setVisibility(View.GONE);
      mRecyclerView.setVisibility(View.VISIBLE);
      mRecyclerView.setHasFixedSize(true);
      mCache.put(Config.weather_list, (Serializable) forecastWeather);
      mWeatherForecastAdapter.setItems(forecastWeather);
      mWeatherForecastAdapter.notifyDataSetChanged();
    } else {
      mRecyclerView.setVisibility(View.GONE);
      mProgress.setVisibility(View.GONE);
    }
  }
}
