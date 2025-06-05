FROM openjdk:17-jdk-slim
WORKDIR /app
COPY proyectoventasbazar/target/proyectoventasbazar-0.0.1-SNAPSHOT.jar app.jar
COPY scripts/wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh
ENTRYPOINT ["/wait-for-it.sh", "db:3306", "--", "java", "-jar", "app.jar"]
EXPOSE 8080



