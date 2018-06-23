echo "Compiling c++"
clang++ -O1 -c -std=c++11 -emit-llvm \
    -I$JAVA_HOME/jre/languages/llvm \
    ./cpp/ExceptionTest.cpp \
    -o src/main/resources/bitcode.bc
