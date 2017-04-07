function promisifyCordovaExec(command, params, className) {
  params = params || [];
  className = className || 'ChromeWebviewChecker';

  return new Promise(function innerPromise(resolve, reject) {
    cordova.exec(resolve, reject, className, command, params);
  });
}

module.exports = {
  isChromeWebviewInstalled: function isChromeWebviewInstalled() { return promisifyCordovaExec('isChromeWebviewInstalled', ['com.google.android.webview']) },
  getChromeWebviewVersion: function getChromeWebviewVersion() { return promisifyCordovaExec('getChromeWebviewVersion', ['com.google.android.webview']) }
}