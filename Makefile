.PHONY : clone swig build example compile-java
CFLAGS="-fpic"
CC=gcc
# Try to find jni.h and jni_md.h (modify this if it does not compile)
JNI=$(shell find /usr/ -type d -name "jvm" -print0 2>/dev/null |xargs -0 -I {} find {} -name "jni.h" -print0 |xargs -0 -I % dirname %|head -n 1) 
JNI_MD=$(shell find /usr/ -type d -name "jvm" -print0 2>/dev/null |xargs -0 -I {} find {} -name "jni_md.h" -print0 |xargs -0 -I % dirname %|head -n 1)
JVM_INCLUDE=-I$(JNI) -I$(JNI_MD)
GENERATED_JAVA_DIR="generated-java"
GENERATED_C_DIR="generated-c"
MICRO_ECC_REV="601bd11062c551b108adbb43ba99f199b840777c"

all : compile compile-java example
all-generate : clone clean swig compile compile-java example 

clone : 
	rm -Rf micro-ecc
	git clone https://github.com/kmackay/micro-ecc.git
	(cd micro-ecc && git reset --hard ${MICRO_ECC_REV})
swig : swig-clean
	mkdir -p $(GENERATED_JAVA_DIR)/src/main/java/kmackay/swig/uECC
	swig -java -package kmackay.swig.uECC swig/uECC.i 
	mv swig/*.java $(GENERATED_JAVA_DIR)/src/main/java/kmackay/swig/uECC 
	mv swig/*.c $(GENERATED_C_DIR)
compile : 
	echo $(find /usr/lib -name "jni.h" |head -n 1)
	$(CC) $(CFLAGS) -c $(GENERATED_C_DIR)/uECC_wrap.c $(JVM_INCLUDE) -Imicro-ecc
	$(CC) $(CFLAGS) -c micro-ecc/uECC.c
	ld -G uECC_wrap.o uECC.o -o libuECC.so
	rm -f *.o 
	mkdir -p $(GENERATED_JAVA_DIR)/src/main/resources/
	mv libuECC.so $(GENERATED_JAVA_DIR)/src/main/resources/
compile-java :
	cp -r java/pom.xml $(GENERATED_JAVA_DIR)
	mkdir -p $(GENERATED_JAVA_DIR)/src/main/
	mkdir -p $(GENERATED_JAVA_DIR)/src/main/resources
	mkdir -p $(GENERATED_JAVA_DIR)/src/test/
	mkdir -p $(GENERATED_JAVA_DIR)/src/resources/
	cp -r java/src/main/* $(GENERATED_JAVA_DIR)/src/main/
	cp -r java/src/test/* $(GENERATED_JAVA_DIR)/src/test/
	(cd $(GENERATED_JAVA_DIR) && mvn install)
	jar tf $(GENERATED_JAVA_DIR)/target/micro-ecc* 
	cp $(GENERATED_JAVA_DIR)/target/*.jar .
java-clean :
	rm -Rf $(GENERATED_JAVA_DIR)
swig-clean :
	rm -f swig/*.java
	rm -f swig/*.c
	rm -Rf $(GENERATED_JAVA_DIR)/src/main/java/kmackay
clean : java-clean clean-example
	rm -f *.o 
clean-example : 
	(cd example && mvn clean)
example :
	(cd example && mvn package)
	echo "run example"
	java -cp $(shell pwd) -jar example/target/uECC-0.0.1-SNAPSHOT-jar-with-dependencies.jar
