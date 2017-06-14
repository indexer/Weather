package com.indexer.weather.social;

import android.content.Intent;
import com.indexer.weather.base.BasePresenter;
import com.indexer.weather.main.MainActivity;

/**
 * Created by indexer on 12/6/17.
 */

public interface GoogleSignInPresenter extends BasePresenter {
  void createGoogleClient(MainActivity loginView);

  void signIn(MainActivity loginView);

  void onActivityResult(MainActivity loginView, int requestCode, int resultCode, Intent data);
}