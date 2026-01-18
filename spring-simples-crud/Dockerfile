FROM eclipse-temurin:17-jdk-jammy as builder
WORKDIR /opt/app
RUN groupadd -g 1234 customgroup && \
    useradd -m -u 1234 -g customgroup customuser
RUN chown 1234:1234 /opt/app
USER 1234
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY ./src ./src
RUN ./mvnw clean install
 
FROM eclipse-temurin:17-jre-jammy
WORKDIR /opt/appd
EXPOSE 8080
COPY --from=builder /opt/app/target/*.jar /opt/app/*.jar
ENTRYPOINT ["java", "-jar", "/opt/app/*.jar"]