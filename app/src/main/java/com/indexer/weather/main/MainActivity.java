package com.indexer.weather.main;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.gms.common.SignInButton;
import com.indexer.weather.base.BaseActivity;
import com.indexer.weather.R;
import com.indexer.weather.base.Config;
import com.indexer.weather.model.UserInfo;
import com.indexer.weather.social.GoogleSignIn;
import com.indexer.weather.social.GoogleSignInPresenter;
import com.indexer.weather.social.LoginView;
import com.squareup.picasso.Picasso;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity
    implements LoginView, NavigationView.OnNavigationItemSelectedListener {

  @BindView(R.id.toolbar) android.support.v7.widget.Toolbar mToolbar;
  @BindView(R.id.design_navigation) NavigationView navigationView;
  @BindView(R.id.drawer_layout) DrawerLayout getmDrawerLayout;
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
      ActionBarDrawerToggle drawerToggle =
          new ActionBarDrawerToggle(this, getmDrawerLayout, mToolbar,
              R.string.drawer_open,
              R.string.drawer_close);
      getmDrawerLayout.addDrawerListener(drawerToggle);
      drawerToggle.syncState();
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

  @Override protected boolean needToolbar() {
    return true;
  }

  @Override protected int getLayoutResource() {
    return R.layout.activity_main;
  }

  @Override protected void onStart() {
    super.onStart();
    signInGooglePresenter.start();
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
    view_container.setVisibility(View.VISIBLE);
    getmButtonGoogleSignIn.setVisibility(View.GONE);
    Menu nav_Menu = navigationView.getMenu();
    nav_Menu.findItem(R.id.navigation_sub_item_logout).setVisible(true);
  }

  @Override public Context getContext() {
    return this.getApplicationContext();
  }

  @Override public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
    return false;
  }
}
