FROM eclipse-temurin:21-jre-alpine

COPY target/gw-svc*.jar /app/gw-svc.jar
WORKDIR /app
#Expose server port
EXPOSE 50070
#Expose gRPC port
EXPOSE 50075
CMD ["java", "-jar", "gw-svc.jar"]