#include <stdexcept>
#include <iostream>
#include "polyglot.h"

#if defined(__cplusplus)
extern "C" {
#endif

void throwException() {
    // just throw an exception
    throw std::runtime_error("thrown from C++");
}

void catchInCPlusPlus() {
    try {
        throwException();
	} catch (std::runtime_error &ex) {
	    std::cout << "Caught from C++";
    }
}

#if defined(__cplusplus)
}
#endif
