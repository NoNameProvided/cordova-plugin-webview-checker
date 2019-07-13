# Changelog and release notes

_This changelog follows the [keep a changelog][1]_ format to maintain a human readable changelog.

## 1.0.0 - 2019-07-13 [BREAKING CHANGE]

### Added

- add `isAndroidWebViewEnabled` function to allow to checki if the default Android System WebView is enabled or not
- add `getAndroidWebViewPackageInfo` function to allow to check the version of the  Android System WebView
- add `getCurrentWebViewPackageInfo` function to allow to check what is the currently selected WebView engine
- add `POSSIBLE_WEBVIEW_ENGINES` constant exposing all available WebView engine package names

### Changed 

- `openGooglePlayPage` function now accepts an optional parameter specifiying a package name
- `README.md` intro is rewritten to better explain the functionality of the plugin

### Removed

- remove `isWebViewEnabled()` function
- remove `getWebViewVersion()` function

## 0.9.2

### Added

- add `openGooglePlayPage` function to allow prompting the user to re-enable the Android System WebView

### Changed

- rename plugin from `cordova-plugin-chrome-webview-checker` to `cordova-plugin-webview-checker`

## 0.9.1

### Changed

- refactor JS code to ES5

## 0.9.0

Initial release.

[1]: http://keepachangelog.com/en/1.0.0/