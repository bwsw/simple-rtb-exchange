FROM ci.bw-sw.com:5000/rtb-dev-team/oracle-java:8

# Define working directory.
WORKDIR /data

ARG VERSION

# Download rtb-exchange from nexus.
RUN \
    export REPO_TYPE="$(echo "${VERSION}" | sed -n 's/.*SNAPSHOT.*/-snapshot/p')" && \
    wget --user=deployment --password=Bg3MWyM54Mtq4tK8 -O rtb-exchange.jar \
    "http://rtb-ci.z1.netpoint-dc.com:8081/nexus/service/local/artifact/maven/content?r=bitworks-rtb${REPO_TYPE}&g=com.bitworks&a=rtb-exchange_2.11&v=$VERSION&c=jar-with-dependencies"

# Run rtb-exchange.
ENTRYPOINT ["java"]
CMD ["-jar", "rtb-exchange.jar"]

EXPOSE 8081
