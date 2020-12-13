#include "wiringPiI2C.h"
#include <wiringPiI2C.h>
#include <unistd.h>

namespace nodewpi {

  // Func : int wiringPiI2CRead (int fd);
  // Simple device read. Some devices present data when you read them without having to do any register transactions.

  NAN_METHOD(wiringPiI2CRead) {
    
    SET_ARGUMENT_NAME(0, fd);
    
    CHECK_ARGUMENTS_LENGTH_EQUAL(1);
    
    CHECK_ARGUMENT_TYPE_INT32(0);
    
    int fd = GET_ARGUMENT_AS_INT32(0);
    
    int res = ::wiringPiI2CRead(fd);
    
    info.GetReturnValue().Set(res);
  }

  // Func : int wiringPiI2CRead (int fd, int reg);
  // read an 8-bits value from the device register indicated.

  NAN_METHOD(wiringPiI2CReadReg8) {
    
    SET_ARGUMENT_NAME(0, fd);
    SET_ARGUMENT_NAME(1, reg);
    
    CHECK_ARGUMENTS_LENGTH_EQUAL(2);
    
    CHECK_ARGUMENT_TYPE_INT32(0);
    CHECK_ARGUMENT_TYPE_INT32(1);
    
    int fd = GET_ARGUMENT_AS_INT32(0);
    int reg = GET_ARGUMENT_AS_INT32(1);
    
    int res = ::wiringPiI2CReadReg8(fd, reg);
    
    info.GetReturnValue().Set(res);
  }

  // Func : int wiringPiI2CRead (int fd, int reg)
  // read a 16-bits value from the device register indicated.

  NAN_METHOD(wiringPiI2CReadReg16) {

    SET_ARGUMENT_NAME(0, fd);
    SET_ARGUMENT_NAME(1, reg);

    CHECK_ARGUMENTS_LENGTH_EQUAL(2);

    CHECK_ARGUMENT_TYPE_INT32(0);
    CHECK_ARGUMENT_TYPE_INT32(1);

    int fd = GET_ARGUMENT_AS_INT32(0);
    int reg = GET_ARGUMENT_AS_INT32(1);

    int res = ::wiringPiI2CReadReg16(fd, reg);

    info.GetReturnValue().Set(res);
  }

  // Func : int wiringPiI2CWrite (int fd, int data)
  // Simple device write. Some devices accept data this way without needing to access any internal registers.

  NAN_METHOD(wiringPiI2CWrite) {

    SET_ARGUMENT_NAME(0, fd);
    SET_ARGUMENT_NAME(1, data);
    
    CHECK_ARGUMENTS_LENGTH_EQUAL(2);
    
    CHECK_ARGUMENT_TYPE_INT32(0);
    CHECK_ARGUMENT_TYPE_INT32(1);
    
    int fd = GET_ARGUMENT_AS_INT32(0);
    int data = GET_ARGUMENT_AS_INT32(1);
    data = data & 0xFF;
    
    int res = ::wiringPiI2CWrite(fd, data);
    
    info.GetReturnValue().Set(res);
  }

  // Func : int wiringPiI2CWriteReg8 (int fd, int reg, int data)
  // write an 8-bit data value into the device register indicated.

  NAN_METHOD(wiringPiI2CWriteReg8) {

    SET_ARGUMENT_NAME(0, fd);
    SET_ARGUMENT_NAME(1, reg);
    SET_ARGUMENT_NAME(2, data);

    CHECK_ARGUMENTS_LENGTH_EQUAL(3);

    CHECK_ARGUMENT_TYPE_INT32(0);
    CHECK_ARGUMENT_TYPE_INT32(1);
    CHECK_ARGUMENT_TYPE_INT32(2);

    int fd = GET_ARGUMENT_AS_INT32(0);
    int reg = GET_ARGUMENT_AS_INT32(1);
    int data = GET_ARGUMENT_AS_INT32(2);
    data = data & 0xFF;

    int res = ::wiringPiI2CWriteReg8(fd, reg, data);

    info.GetReturnValue().Set(res);
  }

  NAN_METHOD(wiringPiI2CWriteReg16) {


    SET_ARGUMENT_NAME(0, fd);
    SET_ARGUMENT_NAME(1, reg);
    SET_ARGUMENT_NAME(2, data);

    CHECK_ARGUMENTS_LENGTH_EQUAL(3);

    CHECK_ARGUMENT_TYPE_INT32(0);
    CHECK_ARGUMENT_TYPE_INT32(1);
    CHECK_ARGUMENT_TYPE_INT32(2);

    int fd = GET_ARGUMENT_AS_INT32(0);
    int reg = GET_ARGUMENT_AS_INT32(1);
    int data = GET_ARGUMENT_AS_INT32(2);
    data = data & 0xFFFF;

    int res = ::wiringPiI2CWriteReg16(fd, reg, data);

    info.GetReturnValue().Set(res);
  }

  // Func : int wiringPiI2CSetupInterface (const char *device, int devId)

  NAN_METHOD(wiringPiI2CSetupInterface) {

    
    SET_ARGUMENT_NAME(0, device);
    SET_ARGUMENT_NAME(1, devId);
    
    CHECK_ARGUMENTS_LENGTH_EQUAL(2);
    
    CHECK_ARGUMENT_TYPE_STRING(0);
    CHECK_ARGUMENT_TYPE_INT32(1);
    
    Nan::Utf8String device(GET_ARGUMENT_AS_STRING(0));
    
    int devId = GET_ARGUMENT_AS_INT32(1);
    
    int res = ::wiringPiI2CSetupInterface(*device, devId);
    
    info.GetReturnValue().Set(res);
  }

  // Func : int wirintPiI2CSetup (int devId)

  NAN_METHOD(wiringPiI2CSetup) {

    
    SET_ARGUMENT_NAME(0, devId);
    
    CHECK_ARGUMENTS_LENGTH_EQUAL(1);
    
    CHECK_ARGUMENT_TYPE_INT32(0);
    
    int devId = GET_ARGUMENT_AS_INT32(0);
    
    int res = ::wiringPiI2CSetup(devId);
    
    info.GetReturnValue().Set(res);
  }

  // Func : void wiringPiI2CClose(const int fd)
  // Description : This closes opened I2C file descriptor
  // fd is file descriptor returned either from wiringPiI2CSetup or wiringPiI2CSetupInterface

  NAN_METHOD(wiringPiI2CClose) {

    SET_ARGUMENT_NAME(0, fd);

    CHECK_ARGUMENTS_LENGTH_EQUAL(1);

    CHECK_ARGUMENT_TYPE_INT32(0);

    int fd = GET_ARGUMENT_AS_INT32(0);

    ::close(fd);


  }

  NAN_MODULE_INIT(init_wiringPiI2C) {
    NAN_EXPORT(target, wiringPiI2CRead);
    NAN_EXPORT(target, wiringPiI2CReadReg8);
    NAN_EXPORT(target, wiringPiI2CReadReg16);
    NAN_EXPORT(target, wiringPiI2CWrite);
    NAN_EXPORT(target, wiringPiI2CWriteReg8);
    NAN_EXPORT(target, wiringPiI2CWriteReg16);
    NAN_EXPORT(target, wiringPiI2CSetupInterface);
    NAN_EXPORT(target, wiringPiI2CSetup);
    NAN_EXPORT(target, wiringPiI2CClose);
  }

} //namespace nodewpi
