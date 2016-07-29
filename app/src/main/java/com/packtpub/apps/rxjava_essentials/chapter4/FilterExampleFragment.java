package com.packtpub.apps.rxjava_essentials.chapter4;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.packtpub.apps.rxjava_essentials.R;
import com.packtpub.apps.rxjava_essentials.apps.AppInfo;
import com.packtpub.apps.rxjava_essentials.apps.ApplicationAdapter;
import com.packtpub.apps.rxjava_essentials.apps.ApplicationsList;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class FilterExampleFragment extends Fragment {

  @Bind(R.id.fragment_first_example_list)
  RecyclerView mRecyclerView;

  @Bind(R.id.fragment_first_example_swipe_container)
  SwipeRefreshLayout mSwipeRefreshLayout;

  private ApplicationAdapter mAdapter;

  private ArrayList<AppInfo> mAddedApps = new ArrayList<>();

  public FilterExampleFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_example, container, false);
  }

  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(this, view);

    mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

    mAdapter = new ApplicationAdapter(new ArrayList<>(), R.layout.applications_list_item);
    mRecyclerView.setAdapter(mAdapter);

    mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.myPrimaryColor));
    mSwipeRefreshLayout.setProgressViewOffset(false, 0,
        (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24,
            getResources().getDisplayMetrics()));

    // Progress
    mSwipeRefreshLayout.setEnabled(false);
    mSwipeRefreshLayout.setRefreshing(true);
    mRecyclerView.setVisibility(View.GONE);

    List<AppInfo> apps = ApplicationsList.getInstance().getList();

    loadList(apps);
  }

  private void loadList(List<AppInfo> apps) {
    mRecyclerView.setVisibility(View.VISIBLE);

    Observable.from(apps)
        .filter(new Func1<AppInfo, Boolean>() {
          @Override
          public Boolean call(AppInfo appInfo) {
            return appInfo.getName().startsWith("A");
          }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<AppInfo>() {
          @Override
          public void onCompleted() {
            mSwipeRefreshLayout.setRefreshing(false);
          }

          @Override
          public void onError(Throwable e) {
            Toast.makeText(getActivity(), "Something went south!", Toast.LENGTH_SHORT).show();
            mSwipeRefreshLayout.setRefreshing(false);
          }

          @Override
          public void onNext(AppInfo appInfo) {
            mAddedApps.add(appInfo);
            mAdapter.addApplication(mAddedApps.size() - 1, appInfo);
          }
        });
  }
}
