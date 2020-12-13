import requests 
import json
import subprocess
import time
# from checkQuantity import getArrayQuantity



device_id = str(subprocess.check_output("/home/pi/Medicinebox/script/device_info", shell=True)).split("\'")[1]
device_id = device_id.replace("\\n", "")
device_id = device_id.replace("\n", "")

apiUrl = 'http://ec2-3-34-54-94.ap-northeast-2.compute.amazonaws.com:65004/quantity'
# quantityArray = getArrayQuantity()


def sendServer(quantityArray) :

    for j in range(0, 6) :
        quantityArray[j] = round(float(quantityArray[j]))
    for i in range(0, 6) :
        sendJson = { "user_device" : device_id , "slot" : str(i+1), "quantity" : str(quantityArray[i]) }
        print(sendJson)
        res = requests.post(url=apiUrl, json=sendJson, 
            headers={"Content-Type" : "application/json"})
        if res.status_code == 200 :
            print(res.text)
            return res.text
        # time.sleep(0.3)





# print "device_id : ", device_id

# def sendServer(param, sendJson) :
#     # requestUrl = "/".join([apiUrl, param])``
#     res = requests.post(url=requestUrl, json=sendJson, 
#         headers={"Content-Type" : "application/json"})
#     if res.status_code == 200 :
#         print(res.text)
#         return res.text
#     print(requestUrl)


