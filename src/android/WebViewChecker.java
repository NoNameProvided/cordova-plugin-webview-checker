package com.nonameprovided.cordova.WebViewChecker;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Build;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;

import java.lang.reflect.Method;

/**
 * This class echoes a string called from JavaScript.
 */
public class WebViewChecker extends CordovaPlugin {

  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    if (action.equals("isAppEnabled")) {
      String packageName = args.getString(0);
      this.isAppEnabled(packageName, callbackContext);

      return true;
    }

    if (action.equals("getAppPackageInfo")) {
      String packageName = args.getString(0);
      this.getAppPackageInfo(packageName, callbackContext);

      return true;
    }

    if (action.equals("openGooglePlayPage")) {
      String packageName = args.getString(0);
      this.openGooglePlayPage(packageName, callbackContext);

      return true;
    }

    if (action.equals("getCurrentWebViewPackageInfo")) {
      this.getCurrentWebViewPackageInfo(callbackContext);

      return true;
    }

    callbackContext.error("Command not found. (" + action.toString() + ")");
    return false;
  }

  /**
   * Returns information about the currently selected WebView engine.
   */
  public void getCurrentWebViewPackageInfo(CallbackContext callbackContext) throws org.json.JSONException {
    PackageInfo pInfo = null;
    JSONObject responseObject = new JSONObject();

    try {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        /* Starting with Android O (API 26) they added a new method specific for this */
        pInfo = WebView.getCurrentWebViewPackage();
      } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        /**
        * With Android Lollipop (API 21) they started to update the WebView
        * as a separate APK with the PlayStore and they added the
        * getLoadedPackageInfo() method to the WebViewFactory class and this
        * should handle the Android 7.0 behaviour changes too.
        */
        Class webViewFactory = Class.forName("android.webkit.WebViewFactory");
        Method method = webViewFactory.getMethod("getLoadedPackageInfo");
        pInfo = (PackageInfo) method.invoke(null);            
      } else {
        /* Before Lollipop the WebView was bundled with the OS. */
        this.getAppPackageInfo("com.google.android.webview", callbackContext);

        /* The getAppPackageInfo function resolves the callbackContext 
         * and returns the same response, so we need to return here. 
         */
        return;
      }

      responseObject.put("packageName", pInfo.packageName);
      responseObject.put("versionName", pInfo.versionName);
      responseObject.put("versionCode", pInfo.versionCode);

      callbackContext.success(responseObject);  
    } catch (Exception e) {
        callbackContext.error("Cannot determine current WebView engine. (" + e.getMessage() + ")");
        
      return;
    }
  }

  /**
   * Returns partial package information about the specified package.
   */
  public void getAppPackageInfo(String packagename, CallbackContext callbackContext) throws org.json.JSONException {
    PackageInfo pInfo = null;
    JSONObject responseObject = new JSONObject();
    PackageManager packageManager = this.cordova.getActivity().getPackageManager();

    try {
      pInfo = packageManager.getPackageInfo(packagename, 0);
    } catch (PackageManager.NameNotFoundException e) {
      callbackContext.error("Package not found");
    }

    responseObject.put("packageName", pInfo.packageName);
    responseObject.put("versionName", pInfo.versionName);
    responseObject.put("versionCode", pInfo.versionCode);

    callbackContext.success(responseObject);
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

  private void openGooglePlayPage(String packagename, CallbackContext callbackContext) throws android.content.ActivityNotFoundException {
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
