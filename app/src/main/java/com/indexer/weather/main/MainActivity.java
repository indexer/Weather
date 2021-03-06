package com.indexer.weather.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.text.format.Time;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.gms.common.SignInButton;
import com.indexer.weather.R;
import com.indexer.weather.base.BaseActivity;
import com.indexer.weather.base.Config;
import com.indexer.weather.base.Utils;
import com.indexer.weather.forecastFragment.ForecastFragment;
import com.indexer.weather.forecastWeatherFragment.ForecastWeatherFragment;
import com.indexer.weather.model.UserInfo;
import com.indexer.weather.model.Weather;
import com.indexer.weather.social.GoogleSignIn;
import com.indexer.weather.social.GoogleSignInPresenter;
import com.indexer.weather.social.LoginView;
import com.squareup.picasso.Picasso;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.List;
import org.afinal.simplecache.ACache;

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
  @BindView(R.id.fragment_container) FrameLayout mFragmentContainer;
  @BindView(R.id.m_coordinatorLayout) CoordinatorLayout coordinatorLayout;
  @BindView(R.id.navigation_header_container) RelativeLayout getView_container;
  private GoogleSignInPresenter signInGooglePresenter;
  private MainWeatherInfo mainWeatherInfo;
  private Menu nav_Menu;
  private ActionBarDrawerToggle drawerToggle;
  ForecastFragment forecastFragment;
  ForecastWeatherFragment forecastWeatherFragment;
  View headerView;
  TextView mUserName;
  TextView mUserEmail;
  UserInfo mUserInfo;
  RelativeLayout view_container;
  SignInButton getmButtonGoogleSignIn;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ButterKnife.bind(this);
    setSupportActionBar(mToolbar);

    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayShowTitleEnabled(false);
      drawerToggle =
          new ActionBarDrawerToggle(this, getmDrawerLayout, mToolbar,
              R.string.drawer_open,
              R.string.drawer_close);
      getmDrawerLayout.addDrawerListener(drawerToggle);
      drawerToggle.syncState();
    }
    forecastFragment = new ForecastFragment();

    getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
        forecastFragment).commit();

    if (!isConnected) {
      noInternetAction();
      /*ACache mCache = ACache.get(this);
      Weather value = (Weather) mCache.getAsObject(Config.weather_cache);
      Log.e("value", "current" + value.main);*/
    }
    //Google+
    signInGooglePresenter = new GoogleSignIn(this);
    signInGooglePresenter.createGoogleClient(this);
    initComponents();
    mUserInfo = UserInfo.getInstance();
    try {
      mUserInfo.readCach(this);
    } catch (ClassCastException x) {
      x.printStackTrace();
    }
    if (mUserInfo != null) {
      showAlreadyUser(mUserInfo);
    }
  }

  @Override protected void onResume() {
    super.onResume();
    mainWeatherInfo = new MainWeatherInfo(this);
    if (Utils.isNetworkAvaliable(this)) {
      mainWeatherInfo.getWeatherToday(this);
      initComponents();
    } else {
      getView_container.setVisibility(View.GONE);
      noInternetAction();
    }
  }

  private void noInternetAction() {
    updateHeader(null);
    getmDrawerLayout.closeDrawer(Gravity.START);
    Snackbar snackbar = Snackbar
        .make(coordinatorLayout, "There is no Network Connection", Snackbar.LENGTH_LONG)
        .setAction("Open", new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
          }
        });

    snackbar.show();
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    return drawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
  }

  public void initComponents() {

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

  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    // Sync the toggle state after onRestoreInstanceState has occurred.
    drawerToggle.syncState();
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    // Pass any configuration change to the drawer toggles
    drawerToggle.onConfigurationChanged(newConfig);
  }

  @Override protected void onStart() {
    super.onStart();
    signInGooglePresenter.start();
  }

  public void showAlreadyUser(UserInfo UserInfo) {
    nav_Menu = navigationView.getMenu();
    if (UserInfo.getUser_name().equals("NONE")) {
      getmButtonGoogleSignIn.setVisibility(View.VISIBLE);
      getmDrawerLayout.openDrawer(Gravity.START);
      nav_Menu.findItem(R.id.navigation_sub_item_logout).setVisible(false);
    } else {
      CircleImageView drawerImage = (CircleImageView) headerView.findViewById(R.id.profile_image);
      Picasso.with(this).load(UserInfo.getAvatarURL()).into(drawerImage);
      mUserName.setText(UserInfo.getUser_name());
      mUserEmail.setText(UserInfo.getUser_email());
      view_container.setVisibility(View.VISIBLE);
      getmButtonGoogleSignIn.setVisibility(View.GONE);
      nav_Menu.findItem(R.id.navigation_sub_item_logout).setVisible(true);
    }
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    signInGooglePresenter.onActivityResult(MainActivity.this, requestCode, resultCode, data);
  }

  @Override public void updateUserProfile(UserInfo UserInfo) {
    CircleImageView drawerImage = (CircleImageView) headerView.findViewById(R.id.profile_image);
    Picasso.with(this).load(UserInfo.getAvatarURL()).into(drawerImage);
    mUserName.setText(UserInfo.getUser_name());
    mUserEmail.setText(UserInfo.getUser_email());
    view_container.setVisibility(View.VISIBLE);
    getmButtonGoogleSignIn.setVisibility(View.GONE);
    nav_Menu = navigationView.getMenu();
    nav_Menu.findItem(R.id.navigation_sub_item_logout).setVisible(true);
    if (getmDrawerLayout.isDrawerOpen(Gravity.START)) {
      getmDrawerLayout.closeDrawer(Gravity.START);
    }
  }

  @Override public void updateHeader(Weather weatherData) {
    if (weatherData != null) {
      getView_container.setVisibility(View.VISIBLE);
      mTempTextView.setText(
          String.format("%sC",
              Utils.formatTemperature(this, weatherData.temp,
                  true)));
      mTextCityName.setText(weatherData.city);
      mWeatherDescription.setText(weatherData.description);
      mWeatherHumidity.setText(
          Utils.getFormattedHumidity(this, weatherData.humidity));
      String getWindwithFormat =
          Utils.getFormattedWind(this, weatherData.speed,
              weatherData.degree);
      mWeatherWind.setText(getWindwithFormat);
      // format of the date
      //Today Time
      Time dayTime = new Time();
      dayTime.setToNow();
      long dateTime;
      // we start at the day returned by local time. Otherwise this is a mess.
      int julianStartDay = Time.getJulianDay(System.currentTimeMillis(), dayTime.gmtoff);
      dayTime = new Time();
      dateTime = dayTime.setJulianDay(julianStartDay);
      mWeatherTime.setText(Utils.getFriendlyDayString(this, dateTime));
      String webIcon =
          String.format("http://openweathermap.org/img/w/%s.png",
              weatherData.icon);
      Picasso.with(this).load(webIcon).into(imageView);
      ACache mCache = ACache.get(this);
      mCache.put(Config.weather_cache, weatherData, ACache.TIME_DAY);
    } else {
      getView_container.setVisibility(View.GONE);
      ACache mCache = ACache.get(this);
      Weather value = (Weather) mCache.getAsObject(Config.weather_cache);
      if (value != null) {
        updateHeader(value);
      }
    }
  }

  @Override public void onBackPressed() {
    super.onBackPressed();
    this.finish();
  }

  @Override public void updateProfile(UserInfo userInfo) {
    if (userInfo == null) {
      SharedPreferences preferences = getSharedPreferences(Config.USER_INFO, Context.MODE_PRIVATE);
      preferences.edit().clear().commit();
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
    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
    switch (menuItem.getItemId()) {
      case R.id.navigation_sub_item_1:
        forecastWeatherFragment = new ForecastWeatherFragment();
        ft.replace(R.id.fragment_container, forecastFragment);
        ft.commit();
        getmDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
      case R.id.navigation_sub_item_2:
        forecastWeatherFragment = new ForecastWeatherFragment();
        ft.replace(R.id.fragment_container, forecastWeatherFragment);
        ft.commit();
        getmDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
      case R.id.navigation_sub_item_logout:
        mainWeatherInfo.signOut(this);
        return true;
      default:
        return true;
    }
  }
}
