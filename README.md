# cordova-plugin-chrome-webview-checker

Checks whether Android System Webview is installed and enabled or not.

## API

Currently there is 2 API call only:

### isChromeWebviewInstalled

It returns a promise which will be resolved to `true` if the Android System Webview is installed and enabled or to `false` otherwise.

Note: Technically all Android phone has Android System Webview but when it's disabled it will defaults to the one shipped with the phone.
So you should determine a minimal reqired version and use `plugins.chromeWebviewChecker.getChromeWebviewVersion` to check if the installed version is higher or not.

```js
plugins.chromeWebviewChecker.isChromeWebviewInstalled()
  .then(function(enabled) { console.log(enabled); }))
  .catch(function(error) { console.error(error); }));
```

### getChromeWebviewVersion

It returns a promise which will be resolved to string representaion of the version number (eg: `57.0.2987.132`) or reject with a `Package is not found` error.

```js
plugins.chromeWebviewChecker.getChromeWebviewVersion()
  .then(function(enabled) { console.log(enabled); }))
  .catch(function(error) { console.error(error); }));
```

## Notes

Do not use arrow functions in the callback as they will break the code if the support package is not installed.
Also do not pass the console function as a parameter like `.then(console.log)`, this will cause an `Uncaught TypeError: Illegal invocation` error.

## TODO

For the 1.0 release the followings have to be done:

- refactor app code to use best practices
- write proper cordova plugin config
- add option to subscribe to changes in avaibility

