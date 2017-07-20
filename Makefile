.PHONY : clone swig build example compile-java
CFLAGS="-fpic"
CC=gcc
# Try to find jni.h and jni_md.h (modify this if it does not compile)
JNI=$(shell find /usr/ -type d -name "jvm" -print0 2>/dev/null |xargs -0 -I {} find {} -name "jni.h" -print0 |xargs -0 -I % dirname %|head -n 1) 
JNI_MD=$(shell find /usr/ -type d -name "jvm" -print0 2>/dev/null |xargs -0 -I {} find {} -name "jni_md.h" -print0 |xargs -0 -I % dirname %|head -n 1)
JVM_INCLUDE=-I$(JNI) -I$(JNI_MD)

all : clean swig compile compile-java example

clone : 
	rm -Rf micro-ecc
	git clone https://github.com/kmackay/micro-ecc.git
swig : swig-clean
	mkdir -p java/src/main/java/kmackay/swig/uECC
	swig -java -package kmackay.swig.uECC swig/uECC.i 
	mv swig/*.java java/src/main/java/kmackay/swig/uECC 
compile : clean swig
	echo $(find /usr/lib -name "jni.h" |head -n 1)
	$(CC) $(CFLAGS) -c swig/uECC_wrap.c $(JVM_INCLUDE) -Imicro-ecc
	$(CC) $(CFLAGS) -c micro-ecc/uECC.c
	ld -G uECC_wrap.o uECC.o -o libuECC.so
	rm -f *.o 
	mkdir -p java/src/main/resources/
	mv libuECC.so java/src/main/resources/
compile-java :
	(cd java && mvn install)
	jar tf java/target/micro-ecc* 
	cp java/target/*.jar .
java-clean :
	(cd java && mvn clean)
swig-clean :
	rm -f swig/*.java
	rm -f swig/*.c
	rm -Rf java/src/main/java/kmackay
clean : swig-clean java-clean clean-example
	rm -f *.o 
	rm -Rf ../src/java
clean-example : 
	(cd example && mvn clean)
example :
	(cd example && mvn package)
	java -cp $(shell pwd) -jar example/target/uECC-0.0.1-SNAPSHOT-jar-with-dependencies.jar
