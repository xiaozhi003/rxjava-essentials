package com.packtpub.apps.rxjava_essentials.apps;

public class AppInfo implements Comparable<Object> {

  long mLastUpdateTime;

  String mName;

  String mIcon;

  public AppInfo(String name, String icon, long lastUpdateTime) {
    mName = name;
    mIcon = icon;
    mLastUpdateTime = lastUpdateTime;
  }

  public long getLastUpdateTime() {
    return mLastUpdateTime;
  }

  public String getName() {
    return mName;
  }

  public void setName(String name) {
    mName = name;
  }

  public String getIcon() {
    return mIcon;
  }

  public void setIcon(String icon) {
    mIcon = icon;
  }

  @Override public int compareTo(Object another) {
    AppInfo f = (AppInfo) another;
    return getName().compareTo(f.getName());
  }
}
