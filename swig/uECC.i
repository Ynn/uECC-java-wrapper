%module uECCSwig
%include "stdint.i"
%include "typemaps.i"
%include "arrays_java.i";

%apply(uint8_t *STRING, size_t LENGTH) { (uint8_t *str, size_t len) };

%include "carrays.i"
%array_functions(unsigned char, uint8Array);

%{
/* Includes the header in the wrapper code */
#include "uECC.h"
%}

/* Parse the header file to generate wrappers */
%include "../micro-ecc/uECC.h"
%include "../micro-ecc/types.h"


