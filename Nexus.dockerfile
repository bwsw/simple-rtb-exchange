FROM openjdk:8-alpine

# Define working directory.
WORKDIR /data

ARG URL
ARG USERNAME
ARG PASSWORD

# Download rtb-exchange from nexus.
RUN \
    export REPO_TYPE="$(echo "${VERSION}" | sed -n 's/.*SNAPSHOT.*/-snapshot/p')" && \
    wget --user=${USERNAME} --password=${PASSWORD} -O rtb-exchange.jar ${URL}

ENV env=prod

# Run rtb-exchange.
CMD java -Dconfig.resource=application.${env}.conf -jar rtb-exchange.jar

EXPOSE 8081
