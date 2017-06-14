package com.indexer.weather.detail;

import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.indexer.weather.R;
import com.indexer.weather.base.BaseActivity;
import com.indexer.weather.model.Weather;

public class DetailActivity extends BaseActivity {
  @BindView(R.id.toolbar) android.support.v7.widget.Toolbar mToolbar;

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

    }
  }

  @Override protected boolean needToolbar() {
    return true;
  }

  @Override protected int getLayoutResource() {
    return R.layout.activity_detail;
  }
}
