package com.indexer.weather.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.indexer.weather.R;
import com.indexer.weather.base.BaseViewHolder;
import com.indexer.weather.base.Utils;
import com.indexer.weather.model.Weather;
import com.squareup.picasso.Picasso;

/**
 * Created by indexer on 22/5/17.
 */

public class WeatherItemView extends BaseViewHolder {
  @BindView(R.id.item_image) ImageView itemImage;
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
    mWeaterText.setText(Utils.getFriendlyDayString(itemView.getContext(), weather.date));
    getmWeaterText.setText(weather.main + "/" + weather.description);
    String webIcon =
        String.format("http://openweathermap.org/img/w/%s.png",
            weather.icon);
    Picasso.with(itemView.getContext()).load(webIcon).into(itemImage);
  }
}
