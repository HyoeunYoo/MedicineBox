#include <nan.h>

#include "wiringPi.h"
#include "softPwm.h"
#include "softTone.h"
#include "wiringPiI2C.h"
#include "wiringPiSPI.h"
#include "wiringPiISR.h"
#include "wiringSerial.h"
#include "wiringShift.h"
  
//#include "extensions/extensions.h"
//#include "devlib/devlib.h"

namespace nodewpi {
    NAN_MODULE_INIT(init_nodeWiringPi);
}