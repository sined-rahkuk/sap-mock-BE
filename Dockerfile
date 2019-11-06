FROM openjdk:11-jre

LABEL maintainer="s.jauker@mwaysolutions.com"

EXPOSE 8080
VOLUME /tmp

COPY build/libs/sap-mock-backend-*.jar app.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
