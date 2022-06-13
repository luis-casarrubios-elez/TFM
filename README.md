# TFM
## Development of Mechanisms for Application Handover in Edge Computing Environments using Software-Defined Networks

This is the GitHub repository for my Master final project. 

**ARRANCAR:**

Ejecutar "initScript.py" en el mismo directorio en el que se encuentre "Dockerfile"

`python initScript.py`

Con esto, tendremos un contenedor Docker con traffic server corriendo listo para usarse, conectado a un ovs-bridge "ovsNet" mediante la interfaz "veth0" con la dirección IP "10.2.0.1" (Importante tener en cuenta que traffic server funciona en el puerto 8080 por defecto).

Esta configuración nos permite integrar el contenedor en un escenario de red virtualizado en nuestro host.

También tendríamos la opción de acceder a nuestro Proxy desde un navegador si configuramos una ruta para ello con el comando:

`sudo ip route add 10.2.0.1 via 172.17.0.1 dev docker0`

(Es recomendable usar Google Chrome con el complemento "SwitchyOmega" para gestionar nuestros Proxy personales).

**DESTRUIR:**

Ejecutar "destroyScript.py", lo que borrará la configuración generada por el script previo.

`python destroyScript.py`

No olvidar borrar también la ruta ip si la hemos añadido, ya que no está automatizado:

`sudo ip route del 10.2.0.1 via 172.17.0.1 dev docker0`