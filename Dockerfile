FROM frolvlad/alpine-oraclejdk8
VOLUME /tmp
ADD target/demo-1.0.jar app.jar
EXPOSE 8080
RUN sh -c 'touch /app.jar'
ENTRYPOINT ["java", "-jar", "/app.jar"]
