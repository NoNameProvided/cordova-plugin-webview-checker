package com.nonameprovided.cordova.ChromeWebviewChecker;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.PackageManager;

/**
* This class echoes a string called from JavaScript.
*/
public class ChromeWebviewExistenceChecker extends CordovaPlugin {

  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    if (action.equals("isChromeWebviewInstalled")) {
      String packagename = args.getString(0);
      this.isChromeWebviewInstalled(packagename, callbackContext);
      return true;
    }

    if (action.equals("getChromeWebviewVersion")) {
      String packagename = args.getString(0);
      this.getChromeWebviewVersion(packagename, callbackContext);
      return true;
    }
    callbackContext.error("Package is not found");

    return false;
  }

  public boolean getChromeWebviewVersion(String packagename, CallbackContext callbackContext) {
    PackageManager packageManager = this.cordova.getActivity().getPackageManager();

    try {
      PackageInfo info = packageManager.getPackageInfo(packagename, 0);
      String installed = info.versionName;
      callbackContext.success(installed);
      return true;
    } catch (PackageManager.NameNotFoundException e) {
      callbackContext.error("Package is not found");
      return false;
    }
  }

  public boolean isChromeWebviewInstalled(String packagename, CallbackContext callbackContext) {
    PackageManager packageManager = this.cordova.getActivity().getPackageManager();

    try {
      Boolean installed = packageManager.getApplicationInfo(packagename, 0).enabled;
      callbackContext.success(installed.toString());
      return true;
    } catch (PackageManager.NameNotFoundException e) {
      callbackContext.success("false");
      // callbackContext.error("Package is not found");
      return false;
    }
  }
}