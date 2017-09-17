# cordova-plugin-webview-checker

Checks whether Android System WebView is enabled or not.

## Installation

```
cordova plugin add cordova-plugin-webview-checker
```

## Usage

As this plugin is used to detect the version of the WebView before launching your app, it should not be used in your application code, but directly added to `index.html` via a script tag to render an error state if the required WebView is not present.

## Notes

The Android System WebView was first shipped in Android 4.4 (KitKat) and based on Chrome for Android 30. Every phone which comes with Google Play installed has it From Android 4.4. In Android 5.0 (Lollipop) the WebView has been decoupled from the OS and become updatable through the Play Store. Above Android 7.0 the Android System WebView will be disabled and won't be updated as long as Chrome is installed and enabled.

### Pre-installed Versions

Every Android release since Android 4.4 (KitKat) comes pre-installed with Android System WebView. The pre-installed versions are the followings:

- Android 4.4 (KitKat) - Android System WebView `30.0.0.0`
- Android 4.4.3 - Android System WebView `33.0.0.0`
- Android 5.0 (Lollipop) - Android System WebView `37.0.0.0`
- Android 6.0 (Marshmallow) - Android System WebView `44.0.2403.117`
- Android 7.0 (Nougat) - disabled by default as Chrome 51+ is used for rendering
- Android 8.0 (Oreo) - disabled by default as Chrome 51+ is used for rendering

### Usage of language features above ES5

In short, do not use anything else than ES5 to write your handler logic for this plugin and do not bundle it into your main application. The reason for this is that the older versions which are shipped do not support most of the ES6+ features and can only interpret ES5 code.

Also do not pass the console function as a parameter like `.then(console.log)`, this will cause an `Uncaught TypeError: Illegal invocation` error in older versions of the Android System WebView.

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

A helper function to open the Google Play page of Android System Webview. Useful for prompting the user to update/enable the Android System Webview.

```js
plugins.webViewChecker.openGooglePlayPage()
  .then(function() { console.log('Google Play page of Android System Webview has been opened.'); }))
  .catch(function(error) { console.error(error); }));
```