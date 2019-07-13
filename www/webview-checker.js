/**
 * List of possible WebView engines package names.
 */
var POSSIBLE_WEBVIEW_ENGINES = {
  AndroidSystemWebView: "com.google.android.webview",
  GoogleChrome: "com.android.chrome",
  GoogleChromeBeta: "com.chrome.beta",
  GoogleChromeCanary: "com.chrome.canary",
  GoogleChromeDev: "com.chrome.dev"
};

/**
 * Helper function what turns the callback based cordova.exec command into a Promise based one.
 * 
 * @param {string} action The action name to call on the native side. This generally corresponds to the native class method.
 * @param {Array<string | number | boolean>} params An array of arguments to pass into the native environment.
 * @param {string} className The service name to call on the native side. This corresponds to a native class.
 */
function promisifyCordovaExec(action, params, className) {
  params = params || [];
  className = className || 'WebViewChecker';

  return new Promise(function promiseHandler(resolve, reject) {
    var rejecter = function rejecter(error) {
      console.error('[Android Webview Checker] Error:', error);
      reject(error)
    };

    cordova.exec(resolve, rejecter, className, action, params);
  });
}

/**
 * Check if default Android System Webview (com.google.android.webview) is enabled or not.
 * 
 * @returns {Promise<boolean>}
 */
function isAndroidWebViewEnabled() {
  return promisifyCordovaExec('isAppEnabled', ['com.google.android.webview']).catch(function (error) {
    console.warn('[Android Webview Checker]: Error while trying to load information for Android WebView, falling back to "com.android.webview"!');
    return promisifyCordovaExec('isAppEnabled', ['com.android.webview']);
  });
}

/**
 * Gets the version of the default Android System Webview (com.google.android.webview).
 * 
 * @returns {Promise<{ packageName: string, versionName: string, versionCode: number }>}
 */
function getAndroidWebViewPackageInfo() {
  return promisifyCordovaExec('getAppPackageInfo', ['com.google.android.webview']).catch(function (error) {
    console.warn('[Android Webview Checker]: Error while trying to load information for Android WebView, falling back to "com.android.webview"!');
    return promisifyCordovaExec('getAppPackageInfo', ['com.android.webview']);
  });
}

/**
 * Returns partial package information about the currently selected WebView engine.
 * 
 * @returns {Promise<{ packageName: string, versionName: string, versionCode: number }>}
 */
function getCurrentWebViewPackageInfo() {
  return promisifyCordovaExec('getCurrentWebViewPackageInfo');
}

/**
 * Opens the Google Play page of the currently active WebView engine or the 
 * specified package name.
 * 
 * @param {string} [packageName] the package name to open the store for (OPTIONAL) 
 * 
 * @returns {Promise<void>}
 */
function openGooglePlayPage(packageName) {
  if (typeof packageName == 'string') {
    return promisifyCordovaExec('openGooglePlayPage', [packageName]);
  } else {
    return getCurrentWebViewPackageInfo().then(function (packageInfo) {
      return promisifyCordovaExec('openGooglePlayPage', [packageInfo.packageName]);
    });
  }
}

module.exports = {
  POSSIBLE_WEBVIEW_ENGINES: POSSIBLE_WEBVIEW_ENGINES,
  isAndroidWebViewEnabled: isAndroidWebViewEnabled,
  getAndroidWebViewPackageInfo: getAndroidWebViewPackageInfo,
  getCurrentWebViewPackageInfo: getCurrentWebViewPackageInfo,
  openGooglePlayPage: openGooglePlayPage
}