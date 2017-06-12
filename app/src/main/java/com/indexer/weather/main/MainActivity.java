package com.indexer.weather.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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

  @BindView(R.id.toolbar) android.support.v7.widget.Toolbar mToolbar;
  @BindView(R.id.design_navigation_view) NavigationView navigationView;
  private GoogleSignInPresenter signInGooglePresenter;
  View headerView;
  TextView mUserName;
  TextView mUserEmail;
  RelativeLayout view_container;
  SignInButton getmButtonGoogleSignIn;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ButterKnife.bind(this);
    setSupportActionBar(mToolbar);
    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
    //Google+
    signInGooglePresenter = new GoogleSignIn(this);
    signInGooglePresenter.createGoogleClient(this);
    headerView = navigationView.getHeaderView(0);
    mUserName = (TextView) headerView.findViewById(R.id.user_name);
    mUserEmail = (TextView) headerView.findViewById(R.id.user_email);
    view_container = (RelativeLayout) headerView.findViewById(R.id.view_container);
    getmButtonGoogleSignIn = (SignInButton) headerView.findViewById(R.id.button_google_sign_in);
    getmButtonGoogleSignIn.setSize(SignInButton.SIZE_STANDARD);

    getmButtonGoogleSignIn.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        signInGooglePresenter.signIn(MainActivity.this);
      }
    });
  }

  @Override protected void onStart() {
    super.onStart();
    signInGooglePresenter.start();
  }

  @Override protected int getLayoutResource() {
    return R.layout.activity_main;
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    signInGooglePresenter.onActivityResult(MainActivity.this, requestCode, resultCode, data);
  }

  @Override public void updagteProfile(UserInfo UserInfo) {

    CircleImageView drawerImage = (CircleImageView) headerView.findViewById(R.id.profile_image);
    drawerImage.setImageDrawable(getDrawable(R.mipmap.ic_launcher));
    Picasso.with(this).load(UserInfo.getAvatarURL()).into(drawerImage);
    mUserName.setText(UserInfo.getUser_name());
    mUserEmail.setText(UserInfo.getUser_email());
    if (UserInfo != null) {
      view_container.setVisibility(View.VISIBLE);
      getmButtonGoogleSignIn.setVisibility(View.GONE);
    } else {
      getmButtonGoogleSignIn.setVisibility(View.GONE);
    }
  }

  @Override public Context getContext() {
    return this.getApplicationContext();
  }
}
