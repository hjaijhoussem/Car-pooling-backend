#FROM openjdk:17-jdk
#WORKDIR /app
#COPY target/*.jar /app/carpooling.jar
#EXPOSE 8088
#CMD ["java","-jar","carpooling.jar"]
#CMD ["java","-jar","carpooling.jar", "--spring.profiles.active=${PROFILE}"]
# testing webhook

# Stage 1: Build

FROM maven:3.8-openjdk-17 as builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM openjdk:17-jdk
WORKDIR /app
COPY --from=builder /app/target/*.jar /app/carpooling.jar
EXPOSE 8088
CMD ["java", "-jar", "carpooling.jar"]
