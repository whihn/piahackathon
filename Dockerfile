#FROM openjdk:8-jdk-alpine
#VOLUME /tmp
#ARG DEPENDENCY=target/dependency
#COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
#COPY ${DEPENDENCY}/META-INF /app/META-INF
#COPY ${DEPENDENCY}/BOOT-INF/classes /app
#ENTRYPOINT ["java","-cp","app:app/lib/*","ImageRendererApplication.Application"]

FROM openjdk:8-jdk
MAINTAINER matt@econda.de
VOLUME /tmp
    
COPY target/secondBean.jar /opt/app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","opt/app.jar"]
EXPOSE 8888