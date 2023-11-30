FROM openjdk:17-jdk
WORKDIR /app
COPY target/*.jar /app/carpooling.jar
EXPOSE 8088
CMD ["java","-jar","carpooling.jar"]