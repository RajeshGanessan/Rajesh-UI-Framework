FROM openjdk:8u191-jre-alpine3.8

RUN apk add curl jq

WORKDIR /usr/mystartupEquity

#Adding jar files from the host
ADD target/MSE-docker.jar        MSE-docker.jar
ADD target/MSE-docker-tests.jar  MSE-docker-tests.jar
ADD target/libs      			 libs

#Adding testData and required Files
ADD resources/TestData/ExcelData.xlsx  			resources/TestData/ExcelData.xlsx
ADD resources/configuration/Data.properties  	resources/configuration/Data.properties

#Addinf Suite-XML files
ADD src/test/TestRunner/testng.xml			resources/TestRunner/testng.xml

ENTRYPOINT java -cp MSE-docker.jar:MSE-docker-tests.jar:libs/* -DREMOTE=$REMOTE -DBROWSER=$BROWSER -DHUB_HOST=$HUB_HOST org.testng.TestNG ./resources/TestRunner/$MODULE

