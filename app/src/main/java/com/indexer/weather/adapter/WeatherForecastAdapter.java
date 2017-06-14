package com.indexer.weather.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.indexer.weather.R;
import com.indexer.weather.base.BaseAdapter;
import com.indexer.weather.model.List;

/**
 * Created by indexer on 14/6/17.
 */

public class WeatherForecastAdapter extends BaseAdapter<WeatherItemView, List> {
  @Override public WeatherItemView onCreateViewHolder(ViewGroup parent, int
      viewType) {
    View view =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_item, parent, false);
    return new WeatherItemView(view, null);
  }

  @Override public void onBindViewHolder(WeatherItemView holder, int position) {
    holder.onBind(mItems.get(position).getWeather().get(0), position);
  }
}