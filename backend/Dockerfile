FROM java:8-alpine
RUN mkdir -p /app /app/resources
WORKDIR /app
COPY target/*-standalone.jar .
CMD java -jar kahvilat-backend-0.1.0-standalone.jar
EXPOSE 8080
