#!/bin/bash

sudo apt-get install apache2 -y
cd /tmp
wget https://github.com/Dash-Industry-Forum/dash.js/archive/v2.9.2.zip 
cd /var/www/html
sudo unzip /tmp/v2.9.2.zip
sudo chmod g+r -R /var/www/html
sudo chgrp www-data -R /var/www/html
cat <<-EOF >> /etc/apache2/apache2.conf
<Directory /var/www/html/bbb>
        Order Allow,Deny
        Allow from all
        AllowOverride all
        Header set Access-Control-Allow-Origin "*"
</Directory>
EOF
sudo a2enmod headers
systemctl restart apache2

exit 0