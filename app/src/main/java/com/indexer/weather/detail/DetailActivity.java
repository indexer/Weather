package com.indexer.weather.detail;

import android.os.Bundle;
import com.indexer.weather.R;
import com.indexer.weather.base.BaseActivity;

public class DetailActivity extends BaseActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override protected boolean needToolbar() {
    return false;
  }

  @Override protected int getLayoutResource() {
    return R.layout.activity_detail;
  }
}
