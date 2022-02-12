FROM openjdk:11
VOLUME /tmp
COPY app.jar /
EXPOSE 8080
ENTRYPOINT ["java","-jar", "/app.jar"]
