FROM eclipse-temurin:17-jre-alpine
COPY build/libs/product-management.jar /product-management.jar
EXPOSE 8080
CMD ["java", "-Xms64m", "-Xmx512m", "-jar", "/product-management.jar"]
