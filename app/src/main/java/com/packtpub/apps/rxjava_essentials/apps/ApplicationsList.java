package com.packtpub.apps.rxjava_essentials.apps;

import java.util.List;

import lombok.experimental.Accessors;

@Accessors(prefix = "m")
public class ApplicationsList {

  private static ApplicationsList ourInstance = new ApplicationsList();

  private List<AppInfo> mList;

  private ApplicationsList() {
  }

  public static ApplicationsList getInstance() {
    return ourInstance;
  }

  public List<AppInfo> getList() {
    return mList;
  }

  public void setList(List<AppInfo> list) {
    mList = list;
  }
}
