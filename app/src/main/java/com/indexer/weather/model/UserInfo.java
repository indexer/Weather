package com.indexer.weather.model;

/**
 * Created by indexer on 12/6/17.
 */

public class UserInfo {

  // private static  UserInfo userInstance = null;
  private String user_email;
  private String user_name;
  private String user_bday;
  private int login_status;
  private int service;
  private String id;
  private String avatar;

  private UserInfo user;

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

  public void setLogin_status(int login_status) {
    this.login_status = login_status;
  }

  public void setService(int service) {
    this.service = service;
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

  public String getUser_bday() {
    return user_bday;
  }

  public int getLogin_status() {
    return login_status;
  }

  public int getService() {
    return service;
  }

  public String getId() {
    return id;
  }
}