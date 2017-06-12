package com.indexer.weather.social;

/**
 * Created by indexer on 12/6/17.
 */

import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.indexer.weather.model.UserInfo;

public interface LoginView {
  void specifyGoogleSignIn(GoogleSignInOptions gso);

  void updagteProfile(UserInfo userInfo);

  Context getContext();
}
