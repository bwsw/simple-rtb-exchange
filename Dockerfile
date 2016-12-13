FROM openjdk:8-alpine

# Define working directory.
WORKDIR /data

ARG APP_PATH

COPY $APP_PATH rtb-exchange.jar

# Run rtb-exchange.
ENTRYPOINT ["java"]
CMD ["-jar", "rtb-exchange.jar"]

EXPOSE 8081
