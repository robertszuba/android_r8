package com.onegini.mobile.testr8.model;

import androidx.annotation.NonNull;
import com.google.gson.annotations.SerializedName;

public class TestModel1 {

  @SerializedName("name")
  private String name;

  @SerializedName("username")
  private String username;

  @SerializedName("email")
  private String email;

  @NonNull
  @Override
  public String toString() {
    return "name: "+name+" email: "+email;
  }
}
