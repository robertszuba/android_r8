package com.onegini.mobile.testr8;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.onegini.mobile.testr8.model.TestModel1;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

  private RestClient restClient;
  private TextView textView;
  private Button anonymousButton;
  private Button lambdaButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    textView = findViewById(R.id.textView);
    anonymousButton = findViewById(R.id.button_anonymous);
    lambdaButton = findViewById(R.id.button_lambda);
    restClient = prepareRestClient();

    anonymousButton.setOnClickListener(v -> fetchData());
    lambdaButton.setOnClickListener(v -> fetchDataLambda());
  }

  @SuppressWarnings("ResultOfMethodCallIgnored")
  @SuppressLint("CheckResult")
  private void fetchData() {
    restClient.getUserDetails()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<TestModel1>() {
          @Override
          public void accept(final TestModel1 testModel1) throws Exception {
            textView.setText("Fetch successfull {" + testModel1.toString() + "}");
          }
        }, new Consumer<Throwable>() {
          @Override
          public void accept(final Throwable throwable) throws Exception {
            textView.setText("Fetch failure: " + throwable.getMessage());
            throwable.printStackTrace();
          }
        });
  }

  @SuppressWarnings("ResultOfMethodCallIgnored")
  @SuppressLint("CheckResult")
  private void fetchDataLambda() {
    restClient.getOtherUserDetails()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            testModel2 -> textView.setText("Fetch successfull {" + testModel2.toString() + "}"),
            throwable -> {
              textView.setText("Fetch failure: " + throwable.getMessage());
              throwable.printStackTrace();
            });
  }

  private RestClient prepareRestClient() {
    final OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
    final Retrofit retrofit = new Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build();

    return retrofit.create(RestClient.class);
  }
}
