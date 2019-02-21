#!/bin/bash

appname="SpringBoot_Simp.jar"

rm -f tpid

#启动

nohup /data/jdk1.8.0_112/bin/java -jar SpringBoot_Simp.jar --spring.profiles.active=pro > /dev/null 2>&1 &
echo $! > tpid 
echo Start Success!
