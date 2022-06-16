FROM openjdk:latest

WORKDIR /home/temp
COPY ./build/libs/simple-document-managment-app-0.0.1-SNAPSHOT.jar ./app.jar
CMD ["java", "-jar", "app.jar"]
