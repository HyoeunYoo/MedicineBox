device_id=$(/home/pi/Medicinebox/script/device_info)
origin_ssid=Medicinebox_$device_id
origin_psk=mdb_$device_id

echo "origin_ssid : $origin_ssid"
echo "origin_psk : $origin_psk"

wifi_id=$1
wifi_pw=$2

echo "wifi_id : "$1", wifi_pw : "$2

sudo sed -i "6s/$origin_ssid/$wifi_id/;7s/$origin_psk/$wifi_pw/" /etc/wpa_supplicant/wpa_supplicant.conf

sudo wpa_cli -i wlan0 reconfigure
sudo ifconfig wlan0 down
sleep1
sudo ifconfig wlan0 up