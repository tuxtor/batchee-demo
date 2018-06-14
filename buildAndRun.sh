#!/bin/sh
mvn clean package && docker build -t com.nabenik/batchee-demo .
docker rm -f batchee-demo || true && docker run -d -p 8080:8080 -p 4848:4848 --name batchee-demo com.nabenik/batchee-demo 
