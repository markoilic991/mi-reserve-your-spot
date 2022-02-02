FROM openjdk:11
ADD build/libs/reserve-your-spot-0.0.2-SNAPSHOT.jar app.jar
COPY . .
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
