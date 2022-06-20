ONOS INSTALLATION IN MACOS
--------------------------
# Bazel intallation (version 3.7.2 recommended)

$ `export BAZEL_VERSION=3.7.2`

$ `curl -fLO "https://github.com/bazelbuild/bazel/releases/download/${BAZEL_VERSION}/bazel-${BAZEL_VERSION}-installer-darwin-x86_64.sh"`

$ `chmod +x "bazel-${BAZEL_VERSION}-installer-darwin-x86_64.sh"`

$ `./bazel-${BAZEL_VERSION}-installer-darwin-x86_64.sh --user`

$ `export PATH="$PATH:$HOME/bin"`

$ `bazel --version`

# ONOS installation procedure

$ `git clone https://gerrit.onosproject.org/onos`

$ `cd onos`

Edit '.bash_profile' file adding:

export ONOS_ROOT="\`pwd\`"  
source $ONOS_ROOT/tools/dev/bash_profile  

Finally, run:

$ `. ~/.bash_profile`

$ `cd $ONOS_ROOT`

$ `bazel build onos`

After build process, you're set!

RUNNING ONOS
------------
Running a new ONOS controller instance:

$ `cd ~/onos`

$ `bazel run onos-local clean debug`

Once it has started, from a new terminal window, we access Karaf console with:

$ `onos localhost`