package com.packtpub.apps.rxjava_essentials.chapter8.api.stackexchange;

import com.packtpub.apps.rxjava_essentials.chapter8.api.stackexchange.models.User;
import com.packtpub.apps.rxjava_essentials.chapter8.api.stackexchange.models.UsersResponse;
import java.util.List;
import lombok.experimental.Accessors;
import retrofit.Retrofit;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

@Accessors(prefix = "m") public class SeApiManager {

  private final StackExchangeService mStackExchangeService;

  public SeApiManager() {
      Retrofit sRetrofit = new Retrofit.Builder().baseUrl("https://api.stackexchange.com")
        .build();

    mStackExchangeService = sRetrofit.create(StackExchangeService.class);
  }

  public Observable<List<User>> getTenMostPopularSOusers() {
//    return mStackExchangeService.getTenMostPopularSOusers()
//        .map(UsersResponse::getUsers)
//        .subscribeOn(Schedulers.io())
//        .observeOn(AndroidSchedulers.mainThread());
      return mStackExchangeService.getTenMostPopularSOusers()
              .map(new Func1<UsersResponse, List<User>>() {
                  @Override
                  public List<User> call(UsersResponse usersResponse) {
                      return usersResponse.getUsers();
                  }
              })
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread());
  }

  public Observable<List<User>> getMostPopularSOusers(int howmany) {
//    return mStackExchangeService.getMostPopularSOusers(howmany)
//        .map(UsersResponse::getUsers)
//        .subscribeOn(Schedulers.io())
//        .observeOn(AndroidSchedulers.mainThread());
      return mStackExchangeService.getMostPopularSOusers(howmany)
              .map(new Func1<UsersResponse, List<User>>() {
                  @Override
                  public List<User> call(UsersResponse usersResponse) {
                      return usersResponse.getUsers();
                  }
              })
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread());
  }
}
