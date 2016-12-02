ENV:=e2e
BIDDER_HOST:=rtb-ci.z1.netpoint-dc.com:8083
REPORT_PATH:=../target/test-reports/

app_start: app_stop
	docker network create rtb-ci-network || true ; \
	docker network connect --alias=db rtb-ci-network private-postgres || true ; \
	docker run --name rtb-exchange-e2e -d -p 8082:8081 -v `cd "../target/scala-2.11/"; pwd`:/workdir -w=/workdir --network=rtb-ci-network  isuper/java-oracle:server_jre_8 /usr/bin/java -Dconfig.resource=application.$(ENV).conf -jar `find ../target/scala-2.11 -iname "*-assembly.jar" -printf "%f"` ; \
    sleep 3 ; \

app_stop:
	docker stop rtb-exchange-e2e || true && docker rm rtb-exchange-e2e || true

prepare:
	$(MAKE) -C ../db run env=e2e command=update

execute : prepare app_start run app_stop

run:
	for row in `cat test_cases`; do \
		sqlfile=sql/`echo $$row | cut -d, -f1` ; \
		col=`echo $$row | cut -d, -f2` ; \
		echo "asdasd : $$col" ; \
		./query.sh e2e $(BIDDER_HOST) $$sqlfile ; \
		newman run collections/$$col -e env.newman.$(ENV).json --reporters junit,cli --reporter-junit-export $(REPORT_PATH)$$col.xml || $(MAKE) app_stop && 1 ; \
	done ; \
