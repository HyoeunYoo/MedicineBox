#include "softTone.h"
#include <softTone.h>

namespace nodewpi {

  // Func : int softToneCreate(int pin);
  // Description : This creates a software controlled tone pin. 
  // You can use any GPIO pin and the pin numbering will be that of the wiringPiSetup() function you used.
  // The return value is 0 for success. 
  // Anything else and you should check the global errno variable to see what went wrong.
  // NOTE : You must initialise wiringPi with one of wiringPiSetup(),  wiringPiSetupGpio() or wiringPiSetupPhys() functions. 
  // wiringPiSetupSys() is not fast enough, so you must run your programs with sudo.
  // NOTE2 : Each pin activated in softTone mode uses approximately 0.5% of the CPU.
  // NOTE3 : You need to keep your program running to maintain the sound output!

  NAN_METHOD(softToneCreate) {
    
    SET_ARGUMENT_NAME(0, pin);
    
    CHECK_ARGUMENTS_LENGTH_EQUAL(1);
    
    CHECK_ARGUMENT_TYPE_INT32(0);
    
    int pin = GET_ARGUMENT_AS_INT32(0);
    
    int res = ::softToneCreate(pin);
    
    info.GetReturnValue().Set(res);
  }

  // Func : void softToneWrite(int pin, int freq);
  // Description : This updates the tone frequency value on the given pin. The tone will be played until you set the frequency to 0.

  NAN_METHOD(softToneWrite) {
    
    SET_ARGUMENT_NAME(0, pin);
    SET_ARGUMENT_NAME(1, frequency);
    
    CHECK_ARGUMENTS_LENGTH_EQUAL(2);
    
    CHECK_ARGUMENT_TYPE_INT32(0);
    CHECK_ARGUMENT_TYPE_INT32(1);
    
    int pin = GET_ARGUMENT_AS_INT32(0);
    int frequency = GET_ARGUMENT_AS_INT32(1);
    
    ::softToneWrite(pin, frequency);
  }

  NAN_METHOD(softToneStop) {
      
      SET_ARGUMENT_NAME(0, pin);
      
      CHECK_ARGUMENTS_LENGTH_EQUAL(1);
      
      CHECK_ARGUMENT_TYPE_INT32(0);
      
      int pin = GET_ARGUMENT_AS_INT32(0);
      
      ::softToneStop(pin);
  }

  NAN_MODULE_INIT(init_softTone) {
    NAN_EXPORT(target, softToneCreate);
    NAN_EXPORT(target, softToneWrite);
    NAN_EXPORT(target, softToneStop);
  }

} //namespace nodewpi

