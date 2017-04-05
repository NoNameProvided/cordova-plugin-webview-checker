const promisifyCordovaExec = (command, params = [], className = 'ChromeWebviewChecker') => {
  return new Promise((resolve, reject) => {
    cordova.exec(resolve, reject, className, command, params);
  });
}

module.exports = {
  isChromeWebviewInstalled: () => { return promisifyCordovaExec('isChromeWebviewInstalled', ['com.google.android.webview']) },
  getChromeWebviewVersion: () => { return promisifyCordovaExec('getChromeWebviewVersion', ['com.google.android.webview']) }
}