#ifndef _WPI_SOFT_PWM_H_
#define _WPI_SOFT_PWM_H_

#include "addon.h"

namespace nodewpi {

  NAN_METHOD(softPwmCreate);
  NAN_METHOD(softPwmWrite);
  NAN_METHOD(softPwmStop);

  NAN_MODULE_INIT(init_softPwm);
}

#endif