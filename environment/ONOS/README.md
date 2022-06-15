INSTALACIÓN ONOS EN MACOS
-------------------------
Instalación Bazel (tiene que ser la 3.7.2)
$ `export BAZEL_VERSION=3.7.2`

$ `curl -fLO "https://github.com/bazelbuild/bazel/releases/download/${BAZEL_VERSION}/bazel-${BAZEL_VERSION}-installer-darwin-x86_64.sh"`

$ `chmod +x "bazel-${BAZEL_VERSION}-installer-darwin-x86_64.sh"`

$ `./bazel-${BAZEL_VERSION}-installer-darwin-x86_64.sh --user`

$ `export PATH="$PATH:$HOME/bin"`

$ `bazel --version`

Y deberíamos obtener la versión que queremos

Ahora procedemos con la instalación de ONOS

$ `git clone https://gerrit.onosproject.org/onos`

$ `cd onos`

$ `cat << EOF >> ~/.bash_profile
export ONOS_ROOT="`pwd`"
source $ONOS_ROOT/tools/dev/bash_profile
EOF`

$ `. ~/.bash_profile`

$ `cd $ONOS_ROOT`

$ `bazel build onos`

Y listo tras un rato haciendo el build


INSTALACIÓN VM MININET EN MACOS
-------------------------------
Como no se puede correr de manera nativa nos descargamos la .ovf para VirtualBox. Tras un buen rato prueba error, la que funciona con ONOS sin problema es la de Ubuntu 18.04. No tiene más que meter la máquina descargada y antes de arrancarla poner el adaptador de red en modo Bridged, también he descubierto que es la única manera de que pueda interactuar con la VM por ssh sin problema. Por último, he tenido que ejecutar lo siguiente en la VM porque por algún motivo las claves estaban vacías:

$ `ssh-keygen -t rsa -f /etc/ssh/ssh_host_rsa_key`

$ `ssh-keygen -t ecdsa -f /etc/ssh/ssh_host_ecdsa_key`

$ `ssh-keygen -t ed25519 -f /etc/ssh/ssh_host_ed25519_key`


PONER EN MARCHA EL ESCENARIO
----------------------------
Arrancamos ONOS:

$ `cd ~/onos`

$ `ok [clean] [debug]`

Cuando arranque, desde otra ventana nos conectamos a la consola de karaf:

$ `onos localhost`

Arrancamos mininet-vm y accedemos a ella por ssh:

$ `VBoxHeadless --startvm "Mininet-VM"`

$ `ssh mininet@192.168.0.32`

CAMBIAR A VAGRANT(?)

Arrancamos escenario de prueba dentro de la vm y conectamos el controlador:

$ `sudo mn --mac --controller=remote,ip=192.168.0.14 (--topo linear,3)`

Y YA TODO DEBE FUNCIONAR COMO EN LA VM QUE TENÍA ANTES, PERO CON UNA RESPUESTA MUCHO MEJOR (DE LA UI SOBRETODO)