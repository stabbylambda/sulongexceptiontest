# Sulong Exception Test

This repo contains a minimal use-case for a bug in Sulong's C++ exception handling. When we try to catch a `std::runtime_error` in the C++ code, we get the following abort message printed to stderr.
```
libc++abi.dylib: Type error in sulong_eh_canCatch(…)
Syscalls (write) not supported on this OS.
Syscalls (write) not supported on this OS.
Syscalls (write) not supported on this OS.
Syscalls (write) not supported on this OS.
Syscalls (write) not supported on this OS.
Syscalls (write) not supported on this OS.
...
``` 

The Syscalls (write) portion runs forever (I know the syscalls message is because I'm on macOS. I haven't run it on a linux vm yet.

I tracked this down to https://github.com/graalvm/sulong/blob/5f7532e892ca25e092367d3825f05015ff2c3f7e/projects/com.oracle.truffle.llvm.libraries.bitcode/libcxxabi/cxa_exception.cpp#L706

In the case that the C++ code throws a `std::runtime_error`, `et` is null and `ex->exceptionType` is `St13runtime_error`
The exception never gets marshalled over into the Java code that I’ve got, so I’m unable to catch the error.
I would have expected it to get wrapped in a RuntimeException so that I could handle it.
