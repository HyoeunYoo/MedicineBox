#!/bin/sh

command=$(ifconfig wlan0 | grep inet)
ip_addr=$(echo "${command#*inet}")
ip_addr=$(echo "${ip_addr%netmask*}" | tr -d '[:space:]')
echo ${ip_addr}