%module uECC
%include "stdint.i"
%include "typemaps.i"
%include "arrays_java.i";

%include "carrays.i"
%array_functions(unsigned char, uint8Array);

%{
/* Includes the header in the wrapper code */
#include "uECC.h"
%}

/* Parse the header file to generate wrappers */
%include "../micro-ecc/uECC.h"
%include "../micro-ecc/types.h"


