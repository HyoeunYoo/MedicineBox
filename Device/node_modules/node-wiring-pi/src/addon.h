#ifndef _ADDON_H_
#define _ADDON_H_

#include <stdarg.h>

#include <algorithm>
#include <iterator>
#include <vector>

#include <nan.h>

  using namespace v8;

  // We don't want to include the whole node headers :)
/*  namespace node {
    namespace Buffer {
      bool HasInstance(v8::Handle<v8::Value> val);
      bool HasInstance(v8::Handle<v8::Object> val);
      char* Data(v8::Handle<v8::Value> val);
      char* Data(v8::Handle<v8::Object> val);
      size_t Length(v8::Handle<v8::Value> val);
      size_t Length(v8::Handle<v8::Object> val);
    }
  }*/

  bool find_string(const std::string & value, const std::vector<std::string> & validStrings );
  bool find_int(const int & value, const std::vector<int> & validInts );
  void throw_error(const char* format, ...);

    #define EXPORT_CONSTANT_INT(constant)                                         \
      do {                                                                        \
        v8::Isolate* isolate = target->GetIsolate();                              \
        v8::Local<v8::Context> context = isolate->GetCurrentContext();            \
        v8::Local<v8::String> constant_name =                                     \
            v8::String::NewFromUtf8(isolate, #constant);                          \
        v8::Local<v8::Integer> constant_value =                                    \
            v8::Integer::New(isolate, constant);                                    \
        v8::PropertyAttribute constant_attributes =                               \
            static_cast<v8::PropertyAttribute>(v8::ReadOnly | v8::DontDelete);    \
        target->DefineOwnProperty(context,                                        \
                                    constant_name,                                \
                                    constant_value,                               \
                                    constant_attributes).FromJust();              \
      }                                                                           \
      while (0)

      #define EXPORT_CONSTANT_STRING(constant)                                      \
        do {                                                                        \
          v8::Isolate* isolate = target->GetIsolate();                              \
          v8::Local<v8::Context> context = isolate->GetCurrentContext();            \
          v8::Local<v8::String> constant_name =                                     \
              v8::String::NewFromUtf8(isolate, #constant);                          \
          v8::Local<v8::String> constant_value =                                    \
              v8::String::NewFromUtf8(isolate, constant);                           \
          v8::PropertyAttribute constant_attributes =                               \
              static_cast<v8::PropertyAttribute>(v8::ReadOnly | v8::DontDelete);    \
          target->DefineOwnProperty(context,                                        \
                                      constant_name,                                \
                                      constant_value,                               \
                                      constant_attributes).FromJust();              \
        }                                                                           \
        while (0)

    #define EXPORT_CONSTANT_INT_ARRAY(constant, array, length) \
      do { \
        v8::Local<v8::Array> arr = v8::Array::New(isolate, length); \
        for (int i = 0; i < length; i++) { \
          arr->Set(i, v8::Int32::New(isolate, array[i])); \
        } \
          v8::Isolate* isolate = target->GetIsolate();                              \
          v8::Local<v8::Context> context = isolate->GetCurrentContext();            \
          v8::Local<v8::String> constant_name =                                     \
              v8::String::NewFromUtf8(isolate, #constant);                          \
          v8::Local<v8::Array> constant_value = arr;                                 \
          v8::PropertyAttribute constant_attributes =                               \
              static_cast<v8::PropertyAttribute>(v8::ReadOnly | v8::DontDelete);    \
          target->DefineOwnProperty(context,                                        \
                                      constant_name,                                \
                                      constant_value,                               \
                                      constant_attributes).FromJust();              \
        }                                                                           \
        while (0)

    #define EXPORT_CONSTANT_STRING_ARRAY(constant, array, length) \
      do { \
        v8::Local<v8::Array> arr = Nan::New<Array>(); \
        for (int i = 0; i < length; i++) { \
          arr->Set(i, Nan::New<String>(array[i]).ToLocalChecked()); \
        } \
                                                                                \
          v8::Isolate* isolate = target->GetIsolate();                              \
          v8::Local<v8::Context> context = isolate->GetCurrentContext();            \
          v8::Local<v8::String> constant_name =                                     \
              Nan::New<String>(#constant).ToLocalChecked();                          \
          v8::Local<v8::Array> constant_value = arr;                                 \
          v8::PropertyAttribute constant_attributes =                               \
              static_cast<v8::PropertyAttribute>(v8::ReadOnly | v8::DontDelete);    \
          target->DefineOwnProperty(context,                                        \
                                      constant_name,                                \
                                      constant_value,                               \
                                      constant_attributes).FromJust();              \
        }                                                                           \
        while (0)


  #define INT32(v) Nan::New<v8::Int32>(v)
  #define UINT32(v) Nan::New<v8::Uint32>(v)
  #define STRING(v) Nan::New<v8::String>(v).ToLocalChecked()
 

  #define SET_ARGUMENT_NAME(id, name) static const char* arg##id = #name
  #define GET_ARGUMENT_NAME(id) arg##id

  #define CHECK_ARGUMENTS_LENGTH_EQUAL(length) \
    if (info.Length() != length) { \
      throw_error("%s: arguments.length => (%i === %i) === false", __func__, info.Length(), length); \
    }

  #define CHECK_ARGUMENT_TYPE(id, istype) \
    if (!info[id]->istype()) { \
      throw_error("%s: %s(arguments['%s']) === false", __func__, #istype, GET_ARGUMENT_NAME(id)); \
    }

  #define CHECK_ARGUMENT_TYPE_ARRAY(id) CHECK_ARGUMENT_TYPE(id, IsArray)
  #define CHECK_ARGUMENT_TYPE_INT32(id) CHECK_ARGUMENT_TYPE(id, IsInt32)
  #define CHECK_ARGUMENT_TYPE_UINT32(id) CHECK_ARGUMENT_TYPE(id, IsUint32)
  #define CHECK_ARGUMENT_TYPE_NUMBER(id) CHECK_ARGUMENT_TYPE(id, IsNumber)
  #define CHECK_ARGUMENT_TYPE_STRING(id) CHECK_ARGUMENT_TYPE(id, IsString)
  #define CHECK_ARGUMENT_TYPE_FUNCTION(id) CHECK_ARGUMENT_TYPE(id, IsFunction)
  #define CHECK_ARGUMENT_TYPE_OBJECT(id) CHECK_ARGUMENT_TYPE(id, IsObject)
  #define CHECK_ARGUMENT_TYPE_NODE_BUFFER(id) \
    if (!(info[id]->IsObject() && node::Buffer::HasInstance(info[id]))) { \
      throw_error("%s: %s(arguments['%s']) === false", __func__, "isBuffer", GET_ARGUMENT_NAME(id)); \
    }

  #define CHECK_ARGUMENT_ARRAY_LENGTH(id, length) \
    if (!(v8::Local<v8::Array>::Cast(info[id])->Length() == length)) { \
      throw_error("%s: (arguments['%s'].length === %i) === false", __func__, GET_ARGUMENT_NAME(id), length); \
    }

  #define GET_ARGUMENT_AS_TYPE(id, type) info[id]->type()

  #define GET_ARGUMENT_AS_INT32(id) GET_ARGUMENT_AS_TYPE(id, Int32Value)
  #define GET_ARGUMENT_AS_UINT32(id) GET_ARGUMENT_AS_TYPE(id, Uint32Value)
  #define GET_ARGUMENT_AS_NUMBER(id) GET_ARGUMENT_AS_TYPE(id, NumberValue)
  #define GET_ARGUMENT_AS_STRING(id) GET_ARGUMENT_AS_TYPE(id, ToString)
  #define GET_ARGUMENT_AS_LOCAL_FUNCTION(id) v8::Local<v8::Function>::Cast(info[id])

  #if !NODE_VERSION_AT_LEAST(0, 11, 0)
    #define GET_ARGUMENT_AS_PERSISTENT_FUNCTION(id) v8::Persistent<v8::Function>::New(GET_ARGUMENT_AS_LOCAL_FUNCTION(id))
  #endif

  #define THROW_INVALID_ARGUMENT_EXCEPTION(id, value) \
    throw_error("%s: arguments['%s'].value['%s'] is not a valid input.", __func__, GET_ARGUMENT_NAME(id), value); \

#endif
