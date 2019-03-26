FROM openjdk:8-jdk
MAINTAINER matt@econda.de
VOLUME /tmp

COPY target/secondBean.jar /opt/app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","opt/app.jar"]
EXPOSE 8888