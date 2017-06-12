package com.indexer.weather.base;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import java.util.List;
import pub.devrel.easypermissions.EasyPermissions;

public abstract class BaseActivity extends AppCompatActivity
    implements GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener, LocationListener,
    EasyPermissions.PermissionCallbacks {
  public Location mLastLocation;
  public float mLatitude = 0;
  public float mLongitude = 0;
  public SharedPreferences mSharedPreferences;
  private GoogleApiClient mGoogleApiClient;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(getLayoutResource());
    buildGoogleApiClient();
    EasyPermissions.requestPermissions(this, "NEED SMS PLZ", 1001,
        Manifest.permission.ACCESS_FINE_LOCATION);
    mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
  }

  protected synchronized void buildGoogleApiClient() {
    mGoogleApiClient = new GoogleApiClient.Builder(this)
        .addConnectionCallbacks(this)
        .addOnConnectionFailedListener(this)
        .addApi(LocationServices.API)
        .build();
  }

  protected abstract boolean needToolbar();

  protected abstract int getLayoutResource();

  @Override public void onConnected(Bundle bundle) {

    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED
        && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {
      return;
    }
    mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

    if (mLastLocation != null) {
      mLatitude = (float) mLastLocation.getLatitude();
      mLongitude = (float) mLastLocation.getLongitude();
      mSharedPreferences.edit().putFloat(Config.LAST_LATITUDE, mLatitude).apply();
      mSharedPreferences.edit().putFloat(Config.LAST_LONGITUDE, mLongitude).apply();
    } else {
      LocationRequest mLocationRequest = LocationRequest.create()
          .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
          .setInterval(10 * 1000)        // 10 seconds, in milliseconds
          .setFastestInterval(1 * 1000);
      LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest,
          this);
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    // EasyPermissions handles the request result.
    EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
  }

  @Override
  public void onPermissionsGranted(int requestCode, List<String> perms) {
    Log.d("Base", "onPermissionsGranted:" + requestCode + ":" + perms.size());
  }

  @Override
  public void onPermissionsDenied(int requestCode, List<String> perms) {
    Log.d("Base", "onPermissionsDenied:" + requestCode + ":" + perms.size());
  }

  @Override public void onConnectionSuspended(int i) {

  }

  @Override public void onConnectionFailed(ConnectionResult connectionResult) {

  }

  @Override protected void onResume() {
    super.onResume();
    mGoogleApiClient.connect();
  }

  @Override protected void onPause() {
    super.onPause();
    mGoogleApiClient.disconnect();
  }

  @Override public void onLocationChanged(Location location) {
    mLatitude = (float) location.getLatitude();
    mLongitude = (float) location.getLongitude();
    mSharedPreferences.edit().putFloat(Config.LAST_LATITUDE, mLatitude).apply();
    mSharedPreferences.edit().putFloat(Config.LAST_LONGITUDE, mLongitude).apply();
  }
}


