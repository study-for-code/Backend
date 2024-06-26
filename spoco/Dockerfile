FROM openjdk:17-jdk-slim

RUN yum -y update && \
    yum -y install python3 gcc-c++ nodejs && \
    yum clean all

COPY ./build/libs/spoco-0.0.1-SNAPSHOT.jar /spoco.jar

WORKDIR /

ENTRYPOINT ["java", "-jar", "/spoco.jar"]