package com.packtpub.apps.rxjava_essentials.chapter8.api.openweathermap;

import com.packtpub.apps.rxjava_essentials.chapter8.api.openweathermap.models.WeatherResponse;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class OpenWeatherMapApiManager {

  private static OpenWeatherMapApiManager instance = new OpenWeatherMapApiManager();
  private final OpenWeatherMapService mOpenWeatherMapService;

  private OpenWeatherMapApiManager() {
    Retrofit restAdapter = new Retrofit.Builder().baseUrl("http://api.openweathermap.org")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build();

    mOpenWeatherMapService = restAdapter.create(OpenWeatherMapService.class);
  }

  public static OpenWeatherMapApiManager getInstance() {
    return instance;
  }

  public Observable<WeatherResponse> getForecastByCity(String city) {
    return mOpenWeatherMapService.getForecastByCity(city)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }
}
