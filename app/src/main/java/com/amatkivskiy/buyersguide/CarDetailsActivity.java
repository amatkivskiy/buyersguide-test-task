package com.amatkivskiy.buyersguide;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.amatkivskiy.buyersguide.model.Car;

public class CarDetailsActivity extends AppCompatActivity implements View.OnClickListener {

  private final static String CarExtraName = "selected_car";

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_car_details);

    Car car;
    if (getIntent() != null && getIntent().hasExtra(CarExtraName)) {
      car = getIntent().getParcelableExtra(CarExtraName);
    } else {
      throw new IllegalStateException("Need to pass Car instance to instantiate this activity.");
    }

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    toolbar.setTitle(car.getName());
    toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
    toolbar.setNavigationOnClickListener(this);

    WebView webView = (WebView) findViewById(R.id.car_details_webview);
    webView.setWebViewClient(new WebViewClient());
    webView.loadUrl(car.getUrl());
  }

  public static Intent getStartIntent(Context source, Car selectedCar) {
    Intent intent = new Intent(source, CarDetailsActivity.class);
    intent.putExtra(CarExtraName, selectedCar);

    return intent;
  }

  @Override
  public void onClick(View v) {
    finish();
  }
}
