package com.nonameprovided.cordova.WebViewChecker;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.PackageManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
* This class echoes a string called from JavaScript.
*/
public class WebViewChecker extends CordovaPlugin {

  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    if (action.equals("isAppEnabled")) {
      String packagename = args.getString(0);
      this.isAppEnabled(packagename, callbackContext);
      return true;
    }

    if (action.equals("getAppVersion")) {
      String packagename = args.getString(0);
      this.getAppVersion(packagename, callbackContext);
      return true;
    }

    if (action.equals("openGooglePlayPage")) {
      String packagename = args.getString(0);
      this.openGooglePlayPage(packagename, callbackContext);
      return true;
    }

    callbackContext.error("Command not found");
    return false;
  }

  public void getAppVersion(String packagename, CallbackContext callbackContext) {
    PackageManager packageManager = this.cordova.getActivity().getPackageManager();

    try {
      PackageInfo info = packageManager.getPackageInfo(packagename, 0);
      String version = info.versionName;
      callbackContext.success(version);
    } catch (PackageManager.NameNotFoundException e) {
      callbackContext.error("Package not found");
    }
  }

  public void isAppEnabled(String packagename, CallbackContext callbackContext) {
    PackageManager packageManager = this.cordova.getActivity().getPackageManager();

    try {
      Boolean enabled = packageManager.getApplicationInfo(packagename, 0).enabled;
      callbackContext.success(enabled.toString());
    } catch (PackageManager.NameNotFoundException e) {
      callbackContext.error("Package not found");
    }
  }

  private void openGooglePlayPage(String packagename, CallbackContext callbackContext)
      throws android.content.ActivityNotFoundException {
    Context context = this.cordova.getActivity().getApplicationContext();
    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packagename));

    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

    try {
      context.startActivity(intent);
      callbackContext.success();
    } catch (Exception e) {
      callbackContext.error("Cannot open Google Play. (" + e.getMessage() + ")");
    }

  }
}