#ifndef _WPI_WIRING_SERIAL_H_
#define _WPI_WIRING_SERIAL_H_

  #include "addon.h"
  namespace nodewpi {
    NAN_METHOD(serialOpen);
    NAN_METHOD(serialClose);
    NAN_METHOD(serialFlush);
    NAN_METHOD(serialPutchar);
    NAN_METHOD(serialPuts);
    NAN_METHOD(serialPrintf);
    NAN_METHOD(serialDataAvail);
    NAN_METHOD(serialGetchar);
    
    NAN_MODULE_INIT(init_wiringSerial);
  }
#endif