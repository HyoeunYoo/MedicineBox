#include "wiringSerial.h"
#include <wiringSerial.h>

// Func : int serialOpen(const char* device, const int baud)
namespace nodewpi {

NAN_METHOD(serialOpen) {
  
  SET_ARGUMENT_NAME(0, device);
  SET_ARGUMENT_NAME(1, baudrate);
  
  CHECK_ARGUMENTS_LENGTH_EQUAL(2);
  
  CHECK_ARGUMENT_TYPE_STRING(0);
  CHECK_ARGUMENT_TYPE_INT32(1);
  
  #if NODE_VERSION_AT_LEAST(0, 11, 0)
    String::Utf8Value device(GET_ARGUMENT_AS_STRING(0));
  #else
    String::AsciiValue device(GET_ARGUMENT_AS_STRING(0));
  #endif
  int baudrate = GET_ARGUMENT_AS_INT32(1);
  
  int res = ::serialOpen(*device, baudrate);
  
  info.GetReturnValue().Set(res);
}

// Func : void serialClose(const int fd)

NAN_METHOD(serialClose) {

  SET_ARGUMENT_NAME(0, fd);
  
  CHECK_ARGUMENTS_LENGTH_EQUAL(1);
  
  CHECK_ARGUMENT_TYPE_INT32(0);
  
  int fd = GET_ARGUMENT_AS_INT32(0);
  
  ::serialClose(fd);
  
  info.GetReturnValue().Set(Nan::Undefined());
}

// Func : void serialFlush(const int fd);

NAN_METHOD(serialFlush) {
  
  SET_ARGUMENT_NAME(0, fd);
  
  CHECK_ARGUMENTS_LENGTH_EQUAL(1);
  
  CHECK_ARGUMENT_TYPE_INT32(0);
  
  int fd = GET_ARGUMENT_AS_INT32(0);
  
  ::serialFlush(fd);
  
  info.GetReturnValue().Set(Nan::Undefined());
}

// Func : void serialPutchar(const int fd, const unsigned char c)

NAN_METHOD(serialPutchar) {
 
  SET_ARGUMENT_NAME(0, fd);
  SET_ARGUMENT_NAME(1, character);
  
  CHECK_ARGUMENTS_LENGTH_EQUAL(2);
  
  CHECK_ARGUMENT_TYPE_INT32(0);
  CHECK_ARGUMENT_TYPE_UINT32(1);
  
  int fd = GET_ARGUMENT_AS_INT32(0);
  unsigned char character = GET_ARGUMENT_AS_UINT32(1);
  
  ::serialPutchar(fd, character);
  
  info.GetReturnValue().Set(Nan::Undefined());
}

// Func : void serialPuts(const int fd, const char* s)

NAN_METHOD(serialPuts) {
 
  SET_ARGUMENT_NAME(0, fd);
  SET_ARGUMENT_NAME(1, string);
  
  CHECK_ARGUMENTS_LENGTH_EQUAL(2);
  
  CHECK_ARGUMENT_TYPE_INT32(0);
  CHECK_ARGUMENT_TYPE_STRING(1);
  
  int fd = GET_ARGUMENT_AS_INT32(0);
  Nan::Utf8String string(GET_ARGUMENT_AS_STRING(1));
  
  ::serialPuts(fd, *string);
  
  info.GetReturnValue().Set(Nan::Undefined());
}

// Func : void serialPrintf(const int fd, const char* message, ...)

NAN_METHOD(serialPrintf) {
  // Make serialPrintf a alias to serialPuts
  return serialPuts(info);
}

// Func : int serialDataAvail(const int fd)

NAN_METHOD(serialDataAvail) {
  
  SET_ARGUMENT_NAME(0, fd);
  
  CHECK_ARGUMENTS_LENGTH_EQUAL(1);
  
  CHECK_ARGUMENT_TYPE_INT32(0);
  
  int fd = GET_ARGUMENT_AS_INT32(0);
  
  int res = ::serialDataAvail(fd);
  
  info.GetReturnValue().Set(res);
}

// Func : int serialGetchar(const int fd)

NAN_METHOD(serialGetchar) {
  
  SET_ARGUMENT_NAME(0, fd);
  
  CHECK_ARGUMENTS_LENGTH_EQUAL(1);
  
  CHECK_ARGUMENT_TYPE_INT32(0);
  
  int fd = GET_ARGUMENT_AS_INT32(0);
  
  int res = ::serialGetchar(fd);
  
  info.GetReturnValue().Set(res);
}

NAN_MODULE_INIT(init_wiringSerial) {
  NAN_EXPORT(target, serialOpen);
  NAN_EXPORT(target, serialClose);
  NAN_EXPORT(target, serialFlush);
  NAN_EXPORT(target, serialPutchar);
  NAN_EXPORT(target, serialPuts);
  NAN_EXPORT(target, serialPrintf);
  NAN_EXPORT(target, serialDataAvail);
  NAN_EXPORT(target, serialGetchar);
}

} //namespace nodewpi

