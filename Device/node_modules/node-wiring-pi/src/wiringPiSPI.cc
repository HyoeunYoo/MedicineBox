#include "wiringPiSPI.h"
#include <wiringPiSPI.h>
#include <unistd.h>

namespace nodewpi {
// Func : int wiringPiSPIGetFd(int channel)

NAN_METHOD(wiringPiSPIGetFd) {

  
  SET_ARGUMENT_NAME(0, channel);
  
  CHECK_ARGUMENTS_LENGTH_EQUAL(1);
  
  CHECK_ARGUMENT_TYPE_INT32(0);
  
  int channel = GET_ARGUMENT_AS_INT32(0);
  const std::vector<int> validChannels = { 0, 1 };
  
  if(find_int(channel, validChannels)) {
    int res = ::wiringPiSPIGetFd(channel);
    info.GetReturnValue().Set(res);
  } else {
    //throw error
  }
}

// Func : wiringPiSPIDataRW(int channel, unsigned char* data, int len)

NAN_METHOD(wiringPiSPIDataRW) {
  
  SET_ARGUMENT_NAME(0, channel);
  SET_ARGUMENT_NAME(1, data);
  
  CHECK_ARGUMENTS_LENGTH_EQUAL(2);
  
  CHECK_ARGUMENT_TYPE_INT32(0);
  CHECK_ARGUMENT_TYPE_NODE_BUFFER(1);
  
  const std::vector<int> validChannels = { 0, 1 };

  int channel = GET_ARGUMENT_AS_INT32(0);
  Local<Object> spiDataObj = info[1]->ToObject();
  unsigned int length = info[1]->Uint32Value();
  char* data = node::Buffer::Data(spiDataObj);
  
  if(find_int(channel, validChannels)) {
      ::wiringPiSPIDataRW(channel, reinterpret_cast<unsigned char*>(data), length);
      info.GetReturnValue().Set(Nan::CopyBuffer(data, length).ToLocalChecked());
  } else {
    //throw error
  }
}

// Func : int wiringPiSPISetup(int channel, int speed)

NAN_METHOD(wiringPiSPISetup) {
  
  SET_ARGUMENT_NAME(0, channel);
  SET_ARGUMENT_NAME(1, speed);
  
  CHECK_ARGUMENTS_LENGTH_EQUAL(2);
  
  CHECK_ARGUMENT_TYPE_INT32(0);
  CHECK_ARGUMENT_TYPE_INT32(1);
  
  const std::vector<int> validChannels = { 0, 1 };

  int channel = GET_ARGUMENT_AS_INT32(0);
  int speed = GET_ARGUMENT_AS_INT32(1);
  
  if(find_int(channel, validChannels)) {
    int res = ::wiringPiSPISetup(channel, speed);
    info.GetReturnValue().Set(res);
  } else {
    //throw error
  }
}

NAN_METHOD(wiringPiSPISetupMode) {
  
  SET_ARGUMENT_NAME(0, channel);
  SET_ARGUMENT_NAME(1, speed);
  SET_ARGUMENT_NAME(2, mode);
  
  CHECK_ARGUMENTS_LENGTH_EQUAL(3);
  
  CHECK_ARGUMENT_TYPE_INT32(0);
  CHECK_ARGUMENT_TYPE_INT32(1);
  CHECK_ARGUMENT_TYPE_INT32(2);
  
  int channel = GET_ARGUMENT_AS_INT32(0);
  int speed = GET_ARGUMENT_AS_INT32(1);
  int mode = GET_ARGUMENT_AS_INT32(2);
  
  const std::vector<int> validChannels = { 0, 1 };
  const std::vector<int> validModes = { 0, 1, 2, 3 };
  
  if (find_int(channel, validChannels)) {
    if (find_int(mode, validModes)) {
      int res = ::wiringPiSPISetupMode(channel, speed, mode);
      info.GetReturnValue().Set(res);
    } else {
      //throw mode error
    } 
  } else { 
    // throw channel error
  }
}

// Func : void wiringPiSPIClose(const int fd)
// Description : This closes opened SPI file descriptor
// fd is file descriptor returned either from wiringPiSPISetup or wiringPiSPISetupMode

NAN_METHOD(wiringPiSPIClose) {

  SET_ARGUMENT_NAME(0, fd);

  CHECK_ARGUMENTS_LENGTH_EQUAL(1);

  CHECK_ARGUMENT_TYPE_INT32(0);

  int fd = GET_ARGUMENT_AS_INT32(0);

  ::close(fd);

}

NAN_MODULE_INIT(init_wiringPiSPI) {
   NAN_EXPORT(target, wiringPiSPIGetFd);
   NAN_EXPORT(target, wiringPiSPIDataRW);
   NAN_EXPORT(target, wiringPiSPISetup);
   NAN_EXPORT(target, wiringPiSPISetupMode);
   NAN_EXPORT(target, wiringPiSPIClose);
}

} //namespace nodewpi

