FROM openjdk:8-alpine

# Define working directory.
WORKDIR /data

ARG APP_PATH

COPY $APP_PATH rtb-exchange.jar

ENV env "prod"

# Run rtb-exchange.
CMD java -Dconfig.resource=application.${env}.conf -jar rtb-exchange.jar

EXPOSE 8081
