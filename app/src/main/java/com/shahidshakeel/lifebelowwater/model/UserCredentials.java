package com.shahidshakeel.lifebelowwater.model;

import java.util.Objects;

public class UserCredentials {
  private String username, password;

  public UserCredentials() {

  }

  public UserCredentials(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public boolean equals(UserCredentials uc) {
    if (uc == null)
      return false;
    if (uc.getUsername().equals(username))
      return uc.getPassword().equals(password);
    return false;
  }
}
