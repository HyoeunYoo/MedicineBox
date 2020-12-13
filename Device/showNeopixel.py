import neopixel
import time
import board
import subprocess
import asyncio

num_pixels = 6

pixels = neopixel.NeoPixel(board.D18, num_pixels)

quantityFile = "/home/pi/Medicinebox/script/quantity"
# GPIO.cleanup()
# class AsyncCounter:
#     def __init__(self, stop):
#         self.current = 0
#         self.stop = stop
 
#     def __aiter__(self):
#         return self
 
#     async def __anext__(self):
#         if self.current < self.stop:
#             await asyncio.sleep(0.0001)
#             r = self.current
#             self.current += 1
#             return r
#         else:
#             raise StopAsyncIteration

def showQuantity() :
    readData = str(subprocess.check_output("cat " + quantityFile, shell=True)).split('\'')[1]     # python3
    readData = str(readData).replace("\n", "")
    readData = str(readData).replace("\\n", "")
    qtt = readData.split("//")[0]
    qtt = qtt.strip()
    qtt = qtt.replace("[", "")
    qtt = qtt.replace("]", "")
    mtime = readData.split("//")[1]
    mtime = mtime.strip()

    slot = []

    for i in range(0, 6) :
        slot.append(qtt.split(",")[i])
        slot[i] = float(slot[i].strip())
        print("slot ", i, " : ", slot[i])
    print(qtt)
    print(mtime)

    led = []
    for i in range(0, 6) :
        if slot[i] < 2 :
            led.append("GREEN")
        elif slot[i] >= 2 and slot[i] < 5 :
            led.append("YELLOW")
        else :
            led.append("RED")

    for i in range(0, 6) :
        if led[i] == "GREEN":
            pixels[i] = (0, 255, 0)
        elif led[i] == "YELLOW" :
            pixels[i] = (128, 128, 0)
        elif led[i] == "RED" :
            pixels[i] = (255, 0, 0)
    pixels.show()

def showLoading() :
    pixels.fill((0, 0, 0))

    while True :
        for i in range (0, 3) :
            bright = [0, 50, 125, 200, 125, 50, 0]
            for j in range(0, 6) :
                for z in range(0, 6) :
                    if i == 0 :
                        if z <= j :
                            pixels[z] = (250-bright[z], bright[z], bright[z])
                        else :
                            pixels[z] = (250, 250, 250)
                    elif i == 1 :
                        if z <= j :
                            pixels[z] = (bright[z], 250-bright[z], bright[z])
                        else :
                            pixels[z] = (250, 250, 250)
                    elif i == 2 :
                        if z <= j :
                            pixels[z] = (bright[z], bright[z], 250-bright[z])
                        else :
                            pixels[z] = (250, 250, 250)
                yield
                pixels.show()
                time.sleep(0.1)
                tmp = bright[0]
                for z in range(0, 5) :
                    bright[z] = bright[z+1]
                bright[5] = tmp

# async def asyncShowLoading() :
#     pixels.fill((0, 0, 0))

#     # while True :
#     # for i in range (0, 3) :
#     # if sig == "true" :
#     async for i in AsyncCounter(3) :
#         # for i in range(0, 3)
#         bright = [0, 50, 125, 200, 125, 50, 0]
#         # for j in range(0, 6) :
#         async for j in AsyncCounter(6) :
#             # for z in range(0, 6) :
#             async for z in AsyncCounter(6) :
#                 if i == 0 :
#                     if z <= j :
#                         pixels[z] = (250-bright[z], bright[z], bright[z])
#                     else :
#                         pixels[z] = (250, 250, 250)
#                 elif i == 1 :
#                     if z <= j :
#                         pixels[z] = (bright[z], 250-bright[z], bright[z])
#                     else :
#                         pixels[z] = (250, 250, 250)
#                 elif i == 2 :
#                     if z <= j :
#                         pixels[z] = (bright[z], bright[z], 250-bright[z])
#                     else :
#                         pixels[z] = (250, 250, 250)
                        
#             pixels.show()
#             time.sleep(0.1)
#             tmp = bright[0]
#             for z in range(0, 5) :
#                 bright[z] = bright[z+1]
#             bright[5] = tmp


def blink(color) :
    pixels.fill((0, 0, 0))

    for i in range(0, 3) :
        if(color == 'r') :
            pixels.fill((255, 0, 0))
            pixels.show()
            print("turn RED!!")
            time.sleep(0.5)
            pixels.fill((0, 0, 0))
            pixels.show()
            print("turn down")
            time.sleep(0.45)
        if(color == 'g') :
            pixels.fill((0, 255, 0))
            time.sleep(0.5)
            pixels.fill((0, 0, 0))
            time.sleep(0.45)
        if(color == 'b') :
            pixels.fill((0, 0, 255))
            time.sleep(0.5)
            pixels.fill((0, 0, 0))
            time.sleep(0.45)

def keepBlink(color) :
    pixels.fill((0, 0, 0))

    # while True :
    while True :
        if(color == 'r') :
            pixels.fill((255, 0, 0))
            pixels.show()
            time.sleep(0.5)
            pixels.fill((0, 0, 0))
            pixels.show()
            time.sleep(0.45)
            # yield
        if(color == 'g') :
            pixels.fill((0, 255, 0))
            time.sleep(0.5)
            pixels.fill((0, 0, 0))
            time.sleep(0.45)
        if(color == 'b') :
            pixels.fill((0, 0, 255))
            time.sleep(0.5)
            pixels.fill((0, 0, 0))
            time.sleep(0.45)

def selBlink(index, t, color) :
    pixels[index] = (0, 0, 0)

    if color == 'r' : 
        for i in range(0, t) :
            pixels[index] = (255, 0, 0)
            pixels.show()
            time.sleep(0.5)
            pixels[index] = (0, 0, 0)
            pixels.show()
            time.sleep(0.45)


def openDoor(index) :
    
    pixels[index] = (0, 0, 0)
    for i in range(6) :
        pixels[index] = (0, 0, 255)
        pixels.show()
        time.sleep(0.3)
        pixels[index] = (0, 0, 0)
        pixels.show()
        time.sleep(0.2)

def closeDoor(index) :
    
    pixels[index] = (0, 0, 0)
    for i in range(3) :
        pixels[index] = (255, 0, 0)
        pixels.show()
        time.sleep(0.5)
        pixels[index] = (0, 0, 0)
        pixels.show()
        time.sleep(0.45)


            
showQuantity()
#showLoading()
# blink("r")
