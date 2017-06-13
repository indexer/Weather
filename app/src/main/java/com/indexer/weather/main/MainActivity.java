package com.indexer.weather.main;

import android.content.Context;
import android.content.Intent;
import android.icu.util.TimeZone;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.gms.common.SignInButton;
import com.indexer.weather.base.BaseActivity;
import com.indexer.weather.R;
import com.indexer.weather.base.Utils;
import com.indexer.weather.model.UserInfo;
import com.indexer.weather.model.WeatherData;
import com.indexer.weather.social.GoogleSignIn;
import com.indexer.weather.social.GoogleSignInPresenter;
import com.indexer.weather.social.LoginView;
import com.squareup.picasso.Picasso;
import de.hdodenhof.circleimageview.CircleImageView;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends BaseActivity
    implements LoginView, MainView, NavigationView.OnNavigationItemSelectedListener {

  @BindView(R.id.toolbar) android.support.v7.widget.Toolbar mToolbar;
  @BindView(R.id.design_navigation) NavigationView navigationView;
  @BindView(R.id.drawer_layout) DrawerLayout getmDrawerLayout;
  @BindView(R.id.weather_icon) ImageView imageView;
  @BindView(R.id.city_temp) TextView mTempTextView;
  @BindView(R.id.city_name) TextView mTextCityName;
  @BindView(R.id.weather_description) TextView mWeatherDescription;
  @BindView(R.id.weather_humidity) TextView mWeatherHumidity;
  @BindView(R.id.weather_wind) TextView mWeatherWind;
  @BindView(R.id.weather_time) TextView mWeatherTime;
  private GoogleSignInPresenter signInGooglePresenter;
  private MainWeatherInfo mainWeatherInfo;
  private Menu nav_Menu;
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
    mainWeatherInfo = new MainWeatherInfo(this);
    mainWeatherInfo.getWeatherToday(this);
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
    navigationView.setNavigationItemSelectedListener(this);
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
    mainWeatherInfo.start();
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    signInGooglePresenter.onActivityResult(MainActivity.this, requestCode, resultCode, data);
  }

  @Override public void updateUserProfile(UserInfo UserInfo) {
    CircleImageView drawerImage = (CircleImageView) headerView.findViewById(R.id.profile_image);
    drawerImage.setImageDrawable(getDrawable(R.mipmap.ic_launcher));
    Picasso.with(this).load(UserInfo.getAvatarURL()).into(drawerImage);
    mUserName.setText(UserInfo.getUser_name());
    mUserEmail.setText(UserInfo.getUser_email());
    view_container.setVisibility(View.VISIBLE);
    getmButtonGoogleSignIn.setVisibility(View.GONE);
    nav_Menu = navigationView.getMenu();
    nav_Menu.findItem(R.id.navigation_sub_item_logout).setVisible(true);
  }

  @Override public void updateHeader(WeatherData weatherData) {
    if (weatherData != null) {
      char degree = '\u00B0';
      Double c = (double) Math.round(weatherData.main.temp - 273.16);
      mTempTextView.setText(String.format("%s%sC", c.toString(), degree));
      mTextCityName.setText(weatherData.name);
      mWeatherDescription.setText(weatherData.weather.get(0).description);
      mWeatherHumidity.setText(String.format("Humidity %d %% ", weatherData.main.humidity));
      String getWindwithFormat =
          Utils.getFormattedWind(this, weatherData.wind.speed, weatherData.wind.deg);
      mWeatherWind.setText(getWindwithFormat);
      Date date = new Date(weatherData.dt * 1000L);
      // format of the date
      SimpleDateFormat jdf = new SimpleDateFormat("hh:mm");
      String current_date = jdf.format(date);
      mWeatherTime.setText(current_date);
      String webIcon =
          String.format("http://openweathermap.org/img/w/%s.png", weatherData.weather.get(0).icon);
      Picasso.with(this).load(webIcon).into(imageView);
    }
  }

  @Override public void updateProfile(UserInfo userInfo) {
    if (userInfo == null) {
      view_container.setVisibility(View.GONE);
      nav_Menu.findItem(R.id.navigation_sub_item_logout).setVisible(false);
      getmButtonGoogleSignIn.setVisibility(View.VISIBLE);
    }
  }

  @Override public Context getContext() {
    return this.getApplicationContext();
  }

  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
    // Handle navigation view item clicks here.
    int id = menuItem.getItemId();
    menuItem.setChecked(true);
    if (id == R.id.navigation_sub_item_logout) {
      // Handle the home action
      mainWeatherInfo.signOut(this);
    } else if (id == R.id.navigation_sub_item_1) {
      Toast.makeText(this, "Hello 5day Focus", Toast.LENGTH_LONG).show();
    } else if (id == R.id.navigation_sub_item_2) {
      Toast.makeText(this, "Hello 12day Focus", Toast.LENGTH_LONG).show();
    }

    getmDrawerLayout.closeDrawer(GravityCompat.START);
    return true;
  }
}
