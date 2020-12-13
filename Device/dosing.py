import RPi.GPIO as GPIO
import time

module = [6, 5, 12, 7, 8, 25]

# select = 0
def dosing1(slot) :

    slot = slot - 1

    if slot >= 0 and slot < 6 :
        GPIO.setmode(GPIO.BCM)
        GPIO.setwarnings(False)
        GPIO.setup(module[slot], GPIO.OUT)
        # GPIO.cleanup()
        servo = GPIO.PWM(module[slot], 50)
        servo.start(12)
        try :
            # servo.ChangeDutyCycle(9.0)
            servo.ChangeDutyCycle(10.8)
            print("open")
            time.sleep(0.2)
            servo.ChangeDutyCycle(12.5)
            print("close")
            time.sleep(1)

            servo.stop()
            # GPIO.cleanup()
            return "true"
        except :
            servo.stop()
            GPIO.cleanup()
            return "false"
            
    else :
        return "algument_error"

