package com.indexer.weather.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.indexer.weather.base.BaseActivity;
import com.indexer.weather.R;
import com.indexer.weather.model.UserInfo;
import com.indexer.weather.social.GoogleSignIn;
import com.indexer.weather.social.GoogleSignInPresenter;
import com.indexer.weather.social.LoginView;
import com.squareup.picasso.Picasso;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity implements LoginView {
  @BindView(R.id.button_google_sign_in) SignInButton mButtonGoogleSignIn;
  @BindView(R.id.toolbar) android.support.v7.widget.Toolbar mToolbar;
  @BindView(R.id.design_navigation_view) NavigationView navigationView;
  private GoogleSignInPresenter signInGooglePresenter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ButterKnife.bind(this);
    setSupportActionBar(mToolbar);
    getSupportActionBar().setDisplayShowTitleEnabled(false);
    //Google+
    mButtonGoogleSignIn.setSize(SignInButton.SIZE_STANDARD);
    signInGooglePresenter = new GoogleSignIn(this);
    signInGooglePresenter.createGoogleClient(this);
  }

  @Override protected void onStart() {
    super.onStart();
    signInGooglePresenter.start();
  }

  @OnClick(R.id.button_google_sign_in) void SignIn() {
    signInGooglePresenter.signIn(MainActivity.this);
  }

  @Override protected int getLayoutResource() {
    return R.layout.activity_main;
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    signInGooglePresenter.onActivityResult(MainActivity.this, requestCode, resultCode, data);
  }

  @Override public void specifyGoogleSignIn(GoogleSignInOptions gso) {
    mButtonGoogleSignIn.setScopes(gso.getScopeArray());
  }

  @Override public void updagteProfile(UserInfo UserInfo) {
    View headerView = navigationView.getHeaderView(0);
    CircleImageView drawerImage = (CircleImageView) headerView.findViewById(R.id.profile_image);
    drawerImage.setImageDrawable(getDrawable(R.mipmap.ic_launcher));
    Picasso.with(this).load(UserInfo.getAvatarURL()).into(drawerImage);
  }

  @Override public Context getContext() {
    return this.getApplicationContext();
  }
}
