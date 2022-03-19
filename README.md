# dbc-demo

This project is made to compare the performance between 2 database driver in Java.  
1. [R2DBC](https://r2dbc.io/) (Reactive Relational Database Connectivity)
2. [JDBC](https://docs.oracle.com/javase/tutorial/jdbc/basics/index.html) (Java Database Connectivity)

## Prequisites

1. Java JDK 11
2. Spring Boot 2.4.4
3. Docker
4. JMeter (included in [here](https://github.com/enricodg/dbc-demo/tree/master/apache-jmeter-5.4.1))

To run all the services, all you need to do is to execute [run.sh](https://github.com/enricodg/dbc-demo/blob/master/run.sh) from your terminal.  
You can change the [jdbc test plan](https://github.com/enricodg/dbc-demo/blob/master/test.jmx) and [r2dbc test plan](https://github.com/enricodg/dbc-demo/blob/master/r2dbc-test.jmx) using JMeter by editing `test.jmx` and `r2dbc-test.jmx`.  
Execute [test.sh](https://github.com/enricodg/dbc-demo/blob/master/test.sh) script to run the test.  
```
Find JDBC Demo in http://localhost:8000/
Find R2DBC Demo in http://localhost:8001/
PostgreSQL in localhost:5432 | username: postgres | password: password
Grafana in http://localhost:3000/
```
