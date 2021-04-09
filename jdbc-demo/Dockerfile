FROM maven:3.8.1-jdk-11 as build

WORKDIR /build
COPY . /build
RUN mvn -f /build/pom.xml clean package -DskipTests

FROM adoptopenjdk/openjdk11:alpine-jre

COPY --from=build /build/target/app.jar /build/app.jar

RUN wget http://download-gcdn.ej-technologies.com/jprofiler/jprofiler_linux_12_0_2.tar.gz -P /tmp/ \
    && tar -xzf /tmp/jprofiler_linux_12_0_2.tar.gz -C /usr/local \
    && rm /tmp/jprofiler_linux_12_0_2.tar.gz

CMD ["java", "-agentpath:/usr/local/jprofiler12.0.2/bin/linux-x64/libjprofilerti.so=port=8849", "-Xmx1024m", "-Dserver.port=8000", "-Dspring.profiles.active=dev", "-jar", "/build/app.jar"]
EXPOSE 8000
EXPOSE 8849
