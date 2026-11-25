FROM eclipse-temurin:17-jre
WORKDIR /app
COPY target/devops.jar devops.jar
CMD ["java", "-jar", "devops.jar", "db:3306", "30000"]
