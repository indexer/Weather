package com.indexer.weather.detail;

import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.text.format.Time;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.indexer.weather.R;
import com.indexer.weather.base.BaseActivity;
import com.indexer.weather.base.Utils;
import com.indexer.weather.model.Weather;
import com.squareup.picasso.Picasso;

public class DetailActivity extends BaseActivity {
  @BindView(R.id.toolbar) android.support.v7.widget.Toolbar mToolbar;
  @BindView(R.id.weather_icon) ImageView imageView;
  @BindView(R.id.city_temp) TextView mTempTextView;
  @BindView(R.id.city_name) TextView mTextCityName;
  @BindView(R.id.weather_description) TextView mWeatherDescription;
  @BindView(R.id.weather_humidity) TextView mWeatherHumidity;
  @BindView(R.id.weather_wind) TextView mWeatherWind;
  @BindView(R.id.weather_time) TextView mWeatherTime;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ButterKnife.bind(this);
    setSupportActionBar(mToolbar);
    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayShowTitleEnabled(false);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    Weather mWeather = (Weather) getIntent().getSerializableExtra("weather");
    if (mWeather != null) {
      mTempTextView.setText(
          String.format("%sC",
              Utils.formatTemperature(this, mWeather.temp,
                  true)));
      mTextCityName.setText(mWeather.city);
      mWeatherDescription.setText(mWeather.description);
      mWeatherHumidity.setText(
          Utils.getFormattedHumidity(this, mWeather.humidity));
      String getWindwithFormat =
          Utils.getFormattedWind(this, mWeather.speed,
              mWeather.degree);
      mWeatherWind.setText(getWindwithFormat);
      // format of the date
      //Today Time
      Time dayTime = new Time();
      dayTime.setToNow();
      long dateTime;
      // we start at the day returned by local time. Otherwise this is a mess.
      int julianStartDay = Time.getJulianDay(mWeather.date, dayTime.gmtoff);
      dayTime = new Time();
      dateTime = dayTime.setJulianDay(julianStartDay);
      mWeatherTime.setText(Utils.getFriendlyDayString(this, dateTime));
      String webIcon =
          String.format("http://openweathermap.org/img/w/%s.png",
              mWeather.icon);
      Picasso.with(this).load(webIcon).into(imageView);
    }
  }

  @Override protected boolean needToolbar() {
    return true;
  }

  @Override protected int getLayoutResource() {
    return R.layout.activity_detail;
  }
}
