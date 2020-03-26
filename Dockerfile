FROM openjdk:8-alpine
RUN addgroup --gid 510 -S spring \
    && adduser --uid 510 -S spring -G spring

USER spring:spring
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

EXPOSE 8080
EXPOSE 9090

ENTRYPOINT ["java","-jar","/app.jar"]