#ifndef _WPI_WIRING_SHIFT_H_
#define _WPI_WIRING_SHIFT_H_

  #include "addon.h"
  namespace nodewpi {
    NAN_METHOD(shiftIn);
    NAN_METHOD(shiftOut);
    NAN_MODULE_INIT(init_wiringShift);
  }
#endif