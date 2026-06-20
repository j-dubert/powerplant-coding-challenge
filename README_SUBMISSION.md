# Submission README
Prerequisites
- Java 11+ installed (or the Java version required by the project)
- Maven (mvn) installed
- Port 8888 available

Build
1. From the project root (where `pom.xml` is located):
   mvn -q clean package

Run (development)
- To launch the application:
  mvn spring-boot:run

Run (packaged jar)
1. After `mvn package`, run the jar and force the port:
   java -jar target/*-SNAPSHOT.jar --server.port=8888