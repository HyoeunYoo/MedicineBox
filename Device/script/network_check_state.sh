command=$(iwconfig wlan0 | grep ESSID)
n_state=$(echo "${command#*:}" | tr -d '[:space:]')
echo ${n_state}