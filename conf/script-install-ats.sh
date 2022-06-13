#!/bin/bash

sudo apt-get update
sudo apt-get install trafficserver -y
cd /var/log/trafficserver
traffic_manager
mkdir /var/run/trafficserver
chown -R trafficserver:trafficserver /var/run/trafficserver
sudo service trafficserver start
echo "Starting ATS service..."
sleep 10 &
wait
traffic_ctl config set proxy.config.url_remap.remap_required 0
echo "Configuring mapping settings..."
sleep 10 &
wait
traffic_ctl config set proxy.config.reverse_proxy.enabled 0
echo "Enabling reverse proxy mode..."
sleep 10 &
wait
traffic_ctl config set proxy.config.http.cache.required_headers 0
echo "Allowing caching of content..."
sleep 10 &
wait
traffic_ctl config set proxy.config.http_ui_enabled 1
echo "Activating cache UI..."
sleep 10 &
wait
sudo bash /tmp/script-config-ats.sh;
sudo service trafficserver restart

exit 0