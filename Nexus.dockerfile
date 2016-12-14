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

ENV env=

# Run rtb-exchange.
CMD ["./docker-startup.sh"]

EXPOSE 8081
