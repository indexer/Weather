package com.indexer.weather.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.indexer.weather.base.Config;

/**
 * Created by indexer on 12/6/17.
 */

public class UserInfo {

  // private static  UserInfo userInstance = null;
  private String user_email;
  private String user_name;
  private String user_bday;
  private String id;
  private String avatar;
  SharedPreferences.Editor save_cach;
  SharedPreferences read_cach;

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
    save_cach = context.getSharedPreferences(Config.USER_INFO, Context.MODE_PRIVATE).edit();
    save_cach.putString(Config.USER_NAME, user_name);
    save_cach.putString(Config.USER_EMAIL, user_email);
    save_cach.putString(Config.USER_BDAY, user_bday);
    save_cach.putString(Config.USER_PFILE, avatar);
    save_cach.commit();
  }

  public void readCach(Context context) {
    read_cach = context.getSharedPreferences(Config.USER_INFO, Context.MODE_PRIVATE);
    user_name = read_cach.getString(Config.USER_NAME, "NONE");
    user_email = read_cach.getString(Config.USER_EMAIL, "NONE");
    user_bday = read_cach.getString(Config.USER_BDAY, "NONE");
    avatar = read_cach.getString(Config.USER_PFILE, "NONE");
  }

  //Setters
  public void setEmail(String email) {
    this.user_email = email;
  }

  public void setUser_name(String user_name) {
    this.user_name = user_name;
  }

  public void setBday(String bday) {
    this.user_bday = bday;
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