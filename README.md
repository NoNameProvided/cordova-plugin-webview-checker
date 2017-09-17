# cordova-plugin-chrome-webview-checker

Checks whether Android System WebView is enabled or not.

## Notes

Use only ES5 code for checking the version of the Android System WebView (or transpile it to ES5), because if it's not enabled your ES6 code will throw a Syntax Error.

Also do not pass the console function as a parameter like `.then(console.log)`, this will cause an `Uncaught TypeError: Illegal invocation` error.

## API

### isWebViewEnabled()

It returns a promise which will be resolved to `true` if the Android System Webview is enabled and `false` otherwise.

Technically all Android phone has Android System WebView installed but when it's disabled it will defaults to the version shipped with the phone. So you should determine a minimal required version and use `plugins.chromeWebviewChecker.getChromeWebviewVersion` to check if the installed version is higher or not.

```js
plugins.webViewChecker.isWebViewEnabled()
  .then(function(enabled) { console.log(enabled); }))
  .catch(function(error) { console.error(error); }));
```

### getWebViewVersion()

It returns a promise which will be resolved to the string representation of the version number (eg: `57.0.2987.132`) or rejected with a `Package is not found` error if the Android System Webview is not installed

```js
plugins.webViewChecker.getWebViewVersion()
  .then(function(version) { console.log(version); }))
  .catch(function(error) { console.error(error); }));
```

### openGooglePlayPage()

A helper function to open the Google Play page of Android System Webview. Useful for promting the user to update/enable the Android System Webview.

```js
plugins.webViewChecker.openGooglePlayPage()
  .then(function() { console.log('Google Play page of Android System Webview has been opened.'); }))
  .catch(function(error) { console.error(error); }));
```