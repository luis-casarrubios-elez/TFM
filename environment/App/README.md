APP COMPILATION AND INSTALLATION
--------------------------------

Disclaimer for MacOS Big Sur users, I had some troubles with Python dependencies, so before the process I had to run:

$ `cd ~`  
$ `python2.7 -m ensurepip --default-pip  # https://stackoverflow.com/questions/18363022/importerror-no-module-named-pip`  
$ `pip install requests`

I recommend to add your current jdk version to your .bash_profile, to avoid any Maven compilation errors:

$ `vim ~/.bash_profile`  
>export JAVA_HOME=$(/usr/libexec/java_home)  
$ `source ~/.bash_profile`

Since I want to build an application against the current ONOS master, i.e. an unreleased version, I will need to first build ONOS yourself and then publish the resulting artifacts in the Maven repository under ~/.m2/repository by running the following command:

$ `onos-publish -l`

Also, I will have to build the current version of the ONOS application archetypes as follows:

$ `cd ~/onos/tools/package/archetypes`

$ `mvn clean install`

Now, you should check your ONOS version by executing in the Karaf console:

\>summary

My ONOS version is 3.0.0, and it is the current ONOS master, so now let's define the correct POM version:

$ `export ONOS_POM_VERSION=3.0.0-SNAPSHOT`

Let's create the Template Application now by running:

$ `onos-create-app app org.foo foo-app 3.0.0-SNAPSHOT org.foo.app`

And this should generate the new project for you, now to build it and load it into ONOS:

$ `cd foo-app`

$ `mvn clean install`

And once the build success message appears, run the command to install the application into the running ONOS instance (or cluster), you can use the onos-app tool, which uses ONOS REST API within, to upload the .oar file generated in the previous step. Note that by using the exclamation mark with the install parameter, the application will be activated immediately after being installed:


$ `onos-app localhost install! target/foo-app-3.0.0-SNAPSHOT.oar`

If you modify the app source code, you must re-compile and reinstall it in your controller:

$`mvn clean install`

$`onos-app localhost reinstall! target/foo-app-3.0.0-SNAPSHOT.oar`