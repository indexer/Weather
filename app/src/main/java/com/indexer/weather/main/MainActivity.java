package com.indexer.weather.main;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
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
import com.indexer.weather.base.Utils;
import com.indexer.weather.forecastFragment.ForecastFragment;
import com.indexer.weather.forecastWeatherFragment.ForecastWeatherFragment;
import com.indexer.weather.model.ForecastReturnObject;
import com.indexer.weather.model.UserInfo;
import com.indexer.weather.social.GoogleSignIn;
import com.indexer.weather.social.GoogleSignInPresenter;
import com.indexer.weather.social.LoginView;
import com.squareup.picasso.Picasso;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.List;

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

    if (!isConnected) {
      noInternetAction();
    }
    //Google+
    signInGooglePresenter = new GoogleSignIn(this);
    mainWeatherInfo = new MainWeatherInfo(this);
    mainWeatherInfo.getWeatherToday(this);
    signInGooglePresenter.createGoogleClient(this);
    initComponents();
  }

  @Override protected void onResume() {
    super.onResume();
    if (mainWeatherInfo != null && Utils.isNetworkAvaliable(this)) {
      mainWeatherInfo.getWeatherToday(this);
      initComponents();
    } else {
      noInternetAction();
    }
  }

  private void noInternetAction() {
    getmDrawerLayout.closeDrawer(Gravity.LEFT);
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
    if (drawerToggle.onOptionsItemSelected(item)) {
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  public void initComponents() {
    forecastFragment = new ForecastFragment();
    getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
        forecastFragment).commit();
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
    mUserInfo = UserInfo.getInstance();
    mUserInfo.readCach(this);
    if (mUserInfo.getUser_name().equals("NONE")) {
      getmDrawerLayout.openDrawer(Gravity.LEFT);
    } else {
      showAlreadyUser(mUserInfo);
    }
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
    mainWeatherInfo.start();
  }

  public void showAlreadyUser(UserInfo UserInfo) {
    CircleImageView drawerImage = (CircleImageView) headerView.findViewById(R.id.profile_image);
    Picasso.with(this).load(UserInfo.getAvatarURL()).into(drawerImage);
    mUserName.setText(UserInfo.getUser_name());
    mUserEmail.setText(UserInfo.getUser_email());
    view_container.setVisibility(View.VISIBLE);
    getmButtonGoogleSignIn.setVisibility(View.GONE);
    nav_Menu = navigationView.getMenu();
    nav_Menu.findItem(R.id.navigation_sub_item_logout).setVisible(true);
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
    if (getmDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
      getmDrawerLayout.closeDrawer(Gravity.LEFT);
    }
  }

  @Override public void updateHeader(ForecastReturnObject weatherData) {
    if (weatherData != null) {
      mTempTextView.setText(
          Utils.formatTemperature(this, weatherData.getList().get(0).getTemp().getDay(),
              true) + "C");
      mTextCityName.setText(weatherData.getCity().getName());
      mWeatherDescription.setText(weatherData.getList().get(0).getWeather().get(0).description);
      mWeatherHumidity.setText(
          Utils.getFormattedHumidity(this, weatherData.getList().get(0).getHumidity()));
      String getWindwithFormat =
          Utils.getFormattedWind(this, weatherData.getList().get(0).getSpeed(),
              weatherData.getList().get(0).getDeg());
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
              weatherData.getList().get(0).getWeather().get(0).icon);
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


    /**/

  public android.support.v4.app.Fragment getVisibleFragment() {
    FragmentManager fragmentManager = getSupportFragmentManager();
    List<android.support.v4.app.Fragment> fragments = fragmentManager.getFragments();
    if (fragments != null) {
      for (android.support.v4.app.Fragment fragment : fragments) {
        if (fragment != null && fragment.isVisible()) {
          return fragment;
        }
      }
    }
    return null;
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
      if (forecastFragment != getVisibleFragment()) {
        android.support.v4.app.FragmentTransaction transaction =
            getSupportFragmentManager().beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragment_container, forecastFragment);
        transaction.addToBackStack(null);
        // Commit the transaction
        transaction.commit();
      }
    } else if (id == R.id.navigation_sub_item_2) {
      forecastWeatherFragment = new ForecastWeatherFragment();
      if (forecastWeatherFragment != getVisibleFragment()) {
        android.support.v4.app.FragmentTransaction transaction =
            getSupportFragmentManager().beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragment_container, forecastWeatherFragment);
        transaction.addToBackStack(null);
        // Commit the transaction
        transaction.commit();
      }
    }

    getmDrawerLayout.closeDrawer(GravityCompat.START);
    return true;
  }
}
