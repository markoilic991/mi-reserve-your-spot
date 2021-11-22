FROM openjdk:11
ADD build/libs/reserve-your-spot-0.0.1-SNAPSHOT.jar dockerimage.jar
EXPOSE 8080
RUN bash -c 'touch /dockerimage.jar'
ENTRYPOINT ["java", "-jar", "dockerimage.jar"]
