# -*- coding: utf-8 -*-
from flask import Flask, request, jsonify
import os, ntpath
import json
import subprocess
import RPi.GPIO as GPIO
import time
from dosing import dosing1
from checkQuantity import measureQuantity, getQuantity
from entrance import slotOpen, slotClose
from showNeopixel import openDoor, closeDoor, showQuantity

app = Flask(__name__)

# GPIO.setmode(GPIO.BCM)
slotOpenStatus = [False, False, False, False, False, False]
# def dosing(slot) :
#     GPIO.setwarnings(False)
#     servolist = [6, 5, 12, 7, 8, 25]
#     GPIO.setmode(GPIO.BCM)
#     GPIO.setup(servolist[slot], GPIO.OUT)
#     servo = GPIO.PWM(servolist[slot], 50)
#     servo.start(12)

#     if slot >= 0 & slot < 6 :
#         try :
#             servo.ChangeDutyCycle(9.0)
#             time.sleep(0.5)
#             servo.ChangeDutyCycle(12.5)
#             time.sleep(1)

#             servo.stop()
#             GPIO.cleanup()
#             return "true"
#         # except KeyboardInterrupt :
#         #     servo.stop()
#         #     GPIO.cleanup()

#         except :
#             servo.stop()
#             GPIO.cleanup()
#             return "false"


@app.route('/dosing', methods = ['POST'])
def dosingApp():
    data = request.json
    print(data)
    slot = int(data['slot'])
    print (slot)
    result = dosing1(slot)
    if(result == "true"):
        return "true"
    elif(result == "algument_error") :
        return "Check slot number!"
    else :
        return "Fail to Dosing!" 

    # return result




@app.route('/getQuantity', methods = ["GET", "POST"])
def getQuantityApp():
    if request.method == "POST" :
        if measureQuantity() == "true" :
            result = getQuantity()
        else :
            result = "Measure Error"
    else :
        print("asd")
        result = getQuantity()
    return result

@app.route('/open/<int:slot_num>', methods = ["GET"])
def openSlotApp(slot_num) :
    slot = slot_num - 1
    # if slotOpenStatus[slot] == False :
    #     if slotOpen(slot) == "true" :
    #         slotOpenStatus[slot] = True
    #         return "open success"
    #     else : return "open fail"
    # elif slotOpenStatus[slot] == True :
    #     if slotClose(slot) == "true" :
    #         slotOpenStatus[slot] = False
    #         return "close success"
    #     else : return "close fail"

    if slotOpenStatus[slot] == False :
        # try :
        resultOpen = slotOpen(slot)
        slotOpenStatus[slot] = True
        openDoor(slot)
        # time.sleep(3)
        resultClose = slotClose(slot)
        slotOpenStatus[slot] = False
        closeDoor(slot)
        showQuantity()
        if resultOpen == "true" and resultClose == "true" :
            return "open success"
        else : return "open fail"

    elif slotOpenStatus[slot] == True :
        # try :
        resultClose = slotClose(slot)
        slotOpenStatus[slot] = False
        if resultClose == "true" :
            return "close success" 
        
        else : return "close fail"


    else :
        return "false"


if __name__=='__main__':
    app.run(host='0.0.0.0', port=60002, debug=True)

