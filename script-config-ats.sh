#!/bin/bash

cat <<-EOF >> /etc/trafficserver/remap.config
map http://10.10.1.3:8080/myCI/ http://{cache} @action=allow @src_ip=10.10.0.1-10.10.0.254
map http://10.10.1.3:8080/ http://10.10.2.1:80/
EOF

exit 0