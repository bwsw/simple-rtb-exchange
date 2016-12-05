ENV:=e2e
BIDDER_HOST:=rtb-ci.z1.netpoint-dc.com:8083
REPORT_PATH:=../target/test-reports/

app_start: app_stop
	workdir=`cd ../target; pwd`/workdir &&\
	mkdir -p $$workdir && cp $(ASSEMBLY) $$workdir && \
	((docker network create rtb-ci-network && docker network connect --alias=db rtb-ci-network private-postgres) || true) && \
	docker login -u rtb-dev-team -p rtb-dev-team-password ci.bw-sw.com:5000 && \
	docker run --name rtb-exchange-e2e -d -p 8082:8081 -v $$workdir:/workdir -w=/workdir --network=rtb-ci-network  ci.bw-sw.com:5000/rtb-dev-team/oracle-java:8 /usr/bin/java -Dconfig.resource=application.$(ENV).conf -jar `basename $(ASSEMBLY)` && \
	./waitUp.sh localhost:8082

app_stop:
	(docker stop rtb-exchange-e2e && docker rm rtb-exchange-e2e) || true

prepare:
	$(MAKE) -C ../db run env=$(ENV) command=update

execute : prepare app_start run

run:
	for row in `cat test_cases`; do \
		sqlfile=sql/`echo $$row | cut -d, -f1` && \
		col=`echo $$row | cut -d, -f2` && \
		./query.sh e2e $(BIDDER_HOST) $$sqlfile && \
		sleep 3 && \
		newman run collections/$$col -e env.newman.$(ENV).json --reporters junit,cli --reporter-junit-export $(REPORT_PATH)$$col.xml || ($(MAKE) app_stop && exit 1) ; \
	done && \
	$(MAKE) app_stop
