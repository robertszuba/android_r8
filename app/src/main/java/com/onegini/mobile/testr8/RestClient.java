package com.onegini.mobile.testr8;

import com.onegini.mobile.testr8.model.TestModel1;
import com.onegini.mobile.testr8.model.TestModel2;
import io.reactivex.Single;
import retrofit2.http.GET;

public interface RestClient {

  @GET("users/1")
  Single<TestModel1> getUserDetails();

  @GET("users/1")
  Single<TestModel2> getOtherUserDetails();
}
