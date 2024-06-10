FROM eclipse-temurin:21-jre-alpine

COPY target/gw-svc*.jar /app/gw-svc.jar
WORKDIR /app
#Expose server port
EXPOSE 8080

#Expose gRPC port
EXPOSE 8085
CMD ["java", "-jar", "gw-svc.jar"]