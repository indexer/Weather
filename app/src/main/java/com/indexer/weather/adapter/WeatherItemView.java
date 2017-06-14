package com.indexer.weather.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.indexer.weather.R;
import com.indexer.weather.base.BaseViewHolder;
import com.indexer.weather.base.Utils;
import com.indexer.weather.detail.DetailActivity;
import com.indexer.weather.model.Weather;
import com.squareup.picasso.Picasso;
import java.io.Serializable;

/**
 * Created by indexer on 22/5/17.
 */

public class WeatherItemView extends BaseViewHolder {
  @BindView(R.id.item_image) ImageView itemImage;
  @BindView(R.id.weather_date) TextView mWeaterText;
  @BindView(R.id.weather_description) TextView getmWeaterText;
  Weather mWeather;

  public WeatherItemView(View itemView, OnItemClickListener listener) {
    super(itemView, listener);
    ButterKnife.bind(this, itemView);
  }

  @Override public void onClick(View v) {
    Intent intent = new Intent(itemView.getContext(), DetailActivity.class);
    intent.putExtra("weather", mWeather);
    itemView.getContext().startActivity(intent);
  }

  public void onBind(Weather weather, int position) {
    mWeather = weather;
    mWeaterText.setText(Utils.getFriendlyDayString(itemView.getContext(), mWeather.date));
    getmWeaterText.setText(mWeather.main + " / " + mWeather.description);
    String webIcon =
        String.format("http://openweathermap.org/img/w/%s.png",
            mWeather.icon);
    Picasso.with(itemView.getContext()).load(webIcon).into(itemImage);
  }
}
