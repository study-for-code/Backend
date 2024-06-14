FROM ghcr.io/graalvm/graalvm-ce:ol7-java17-22.3.1

RUN yum -y update && \
    yum -y install python3 gcc-c++ nodejs && \
    yum clean all

COPY ./build/libs/spoco-0.0.1-SNAPSHOT.jar /spoco.jar

WORKDIR /

# 환경 변수 설정
ENV SPRING_DATASOURCE_URL=jdbc:h2:tcp://database/~/spoco
ENV SPRING_DATASOURCE_USERNAME=sa
ENV SPRING_DATASOURCE_PASSWORD=
ENV SPRING_DATA_MONGODB_URI=mongodb://root:root@mongodb:27017/mongodb?authSource=admin

ENTRYPOINT ["java", "-jar", "/spoco.jar"]
