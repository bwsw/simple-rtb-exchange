FROM ci.bw-sw.com:5000/rtb-dev-team/oracle-java:8

# Define working directory.
WORKDIR /data

# Define commonly used JAVA_HOME variable.
ENV JAVA_HOME /usr/lib/jvm/java-8-oracle

ARG VERSION

# Download rtb-exchange from nexus.
RUN export REPO_TYPE="$(echo "${VERSION}" | sed -n 's/.*SNAPSHOT.*/-snapshot/p')" &&  wget --user=deployment --password=Bg3MWyM54Mtq4tK8 "http://rtb-ci.z1.netpoint-dc.com:8081/nexus/service/local/artifact/maven/content?r=bitworks-rtb${REPO_TYPE}&g=com.bitworks&a=rtb-exchange_2.11&v=$VERSION&c=assembly" -O rtb-exchange.jar

# Run frtb-exchange.
ENTRYPOINT ["java"]
CMD ["-jar", "rtb-exchange.jar"]

EXPOSE 8081
