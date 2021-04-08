#!/bin/bash

function clean_up {
  docker-compose down
  if [ -z "$1" ]
    then
      echo -e "Bye!\n"
    else
      echo -e $1
    fi
}

echo "Building jdbc-demo"
cd jdbc-demo
mvn clean package -DskipTests
cd ..
cd r2dbc-demo
mvn clean package -DskipTests
cd ..

echo "Starting docker"
docker-compose build
docker-compose up -d

sleep 3

# function test_service_available {
#
# }
#
# test_service_available 8000
# test_service_available 8001

trap clean_up EXIT

echo -e '''

==============================================================================================================
Find JDBC Demo in http://localhost:8000/
Find R2DBC Demo in http://localhost:8001/
PostgreSQL in localhost:5432 | username: postgres | password: password
Grafana in http://localhost:3000/
==============================================================================================================

Use <ctrl>-c to quit'''

read -r -d '' _ </dev/tty
