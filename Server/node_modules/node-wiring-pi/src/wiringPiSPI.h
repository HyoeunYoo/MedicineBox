#ifndef _WPI_WIRING_PI_SPI_H_
#define _WPI_WIRING_PI_SPI_H_

  #include "addon.h"
namespace nodewpi {
  NAN_METHOD(wiringPiSPIGetFd);
  NAN_METHOD(wiringPiSPIDataRW);
  NAN_METHOD(wiringPiSPISetup);
  NAN_METHOD(wiringPiSPISetupMode);
  NAN_METHOD(wiringPiSPIClose);
  
  NAN_MODULE_INIT(init_wiringPiSPI);
}
#endif