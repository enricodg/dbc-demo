#!/bin/bash

mkdir jdbc-result-1000-ccu
mkdir r2dbc-result-1000-ccu

apache-jmeter-5.4.1/bin/jmeter -n -t jdbc-test.jmx -l ./jdbc-result-1000-ccu/jdbc-result-1000-ccu.jtl -e -o ./jdbc-result-1000-ccu

sleep 60

apache-jmeter-5.4.1/bin/jmeter -n -t r2dbc-test.jmx -l ./r2dbc-result-1000-ccu/r2dbc-result-1000-ccu.jtl -e -o ./r2dbc-result-1000-ccu
