FROM ghcr.io/graalvm/graalvm-ce:ol7-java17-22.3.1

RUN yum -y update && \
    yum -y install python3 && \
    yum -y install gcc-c++ && \
    yum -y install nodejs && \
    yum clean all

# Install Java 21.0.3
RUN curl -L -o /tmp/jdk-21.0.3_linux-x64_bin.tar.gz "https://download.oracle.com/java/21/latest/jdk-21_linux-x64_bin.tar.gz" && \
    mkdir -p /opt/java-21 && \
    tar -xzvf /tmp/jdk-21.0.3_linux-x64_bin.tar.gz -C /opt/java-21 --strip-components=1 && \
    rm /tmp/jdk-21.0.3_linux-x64_bin.tar.gz

COPY ./build/libs/spoco-0.0.1-SNAPSHOT.jar /spoco.jar

WORKDIR /

ENTRYPOINT ["java", "-jar", "/spoco.jar"]