#!/bin/bash

cat <<-EOF >> /etc/apache2/apache2.conf
<Directory /var/www/html/bbb>
        Order Allow,Deny
        Allow from all
        AllowOverride all
        Header set Access-Control-Allow-Origin "*"
</Directory>
EOF

exit 0