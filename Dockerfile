FROM openjdk:17
EXPOSE 8080
ADD target/project-etkinlik.jar project-etkinlik.jar
ENTRYPOINT ["java", "-jar","/project-etkinlik.jar"]