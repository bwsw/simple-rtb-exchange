FROM openjdk:8-alpine

# Define working directory.
WORKDIR /opt/rtb-exchange

ARG URL
ARG USERNAME
ARG PASSWORD

# Download rtb-exchange from nexus.
RUN \
    wget --user=${USERNAME} --password=${PASSWORD} -O rtb-exchange.jar ${URL}

ENV env=prod

VOLUME ["/opt/rtb-exchange/logs"]

# Run rtb-exchange.
CMD java -Dconfig.resource=application.${env}.conf -jar rtb-exchange.jar

EXPOSE 8081
