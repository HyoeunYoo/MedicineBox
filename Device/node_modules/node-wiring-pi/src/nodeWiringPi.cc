#include "nodeWiringPi.h"
// Node Module declaration and initialisation

namespace nodewpi {

    NAN_MODULE_INIT(init_nodeWiringPi)
    {
        nodewpi::init_wiringPi(target);
        
        nodewpi::init_softPwm(target);
        nodewpi::init_softTone(target);

        nodewpi::init_wiringPiI2C(target);
        nodewpi::init_wiringPiSPI(target);
        nodewpi::init_wiringPiISR(target);
        nodewpi::init_wiringSerial(target);
        nodewpi::init_wiringShift(target);
    }
}

NODE_MODULE(nodeWiringPi, nodewpi::init_nodeWiringPi)
