package com.indexer.weather.model;

import android.content.Context;
import android.content.SharedPreferences;
import com.indexer.weather.base.Config;

/**
 * Created by indexer on 12/6/17.
 */

public class UserInfo {

  private String user_email;
  private String user_name;
  private String user_bday;
  private String id;
  private String avatar;

  private static UserInfo mInstance = null;

  public void setAvatarURL(String avatar) {
    this.avatar = avatar;
  }

  public String getAvatarURL() {
    return avatar;
  }

  private UserInfo() {
  }

  public static UserInfo getInstance() {
    if (mInstance == null) {
      mInstance = new UserInfo();
    }
    return mInstance;
  }

  public void saveCach(Context context) {
    SharedPreferences.Editor saveCach =
        context.getSharedPreferences(Config.USER_INFO, Context.MODE_PRIVATE).edit();
    saveCach.putString(Config.USER_NAME, user_name);
    saveCach.putString(Config.USER_EMAIL, user_email);
    saveCach.putString(Config.USER_BDAY, user_bday);
    saveCach.putString(Config.USER_PFILE, avatar);
    saveCach.commit();
  }

  public void readCach(Context context) {
    SharedPreferences readCach =
        context.getSharedPreferences(Config.USER_INFO, Context.MODE_PRIVATE);
    user_name = readCach.getString(Config.USER_NAME, "NONE");
    user_email = readCach.getString(Config.USER_EMAIL, "NONE");
    user_bday = readCach.getString(Config.USER_BDAY, "NONE");
    avatar = readCach.getString(Config.USER_PFILE, "NONE");
  }

  //Setters
  public void setEmail(String email) {
    this.user_email = email;
  }

  public void setUser_name(String user_name) {
    this.user_name = user_name;
  }

  public void setBday() {
    this.user_bday = " ";
  }

  public void setId(String id) {
    this.id = id;
  }

  //Getters
  public String getUser_email() {
    return user_email;
  }

  public String getUser_name() {
    return user_name;
  }

  public String getId() {
    return id;
  }
}