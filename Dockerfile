FROM openjdk:8-alpine

# Define working directory.
WORKDIR /data

ARG APP_PATH

COPY $APP_PATH rtb-exchange.jar
COPY docker-startup.sh docker-startup.sh

ENV env=

# Run rtb-exchange.
CMD ["./docker-startup.sh"]

EXPOSE 8081
