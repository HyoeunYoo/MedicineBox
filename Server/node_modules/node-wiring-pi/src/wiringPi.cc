#include "wiringPi.h"
#include <wiringPi.h>

namespace nodewpi {

NAN_METHOD(setup) {

  SET_ARGUMENT_NAME(0, mode);

  std::string mode(*Nan::Utf8String(info[0]));

  int res = 0;
  if (mode.compare("wpi") == 0) {
    res = ::wiringPiSetup();
  }
  else if (mode.compare("gpio") == 0) {
    res = ::wiringPiSetupGpio();
  }
  else if (mode.compare("sys") == 0) {
    res = ::wiringPiSetupSys();
  }
  else if (mode.compare("phys") == 0) {
    res = ::wiringPiSetupPhys();
  } else {
    THROW_INVALID_ARGUMENT_EXCEPTION(0, mode.c_str())
  }

  // libWiringPi v2 setup functions always returns 0, so this check is kind of useless, unless v1 behaviour is restored
  // NOTE: If you want to restore the v1 behaviour, then you need to set the
  // environment variable: WIRINGPI_CODES (to any value, it just needs to exist)
  info.GetReturnValue().Set(res);
}

// Func : int wiringPiSetup(void)
// Returns : error code if v1 mode otherwise always returns 0
// Description : Initialises wiringPi and assumes that the calling program is going
// to be using the wiringPi pin numbering scheme.
// This is a simplified numbering scheme which provides a mapping from virtual
// pin numbers 0 through 16 to the real underlying Broadcom GPIO pin numbers.
// see the pins page (https://projects.drogon.net/raspberry-pi/wiringpi/pins/) for a table
// which maps the wiringPi pin number to the Broadcom GPIO pin number to the physical location
// on the edge connector.
// This function needs to be called with root privileges.

NAN_METHOD(wiringPiSetup) {

  CHECK_ARGUMENTS_LENGTH_EQUAL(0);

  int res = ::wiringPiSetup();

  info.GetReturnValue().Set(res);
}

// Func : int wiringPiSetupGpio(void)
// Returns : error code if v1 mode otherwise always returns 0
// Description : This is indential to above, however it allows the calling programs to use
// the Broadcom GPIO pin numbers directly with no re-mapping.
// As above, this function needs to be called with root privileges, and note that some pins
// are different from revision 1 to revision 2 boards.

NAN_METHOD(wiringPiSetupGpio) {

  CHECK_ARGUMENTS_LENGTH_EQUAL(0);

  int res = ::wiringPiSetupGpio();

  info.GetReturnValue().Set(res);
}

// Func : int wiringPiSetupSys(void)
// Returns : error code if v1 mode otherwise always returns 0
// Description : This initialises wiringPi but uses the /sys/class/gpio interface rather than
// accessing the hardware directly. This can be called as a non-root user provided the GPIO pins
// have been exported before-hand using gpio program. Pin numbering in this mode is the native
// Broadcom GPIO numbers - the same as wiringPiSetGpio above, so be aware of the differences
// between Rev 1 and Rev 2 boards.
// Note: In this mode you can only use the pins which have been exported via the
// /sys/class/gpio interface before you run your program. You can do this in a seperate
// shell script, or by using the system() function from inside your program to call the gpio program.
// Also note that some functions have no effect when using this mode as they're not currently
// possible to action unless called with root privileges. (although you can use system() to call
// gpio to set/change modes if needed).

NAN_METHOD(wiringPiSetupSys) {


  CHECK_ARGUMENTS_LENGTH_EQUAL(0);

  int res = ::wiringPiSetupSys();

  info.GetReturnValue().Set(res);
}

// Func : int wiringPiSetupPhys(void)
// Returns : error code if v1 mode otherwise always returns 0
// Description : Identical to above, however it allows the calling programs to use
// the physical pin numbers on the P1 connector only.
// As above, this function needs to be called with root priviliges.

NAN_METHOD(wiringPiSetupPhys) {


  CHECK_ARGUMENTS_LENGTH_EQUAL(0);

  int res = ::wiringPiSetupPhys();

  info.GetReturnValue().Set(res);
}

// Func : void pinModeAlt(int pin, int mode)
// Description : This is an un-documented special to let you set any pin to any mode.
// Modes are FSEL_INPT, FSEL_OUTP, FSEL_ALT0, FSEL_ALT1, FSEL_ALT2, FSEL_ALT3, FSEL_ALT4, FSEL_ALT5.

NAN_METHOD(pinModeAlt) {


  SET_ARGUMENT_NAME(0, pin);
  SET_ARGUMENT_NAME(1, mode);

  CHECK_ARGUMENTS_LENGTH_EQUAL(2);

  CHECK_ARGUMENT_TYPE_INT32(0);
  CHECK_ARGUMENT_TYPE_INT32(1);

  int pin = GET_ARGUMENT_AS_INT32(0);
  int mode = GET_ARGUMENT_AS_INT32(1);

  const std::vector<int> validInputs = { FSEL_INPT, FSEL_OUTP, FSEL_ALT0, FSEL_ALT1, FSEL_ALT2, FSEL_ALT3, FSEL_ALT4, FSEL_ALT5 };

  if(find_int(mode, validInputs))
  {
    ::pinModeAlt(pin, mode);
  }
  else {
    THROW_INVALID_ARGUMENT_EXCEPTION(1, mode)
  }
}

// Func : void pinMode(int pin, int mode)
// Description : This sets the mode of a pin to either INPUT, OUTPUT, PWM_OUTPUT or GPIO_CLOCK.
// Note that only wiringPi pin 1 (BCM_GPIO 18) supports PWM output and only wiringPi pin 7 (BCM_GPIO 4)
// supports CLOCK output modes.
// This function has no effect when in Sys mode. If you need to change the pin mode, the you can
// do it with the gpio program in a script before you start your program.

NAN_METHOD(pinMode) {


  SET_ARGUMENT_NAME(0, pin);
  SET_ARGUMENT_NAME(1, mode);

  CHECK_ARGUMENTS_LENGTH_EQUAL(2);

  CHECK_ARGUMENT_TYPE_INT32(0);
  CHECK_ARGUMENT_TYPE_INT32(1);

  int pin = GET_ARGUMENT_AS_INT32(0);
  int mode = GET_ARGUMENT_AS_INT32(1);

  const std::vector<int> validInputs = { INPUT, OUTPUT, PWM_OUTPUT, GPIO_CLOCK, SOFT_PWM_OUTPUT, SOFT_TONE_OUTPUT };

  if(find_int(mode, validInputs))
  {
    ::pinMode(pin, mode);
  }
  else {
    THROW_INVALID_ARGUMENT_EXCEPTION(1, mode)
  }

}

// Func : void pullUpDnControl(int pin, int pud)
// Description : This sets the pull-up or pull-down resistor mode on the given pin, which should be set
// as an input. Unlike Arduino, the BCM2835 has both pull-up and down internal resistors.
// The parameter pud should be; PUD_OFF (no pull up/down), PUD_DOWN (pull to ground) or PUD_UP (pull to 3.3v).
// The internal pull up/down resistors have a value of approximately 50KΩ on the Raspberry Pi.

NAN_METHOD(pullUpDnControl) {


  SET_ARGUMENT_NAME(0, pin);
  SET_ARGUMENT_NAME(1, pud);

  CHECK_ARGUMENTS_LENGTH_EQUAL(2);

  CHECK_ARGUMENT_TYPE_INT32(0);
  CHECK_ARGUMENT_TYPE_INT32(1);

  int pin = GET_ARGUMENT_AS_INT32(0);
  int pud = GET_ARGUMENT_AS_INT32(1);

  const std::vector<int> validInputs = { PUD_OFF, PUD_DOWN, PUD_UP };

  if(find_int(pud, validInputs))
  {
    ::pullUpDnControl(pin, pud);
  } else {
    THROW_INVALID_ARGUMENT_EXCEPTION(1, pud)
  }

}

// Func : int digitalRead(int pin)
// Description : This function returns the value read at the given pin. It will be HIGH or LOW (1 or 0)
// depending on the logic level at the pin.

NAN_METHOD(digitalRead) {


  SET_ARGUMENT_NAME(0, pin);

  CHECK_ARGUMENTS_LENGTH_EQUAL(1);

  CHECK_ARGUMENT_TYPE_INT32(0);

  int pin = GET_ARGUMENT_AS_INT32(0);
  int res = ::digitalRead(pin);

  // Make sure the function returns strictly 1 or 0
  // §4.7/4 from the C++ Standard says (Integral Conversion)
  // If the source type is bool, the value false is converted to zero and the value true is converted to one.
  res = (res != 0);

  info.GetReturnValue().Set(res);
}

// Func : void digitalWrite(int pin, int value)
// Description : Write the value HIGH or LOW (1 or 0) to the given pin which must have been
// previously set as an output.
// WiringPi treats any non-zero number as HIGH, however 0 is the only representation of LOW.

NAN_METHOD(digitalWrite) {


  SET_ARGUMENT_NAME(0, pin);
  SET_ARGUMENT_NAME(1, state);

  CHECK_ARGUMENTS_LENGTH_EQUAL(2);

  CHECK_ARGUMENT_TYPE_INT32(0);
  CHECK_ARGUMENT_TYPE_INT32(1);

  int pin = GET_ARGUMENT_AS_INT32(0);
  int state = GET_ARGUMENT_AS_INT32(1);

  const std::vector<int> validStates = { HIGH, LOW };

  if(find_int(state, validStates)) {
    ::digitalWrite(pin, state);
  } else {
    THROW_INVALID_ARGUMENT_EXCEPTION(1, state)
  }
  
  

}

// Func : void pwmWrite(int pin, int value)
// Description : Writes the value to the PWM register for the given pin. The Raspberry Pi has
// one on-board PWM pin, pin 1 (BCM_GPIO 18, Phys 12) and the range is 0-1024. Other PWM
// devices may have other PWM ranges.
// This function is not able to control the Pi's on-board PWM when in Sys mode.

NAN_METHOD(pwmWrite) {


  SET_ARGUMENT_NAME(0, pin);
  SET_ARGUMENT_NAME(1, value);

  CHECK_ARGUMENTS_LENGTH_EQUAL(2);

  CHECK_ARGUMENT_TYPE_INT32(0);
  CHECK_ARGUMENT_TYPE_INT32(1);

  int pin = GET_ARGUMENT_AS_INT32(0);
  int value = GET_ARGUMENT_AS_INT32(1);

  ::pwmWrite(pin, value);


}

NAN_METHOD(analogRead) {


  SET_ARGUMENT_NAME(0, pin);

  CHECK_ARGUMENTS_LENGTH_EQUAL(1);

  CHECK_ARGUMENT_TYPE_INT32(0);

  int pin = GET_ARGUMENT_AS_INT32(0);
  int res = ::analogRead(pin);

  info.GetReturnValue().Set(res);
}

// Func : void analogWrite(int pin, int value)
// Description : This writes the given value to the supplied analog pin. You will need to register
// additional analog modules to enable this function for devices such as the Gertboard.

NAN_METHOD(analogWrite) {


  SET_ARGUMENT_NAME(0, pin);
  SET_ARGUMENT_NAME(1, value);

  CHECK_ARGUMENTS_LENGTH_EQUAL(2);

  CHECK_ARGUMENT_TYPE_INT32(0);
  CHECK_ARGUMENT_TYPE_INT32(1);

  int pin = GET_ARGUMENT_AS_INT32(0);
  int value = GET_ARGUMENT_AS_INT32(1);

  ::analogWrite(pin, value);


}

NAN_METHOD(delay) {


  SET_ARGUMENT_NAME(0, ms);

  CHECK_ARGUMENTS_LENGTH_EQUAL(1);

  CHECK_ARGUMENT_TYPE_INT32(0);

  int ms = GET_ARGUMENT_AS_INT32(0);

  ::delay(ms);


}

NAN_METHOD(delayMicroseconds) {


  SET_ARGUMENT_NAME(0, us);

  CHECK_ARGUMENTS_LENGTH_EQUAL(1);

  CHECK_ARGUMENT_TYPE_INT32(0);

  int us = GET_ARGUMENT_AS_INT32(0);

  ::delayMicroseconds(us);


}

NAN_METHOD(millis) {

  CHECK_ARGUMENTS_LENGTH_EQUAL(0);

  unsigned int ms = ::millis();

  info.GetReturnValue().Set(ms);
}

NAN_METHOD(micros) {

  CHECK_ARGUMENTS_LENGTH_EQUAL(0);

  unsigned int us = ::micros();

  info.GetReturnValue().Set(us);
}

// === Raspberry Pi specific ===

// Func : int piBoardRev(void)
// Description : This returns the board revision of the Raspberry Pi. It will be either 1 or 2.
// Some of the BCM_GPIO pins changed number and function when moving from board revision 1 to 2,
// so if you are using BCM_GPIO pin numbers, then you need to be aware of the differences.

NAN_METHOD(piBoardRev) {

  CHECK_ARGUMENTS_LENGTH_EQUAL(0);

  int res = ::piBoardRev();

  info.GetReturnValue().Set(res);
}

NAN_METHOD(piBoardId) {

  CHECK_ARGUMENTS_LENGTH_EQUAL(0);

  // libWiringPi 2.20 changes:
  // maker is now a int indexing makerNames string tables
  // a fifth arguments was added named overvolted
  int model, rev, mem, marker, overvolted;

  ::piBoardId(&model, &rev, &mem, &marker, &overvolted);

    Local<Object> obj = Nan::New<Object>();
    obj->Set(STRING("model"), INT32(model));
    obj->Set(STRING("rev"), INT32(rev));
    obj->Set(STRING("mem"), INT32(mem));
    obj->Set(STRING("marker"), INT32(marker));
    obj->Set(STRING("overvolted"), INT32(overvolted));
  
  info.GetReturnValue().Set(obj);
}

// Func : int wpiPinToGpio(int wpiPin)
// Description : This returns the BCM_GPIO pin number of the supplied wiringPi pin.
// It takes the board revision into account.

NAN_METHOD(wpiPinToGpio) {


  SET_ARGUMENT_NAME(0, pin);

  CHECK_ARGUMENTS_LENGTH_EQUAL(1);

  CHECK_ARGUMENT_TYPE_INT32(0);

  int pin = GET_ARGUMENT_AS_INT32(0);
  int res = ::wpiPinToGpio(pin);

  info.GetReturnValue().Set(res);
}

// Func : int physPinToGpio (int physPin)
// Description : This returns the BCM_GPIO pin number of the suppled physical pin on the P1 connector.

NAN_METHOD(physPinToGpio) {


  SET_ARGUMENT_NAME(0, pin);

  CHECK_ARGUMENTS_LENGTH_EQUAL(1);

  CHECK_ARGUMENT_TYPE_INT32(0);

  int pin = GET_ARGUMENT_AS_INT32(0);
  int res = ::physPinToGpio(pin);

  info.GetReturnValue().Set(res);
}

// Func : void setPadDrive(int group, int value)
// Description : This sets the "strength" of the pad drivers for a particular group of pins.
// There are 3 groups of pins and the drive strength is from 0 to 7. Do not use the unless you
// know what you are doing.

NAN_METHOD(setPadDrive) {


  SET_ARGUMENT_NAME(0, group);
  SET_ARGUMENT_NAME(1, value);

  CHECK_ARGUMENTS_LENGTH_EQUAL(2);

  CHECK_ARGUMENT_TYPE_INT32(0);
  CHECK_ARGUMENT_TYPE_INT32(1);

  int group = GET_ARGUMENT_AS_INT32(0);
  int value = GET_ARGUMENT_AS_INT32(1);

  ::setPadDrive(group, value);


}

// Func : int getAlt(int pin)
// Description : Returns the ALT bits for a given port. Only really of-use
// for the gpio readall command (I think).

NAN_METHOD(getAlt) {


  SET_ARGUMENT_NAME(0, pin);

  CHECK_ARGUMENTS_LENGTH_EQUAL(1);

  CHECK_ARGUMENT_TYPE_INT32(0);

  int pin = GET_ARGUMENT_AS_INT32(0);
  int res = ::getAlt(pin);

  info.GetReturnValue().Set(res);
}

NAN_METHOD(pwmToneWrite) {


  SET_ARGUMENT_NAME(0, pin);
  SET_ARGUMENT_NAME(1, frequency);

  CHECK_ARGUMENTS_LENGTH_EQUAL(2);

  CHECK_ARGUMENT_TYPE_INT32(0);
  CHECK_ARGUMENT_TYPE_INT32(1);

  int pin = GET_ARGUMENT_AS_INT32(0);
  int frequency = GET_ARGUMENT_AS_INT32(1);

  ::pwmToneWrite(pin, frequency);


}

// Func : void digitalWriteByte(int value)
// Description : This writes the 8-bit byte supplied to the first 8 GPIO pins.
// It’s the fastest way to set all 8 bits at once to a particular value, although it still takes
// two write operations to the Pi’s GPIO hardware.

NAN_METHOD(digitalWriteByte) {


  SET_ARGUMENT_NAME(0, byte);

  CHECK_ARGUMENTS_LENGTH_EQUAL(1);

  CHECK_ARGUMENT_TYPE_INT32(0);

  int byte = GET_ARGUMENT_AS_INT32(0);
  ::digitalWriteByte(byte);


}

// Func : void pwmSetMode(int mode)
// Description : The PWM generator can run in 2 modes – “balanced” and “mark:space”.
// The mark:space mode is traditional, however the default mode in the Pi is “balanced”.
// You can switch modes by supplying the parameter: PWM_MODE_BAL or PWM_MODE_MS.

NAN_METHOD(pwmSetMode) {


  SET_ARGUMENT_NAME(0, mode);

  CHECK_ARGUMENTS_LENGTH_EQUAL(1);

  CHECK_ARGUMENT_TYPE_INT32(0);

  int mode = GET_ARGUMENT_AS_INT32(0);

  const std::vector<int> validInts = { PWM_MODE_BAL, PWM_MODE_MS };

  if(find_int(mode, validInts)) {
    ::pwmSetMode(mode);
  } else {
    THROW_INVALID_ARGUMENT_EXCEPTION(0, mode)
  }

}

// Func : void pwmSetRange(unsigned int range)
// Description : This sets the range register in the PWM generator. The default is 1024.
// Note: The PWM control functions can not be used when in Sys mode. To understand more about
// the PWM system, you’ll need to read the Broadcom ARM peripherals manual.

NAN_METHOD(pwmSetRange) {


  SET_ARGUMENT_NAME(0, range);

  CHECK_ARGUMENTS_LENGTH_EQUAL(1);

  CHECK_ARGUMENT_TYPE_UINT32(0);

  unsigned int range = GET_ARGUMENT_AS_UINT32(0);
  ::pwmSetRange(range);


}

// Func : void pwmSetClock(int divisor)
// Description : This sets the divisor for the PWM clock.
// Note: The PWM control functions can not be used when in Sys mode. To understand more about
// the PWM system, you’ll need to read the Broadcom ARM peripherals manual.

NAN_METHOD(pwmSetClock) {


  SET_ARGUMENT_NAME(0, divisor);

  CHECK_ARGUMENTS_LENGTH_EQUAL(1);

  CHECK_ARGUMENT_TYPE_INT32(0);

  int divisor = GET_ARGUMENT_AS_INT32(0);
  ::pwmSetClock(divisor);


}

// Func : void gpioClockSet(int pin, int freq)
// Description : Set the frequency on a GPIO clock pin

NAN_METHOD(gpioClockSet) {


  SET_ARGUMENT_NAME(0, pin);
  SET_ARGUMENT_NAME(1, frequency);

  CHECK_ARGUMENTS_LENGTH_EQUAL(2);

  CHECK_ARGUMENT_TYPE_INT32(0);
  CHECK_ARGUMENT_TYPE_INT32(1);

  int pin = GET_ARGUMENT_AS_INT32(0);
  int frequency = GET_ARGUMENT_AS_INT32(1);

  ::gpioClockSet(pin, frequency);


}

NAN_MODULE_INIT(init_wiringPi) {
  // Setup
  NAN_EXPORT(target, setup);
  NAN_EXPORT(target, wiringPiSetup);
  NAN_EXPORT(target, wiringPiSetupGpio);
  NAN_EXPORT(target, wiringPiSetupSys);
  NAN_EXPORT(target, wiringPiSetupPhys);

  // Core functions
  NAN_EXPORT(target, pinModeAlt);
  NAN_EXPORT(target, pinMode);
  NAN_EXPORT(target, pullUpDnControl);
  NAN_EXPORT(target, digitalRead);
  NAN_EXPORT(target, digitalWrite);
  NAN_EXPORT(target, pwmWrite);
  NAN_EXPORT(target, analogRead);
  NAN_EXPORT(target, analogWrite);

  NAN_EXPORT(target, delay);
  NAN_EXPORT(target, delayMicroseconds);
  NAN_EXPORT(target, millis);
  NAN_EXPORT(target, micros);

  // On-Board Rasberry Pi hardware specific stuff
  NAN_EXPORT(target, piBoardRev);
  NAN_EXPORT(target, piBoardId);
  NAN_EXPORT(target, wpiPinToGpio);
  NAN_EXPORT(target, physPinToGpio);
  NAN_EXPORT(target, setPadDrive);
  NAN_EXPORT(target, getAlt);
  NAN_EXPORT(target, pwmToneWrite);
  NAN_EXPORT(target, digitalWriteByte);
  NAN_EXPORT(target, pwmSetMode);
  NAN_EXPORT(target, pwmSetRange);
  NAN_EXPORT(target, pwmSetClock);
  NAN_EXPORT(target, gpioClockSet);

  // WPI_MODEs
  NODE_DEFINE_CONSTANT(target, WPI_MODE_PINS);
  NODE_DEFINE_CONSTANT(target, WPI_MODE_PHYS);
  NODE_DEFINE_CONSTANT(target, WPI_MODE_GPIO);
  NODE_DEFINE_CONSTANT(target, WPI_MODE_GPIO_SYS);
  NODE_DEFINE_CONSTANT(target, WPI_MODE_PIFACE);
  NODE_DEFINE_CONSTANT(target, WPI_MODE_UNINITIALISED);


  // pinMode
  NODE_DEFINE_CONSTANT(target, INPUT);
  NODE_DEFINE_CONSTANT(target, OUTPUT);
  NODE_DEFINE_CONSTANT(target, PWM_OUTPUT);
  NODE_DEFINE_CONSTANT(target, GPIO_CLOCK);
  NODE_DEFINE_CONSTANT(target, SOFT_PWM_OUTPUT);
  NODE_DEFINE_CONSTANT(target, SOFT_TONE_OUTPUT);

  // pullUpDnControl
  NODE_DEFINE_CONSTANT(target, PUD_OFF);
  NODE_DEFINE_CONSTANT(target, PUD_DOWN);
  NODE_DEFINE_CONSTANT(target, PUD_UP);

  // digitalRead/Write
  NODE_DEFINE_CONSTANT(target, HIGH);
  NODE_DEFINE_CONSTANT(target, LOW);

  // pwmSetMode
  NODE_DEFINE_CONSTANT(target, PWM_MODE_BAL);
  NODE_DEFINE_CONSTANT(target, PWM_MODE_MS);

  // piBoardId
  // Versions from wiringPi 2.50
  NODE_DEFINE_CONSTANT(target, PI_MODEL_A);
  NODE_DEFINE_CONSTANT(target, PI_MODEL_B);
  NODE_DEFINE_CONSTANT(target, PI_MODEL_AP);
  NODE_DEFINE_CONSTANT(target, PI_MODEL_BP);
  NODE_DEFINE_CONSTANT(target, PI_MODEL_2);
  NODE_DEFINE_CONSTANT(target, PI_ALPHA);
  NODE_DEFINE_CONSTANT(target, PI_MODEL_CM);
  NODE_DEFINE_CONSTANT(target, PI_MODEL_07);
  NODE_DEFINE_CONSTANT(target, PI_MODEL_3B);
  NODE_DEFINE_CONSTANT(target, PI_MODEL_ZERO);
  NODE_DEFINE_CONSTANT(target, PI_MODEL_CM3);
  NODE_DEFINE_CONSTANT(target, PI_MODEL_ZERO_W);
  NODE_DEFINE_CONSTANT(target, PI_MODEL_3BP);
  NODE_DEFINE_CONSTANT(target, PI_MODEL_3AP);
  NODE_DEFINE_CONSTANT(target, PI_MODEL_CM3P);

  NODE_DEFINE_CONSTANT(target, PI_VERSION_1);
  NODE_DEFINE_CONSTANT(target, PI_VERSION_1_1);
  NODE_DEFINE_CONSTANT(target, PI_VERSION_1_2);
  NODE_DEFINE_CONSTANT(target, PI_VERSION_2);

  NODE_DEFINE_CONSTANT(target, PI_MAKER_UNKNOWN);
  NODE_DEFINE_CONSTANT(target, PI_MAKER_EGOMAN);
  NODE_DEFINE_CONSTANT(target, PI_MAKER_SONY);

  // MBEST became EMBEST after wiringPi 2.32
#ifdef PI_MAKER_EMBEST
  NODE_DEFINE_CONSTANT(target, PI_MAKER_EMBEST);
#endif

#ifdef PI_MAKER_MBEST
  NODE_DEFINE_CONSTANT(target,PI_MAKER_MBEST);
#endif

  EXPORT_CONSTANT_STRING_ARRAY(PI_MODEL_NAMES, piModelNames, 7);
  EXPORT_CONSTANT_STRING_ARRAY(PI_REVISION_NAMES, piRevisionNames, 5);
  EXPORT_CONSTANT_STRING_ARRAY(PI_MAKER_NAMES, piMakerNames, 5);

  // pinModeAlt
  NODE_DEFINE_CONSTANT(target, FSEL_INPT);
  NODE_DEFINE_CONSTANT(target, FSEL_OUTP);
  NODE_DEFINE_CONSTANT(target, FSEL_ALT0);
  NODE_DEFINE_CONSTANT(target, FSEL_ALT1);
  NODE_DEFINE_CONSTANT(target, FSEL_ALT2);
  NODE_DEFINE_CONSTANT(target, FSEL_ALT3);
  NODE_DEFINE_CONSTANT(target, FSEL_ALT4);
  NODE_DEFINE_CONSTANT(target, FSEL_ALT5);
}

} //namespace nodewpi