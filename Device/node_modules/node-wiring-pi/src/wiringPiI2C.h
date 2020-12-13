#ifndef _WPI_WIRING_PI_I2C_H_
#define _WPI_WIRING_PI_I2C_H_

#include "addon.h"
namespace nodewpi {  
  NAN_METHOD(wiringPiI2CRead);
  NAN_METHOD(wiringPiI2CReadReg8);
  NAN_METHOD(wiringPiI2CReadReg16);
  NAN_METHOD(wiringPiI2CWrite);
  NAN_METHOD(wiringPiI2CWriteReg8);
  NAN_METHOD(wiringPiI2CWriteReg16);
  NAN_METHOD(wiringPiI2CSetupInterface);
  NAN_METHOD(wiringPiI2CSetup);
  NAN_METHOD(wiringPiI2CClose);

  NAN_MODULE_INIT(init_wiringPiI2C);
}
#endif