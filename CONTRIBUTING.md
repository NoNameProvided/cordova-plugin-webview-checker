# Contributing

## Prerequisites

The following tools are required to test and develop this plugin:

- NodeJS
  - on Windows download the official MSI installer ([download](nodejs))
  - on MacOS install it via homebrew: `brew install nodejs`
- Java SE Development Kit 8 ([download](java-devkit-8), or alternatively [download](java-devkit-8-3rd-paty) without Oracle account)
- Android Studio ([download](android-studio))
- Cordova CLI ([installation guide](cordova-cli))
  - on Windows install it via NPM: `npm install -g cordova`
  - on MacOS/Linux install it via NPM: `npm install -g cordova` (you may need sudo to install it successfully)

[homebrew]: https://brew.sh/
[nodejs]: https://nodejs.org/en/download/
[java-devkit-8-3rd-paty]: https://www.manageengine.com/products/desktop-central/patch-management/Java-SE-Development-Kit-(x64)-patches/jdk-8u211-windows-x64-patch.html
[java-devkit-8]: https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
[android-studio]: https://developer.android.com/studio
[cordova-cli]: https://cordova.apache.org/docs/en/latest/guide/cli/#installing-the-cordova-cli

## Development environment setup

### Creating a test application

To test the changes made to the plugin a test app needs to be created. There is a helper command which setups
an empty Cordova application and installs the plugin.

```
npm run testing:setup
```

The above command will create an empty Cordova application inside the `playground` folder. To test the changes made to
the plugin some test code needs to be added to the `playground/www/index.html` or `playground/www/js/index.js` file.

### Testing changes

To test any changes made to the plugin, the test project needs be compiled again via the following command:

```
npm run testing:start
```

This will:

- re-add the plugin to the test project
- re-build the test project
- install and start the application on the default target

### Reseting the test application

If something went wrong the and the test application is broken, you can remove it via running:

```
npm run testing:reset
```

This command will remove the entire test application. After this you need to setup the test application again via the  
`npm run testing:setup` command.
