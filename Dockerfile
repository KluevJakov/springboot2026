FROM bellsoft/liberica-runtime-container:jre-25-slim-stream-glibc
WORKDIR /app
COPY /build/libs/springproject-*-SNAPSHOT.jar app.jar
ENV SPRING_PROFILES_ACTIVE=prod
ENTRYPOINT ["java", "-jar", "app.jar"]