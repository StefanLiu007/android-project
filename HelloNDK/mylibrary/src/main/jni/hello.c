#include "com_example_mylibrary_NDKString.h"

JNIEXPORT jstring JNICALL Java_com_example_mylibrary_NDKString_NDKFromC
  (JNIEnv * env, jclass jclass){
     return (*env)->NewStringUTF(env,"from d");
  }