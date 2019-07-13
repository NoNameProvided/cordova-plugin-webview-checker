# cordova-plugin-webview-checker

[![NPM version](https://badgen.net/npm/v/cordova-plugin-webview-checker)][npm-package-url]

Cordova plugin that checks which version of Android System Webview is installed.

This plugin helps you to prevent the white screen of death for your Cordova/Ionic applications. Different versions of Android come with a different version of *Android System WebView* installed. Certain JS features work only in newer webview versions, so when a user tries to start a Cordova/Ionic application with an older webview implementation, the JS parser throws an error which will lead to an empty white screen. This plugin can be used to check the available webview version before parsing your main application bundle and show a meaningful error message to the user.

## Installation

```
cordova plugin add cordova-plugin-webview-checker
```

## Usage

**tl,dr;**

- use ES5 code only
- put your code for this plugin directly 
  - into the `index.html`
  - into a separate js file what is included before the application code in `index.html`  
- avoid the list of known issues in the quirks section
- you need to wait for the `deviceready` signal to start using the API

This plugin is used to detect the version of the WebView before launching your app, it should not be used in your application code, but directly added to `index.html` via a script tag (inline or not) to render an error state if the required Webview version is not present.

**Usage of language features above ES5**

Do not use anything else than ES5 to write your handler logic for this plugin and do not bundle it into your main application. The reason for this is that the older versions of webview do not support most of the ES6+ features and can only interpret ES5 code.

### Known issues and quirks

Do not pass the any `console` function as a parameter in a promise handler (like `.then(console.log)`). This will cause an `Uncaught TypeError: Illegal invocation` error in older versions of the Android System WebView.

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
- Android 9.0 (Q) - disabled by default as Chrome 51+ is used for rendering

## API

### isAndroidWebViewEnabled()

Returns a promise which will be resolved to `true` if the Android System Webview is enabled or `false` otherwise.

```js
plugins.webViewChecker.isAndroidWebViewEnabled()
  .then(function(enabled) { console.log(enabled); })
  .catch(function(error) { console.error(error); });
```

> **Note:** Technically all Android phone has the Android System WebView installed but when it's disabled it will default to the version shipped with the phone. So you should determine a minimum required version and use `plugins.webViewChecker.getCurrentWebViewPackageInfo()` function to check if the installed version is higher or not.

--- 

### getAndroidWebViewPackageInfo()

Returns a promise which will be resolved to an object containing partial package info or rejected with a `Package is not found` error if the Android System Webview is not installed.

```js
plugins.webViewChecker.getAndroidWebViewPackageInfo()
  .then(function(packageInfo) { console.log(packageInfo); })
  .catch(function(error) { console.error(error); });
```

Example response: 

```js
{
  packageName: "com.google.android.webview"
  versionName: "69.0.3497.100"
  versionCode: 349710065
}
```

---

### getCurrentWebViewPackageInfo()

Returns a promise which will be resolved to an object containing partial package info or rejected with a `Cannot determine current WebView engine.` error if an unexpected error happened.

```js
plugins.webViewChecker.getCurrentWebViewPackageInfo()
  .then(function(packageInfo) { console.log(packageInfo); })
  .catch(function(error) { console.error(error); });
```

Example response: 

```js
{
  packageName: "com.android.chrome"
  versionName: "69.0.3497.100"
  versionCode: 349710065
}
```

---

### openGooglePlayPage(packageName?: string)

A helper function to open the Google Play page of the currently active WebView engine or the specified package name.

```js
plugins.webViewChecker.openGooglePlayPage()
  .then(function() { console.log('Google Play page has been opened.'); })
  .catch(function(error) { console.error(error); });
```

## License

[MIT](./LICENSE)

[npm-package-url]: https://www.npmjs.com/package/cordova-plugin-webview-checker