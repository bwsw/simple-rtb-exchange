FROM openjdk:8-alpine

# Define working directory.
WORKDIR /data

ARG VERSION
ARG USERNAME
ARG PASSWORD

# Download rtb-exchange from nexus.
RUN \
    export REPO_TYPE="$(echo "${VERSION}" | sed -n 's/.*SNAPSHOT.*/-snapshot/p')" && \
    wget --user=${USERNAME} --password=${PASSWORD} -O rtb-exchange.jar \
    "http://rtb-ci.z1.netpoint-dc.com:8081/nexus/service/local/artifact/maven/content?r=bitworks-rtb${REPO_TYPE}&g=com.bitworks&a=rtb-exchange_2.11&v=$VERSION&c=jar-with-dependencies"

ENV env=

# Run rtb-exchange.
CMD if [[ -z ${env} ]]; \
        then java -jar rtb-exchange.jar; \
        else java -Dconfig.resource=application.${env}.conf -jar rtb-exchange.jar; \
    fi

EXPOSE 8081
