
#include "addon.h"

bool find_string(const std::string & value, const std::vector<std::string> & validStrings ) {
  return std::find(validStrings.begin(), validStrings.end(), value) != validStrings.end();
}

bool find_int(const int & value, const std::vector<int> & validInts ) {
  return std::find(validInts.begin(), validInts.end(), value) != validInts.end();
}


void throw_error(const char* format, ...) {
  char buffer[256];
  va_list args;
  va_start(args, format);
  vsnprintf(buffer, 156, format, args);
  va_end(args);

  Nan::ThrowError(Nan::New<String>(buffer).ToLocalChecked());
}
