FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/my_practice_springboot-1.0.0.jar
COPY ${JAR_FILE} my_practice_springboot.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "my_practice_springboot.jar"]