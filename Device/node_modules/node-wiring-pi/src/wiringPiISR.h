#ifndef _WPI_WIRING_PI_ISR_H_
#define _WPI_WIRING_PI_ISR_H_

#include "addon.h"

namespace nodewpi {  
  NAN_METHOD(wiringPiISR);
// see comments in .cc   NAN_METHOD(wiringPiISRCancel);

  NAN_MODULE_INIT(init_wiringPiISR);
}
#endif