package com.indexer.weather.social;

/**
 * Created by indexer on 12/6/17.
 */

import android.content.Context;

import com.indexer.weather.model.UserInfo;

public interface LoginView {

  void updateUserProfile(UserInfo us);

  Context getContext();
}
