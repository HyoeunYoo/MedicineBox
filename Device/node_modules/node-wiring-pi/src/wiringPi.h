#ifndef _WPI_WIRING_PI_H_
#define _WPI_WIRING_PI_H_


#include "addon.h"

namespace nodewpi {

  #define	FSEL_INPT		0b000
  #define	FSEL_OUTP		0b001
  #define	FSEL_ALT0		0b100
  #define	FSEL_ALT1		0b101
  #define	FSEL_ALT2		0b110
  #define	FSEL_ALT3		0b111
  #define	FSEL_ALT4		0b011
  #define	FSEL_ALT5		0b010

  // Setup
  NAN_METHOD(setup);
  NAN_METHOD(wiringPiSetup);
  NAN_METHOD(wiringPiSetupGpio);
  NAN_METHOD(wiringPiSetupSys);
  NAN_METHOD(wiringPiSetupPhys);

  // Core functions
  NAN_METHOD(pinModeAlt);
  NAN_METHOD(pinMode);
  NAN_METHOD(pullUpDnControl);
  NAN_METHOD(digitalRead);
  NAN_METHOD(digitalWrite);
  NAN_METHOD(pwmWrite);
  NAN_METHOD(analogRead);
  NAN_METHOD(analogWrite);

  NAN_METHOD(delay);
  NAN_METHOD(delayMicroseconds);
  NAN_METHOD(millis);
  NAN_METHOD(micros);

  // On-Board Rasberry Pi hardware specific stuff
  NAN_METHOD(piBoardRev);
  NAN_METHOD(piBoardId);
  NAN_METHOD(wpiPinToGpio);
  NAN_METHOD(physPinToGpio);
  NAN_METHOD(setPadDrive);
  NAN_METHOD(getAlt);
  NAN_METHOD(pwmToneWrite);
  NAN_METHOD(digitalWriteByte);
  NAN_METHOD(pwmSetMode);
  NAN_METHOD(pwmSetRange);
  NAN_METHOD(pwmSetClock);
  NAN_METHOD(gpioClockSet);

  NAN_MODULE_INIT(init_wiringPi);

}

#endif