package com.indexer.weather.social;

/**
 * Created by indexer on 12/6/17.
 */

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.indexer.weather.main.MainActivity;
import com.indexer.weather.model.UserInfo;

public class GoogleSignIn implements GoogleSignInPresenter, GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener {

  private LoginView fieldLoginView;
  // Google client to communicate with Google
  private GoogleApiClient mGoogleApiClient;
  private static final int RC_SIGN_IN_G = 2016;

  public GoogleSignIn(LoginView loginView) {
    this.fieldLoginView = loginView;
  }

  @Override
  public void createGoogleClient(MainActivity loginView) {
    // Configure sign-in to request the user's ID, email address, and basic
    // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .build();
    // Build a GoogleApiClient with access to the Google Sign-In API and the
    // options specified by gso.
    fieldLoginView.specifyGoogleSignIn(gso);
    mGoogleApiClient = new GoogleApiClient.Builder(loginView)
        .enableAutoManage(loginView /* FragmentActivity */, this
            /* OnConnectionFailedListener */)
        .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
        .build();
  }

  @Override
  public void signIn(MainActivity loginView) {
    Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
    loginView.startActivityForResult(signInIntent, RC_SIGN_IN_G);
  }

  @Override
  public void onActivityResult(MainActivity loginView, int requestCode, int resultCode,
      Intent data) {
    // super.onActivityResult(requestCode, resultCode, data);
    // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
    if (requestCode == RC_SIGN_IN_G) {
      GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
      handleSignInResult(result);
    }
  }

  private void handleSignInResult(GoogleSignInResult result) {
    //Log.d(TAG, "handleSignInResult:" + result.isSuccess());
    if (result.isSuccess()) {
      // Signed in successfully, show authenticated UI.
      GoogleSignInAccount acct = result.getSignInAccount();
      String personName = acct.getDisplayName();
      String personEmail = acct.getEmail();
      String personId = acct.getId();
      Uri personPhoto = acct.getPhotoUrl();
      UserInfo userModelSingleton = UserInfo.getInstance();
      userModelSingleton.setUser_name(personName);
      userModelSingleton.setEmail(personEmail);
      userModelSingleton.setAvatarURL(personPhoto.toString());
      userModelSingleton.setBday(" ");
      fieldLoginView.updagteProfile(userModelSingleton);
    } else {
      // Signed out, show unauthenticated UI.
      //updateUI(false);
    }
  }

  @Override
  public void onConnected(@Nullable Bundle bundle) {

  }

  @Override
  public void onConnectionSuspended(int i) {

  }

  @Override
  public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

  }

  @Override public void resume() {

  }

  @Override public void pause() {

  }

  @Override public void start() {
    mGoogleApiClient.connect();
  }

  @Override public void stop() {
    fieldLoginView = null;
  }

  @Override public void destroy() {
    if (mGoogleApiClient.isConnected()) {
      mGoogleApiClient.disconnect();
    }
  }

  @Override public void onError(String message) {

  }
}
