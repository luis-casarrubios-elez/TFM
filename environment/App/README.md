APP COMPILATION AND INSTALLATION
--------------------------------

Once you have a running instance of ONOS, to install a new custom app do the following:

$`mvn clean install`

And once the build success message appears, run the command:

$`onos-app localhost install! target/foo-app-3.0.0-SNAPSHOT.oar`

If you modify the app source code, you must re-compile and reinstall it in your controller:

$`mvn clean install`

$`onos-app localhost reinstall! target/foo-app-3.0.0-SNAPSHOT.oar`