import RPi.GPIO as GPIO
import time
import requests 
import json
import subprocess
import os
import sys
from showNeopixel import blink, showLoading, keepBlink, showQuantity

BUTTON = 2
GPIO.setmode(GPIO.BCM)
GPIO.setup(BUTTON, GPIO.IN)
flag = 0
longpush = 0
device_id = str(subprocess.check_output("/home/pi/Medicinebox/script/device_info", shell=True)).split("\'")[1]
device_id = device_id.replace("\\n", "")
device_id = device_id.replace("\n", "")



apiUrl = 'http://ec2-3-34-54-94.ap-northeast-2.compute.amazonaws.com:65004/wifi'
deviceInfo = { "device_id" : device_id }
print("device_id : ", device_id)
 

def getWifiInfo() :
    res = requests.get(url=apiUrl+"?device_id="+device_id,  
        headers={"Content-Type" : "application/json"})

    if res.status_code == 200 :
        return res.json()[0]

def deleteServerWifiInfo() :
    res = requests.delete(url=apiUrl, json=deviceInfo, 
        headers={"Content-Type" : "application/json"})
    if res.status_code == 200 :
        print(res.text)
        return res.text

def updateDeviceIp() :
    device_ip=""
    while device_ip == "" or len(device_ip) > 16  :
        print("wifi checking...")
        time.sleep(1)
        device_ip = str(subprocess.check_output("/home/pi/Medicinebox/script/network_ip_addr.sh", shell=True)).split("\'")[1]
        device_ip = device_ip.replace("\\n", "")
        device_ip = device_ip.replace("\n", "")
        time.sleep(1)
    print("ip : " + device_ip)
    deviceIp = { "device_id" : device_id, "device_ip" : device_ip }
    
    res = requests.put(url=apiUrl, json=deviceIp, 
        headers={"Content-Type" : "application/json"})
    if res.status_code == 200 :
        print(res.text)
        return res.text

try :
    print("push BUTTON double tap or long push")
    while True:
        if GPIO.input(BUTTON) != True:
            print("Pushed!!!")
            if flag == 0 or flag == 2:
                flag += 1
            longpush += 1
        elif flag == 1 :
            flag += 1


        if longpush >= 15 :
            print("Long Pushed!!!")      
            break
        if flag == 3 :
            print("Double tapped!!")
            break
        
        time.sleep(0.2)

    if longpush >= 15 :
        # showLoading()
        print("NETWORK INITIALIZATION!!!")
        os.system("/home/pi/Medicinebox/script/init_wifi.sh")
        print("NETWORK RESTART!!!")
        os.system("/home/pi/Medicinebox/script/network_restart.sh")
       # keepBlink('g')
        

        longpush = 0
        flag = 0
    
    elif flag == 3 :
        # showLoading()
        wifiData = getWifiInfo()
        wifi_id = wifiData['wifi_id']
        wifi_pw = wifiData['wifi_pw']
        print("Wifi : " + wifi_id + ", passwd : " + wifi_pw)
        command = "/home/pi/Medicinebox/script/configure_wifi.sh \'" + wifi_id + "\' \'" + wifi_pw + "\'"
        print(command)
        subprocess.call("/home/pi/Medicinebox/script/configure_wifi.sh \"" + wifi_id + "\" \"" + wifi_pw + "\"", shell=True)
        # subprocess.call("/home/pi/Medicinebox/script/configure_wifi.sh \'" + wifi_id + "\' \'" + wifi_pw + "\'", shell=True)
        network_state = str(subprocess.check_output("/home/pi/Medicinebox/script/network_check_state.sh", shell=True)).split("\'")[1]
        network_state = network_state.replace("\\n", "")
        network_state = network_state.replace("\n", "")
        print("network connected to : " + network_state)
        longpush = 0
        flag = 0
        loopNum = 0
        while True :
            if network_state != "off/any" :
                # result = deleteServerWifiInfo()
                print("network1 connected to : " + network_state)
                result = updateDeviceIp()
                print(result)
                blink('b')
                showQuantity()
                break
            else :
                print("Not Connected yet..")
                print("network2 connected to : " + network_state)
                network_state = str(subprocess.check_output("/home/pi/Medicinebox/script/network_check_state.sh", shell=True)).split("\'")[1]
                network_state = network_state.replace("\\n", "")
                network_state = network_state.replace("\n", "")
                time.sleep(3)
                if loopNum > 20 :
                    print("ERROR: CAN'T NOT CONNECT TO ",wifi_id)
                    blink('r')
                    showQuantity()
                    break
                loopNum = loopNum + 1
        network_state = ""
        
except KeyboardInterrupt :
    GPIO.cleanup()
