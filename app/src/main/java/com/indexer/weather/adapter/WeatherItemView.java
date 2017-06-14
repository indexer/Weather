package com.indexer.weather.adapter;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.indexer.weather.R;
import com.indexer.weather.base.BaseViewHolder;
import com.indexer.weather.model.Weather;

/**
 * Created by indexer on 22/5/17.
 */

public class WeatherItemView extends BaseViewHolder {
  @BindView(R.id.weather_date) TextView mWeaterText;
  @BindView(R.id.weather_description) TextView getmWeaterText;

  public WeatherItemView(View itemView, OnItemClickListener listener) {
    super(itemView, listener);
    ButterKnife.bind(this, itemView);
  }

  @Override public void onClick(View v) {
    Toast.makeText(v.getContext(), "Hello", Toast.LENGTH_LONG).show();
  }

  public void onBind(Weather weather, int position) {
    mWeaterText.setText(weather.main);
    getmWeaterText.setText(weather.description);
  }
}
