ENV:=e2e
REPORT_PATH:=../target/test-reports/

app_start: app_stop
	docker network create rtb-ci-network || true; \
	docker network connect --alias=db rtb-ci-network private-postgres || true; \
	docker login -u rtb-dev-team -p rtb-dev-team-password ci.bw-sw.com:5000 && \
	mkdir -p ../target/workdir && \
	workdir=`cd ../target/workdir && pwd` && \
	cp $(ASSEMBLY) $$workdir && \
	docker run --name rtb-exchange-e2e -d -p 8082:8081 -v $$workdir:/workdir -w=/workdir \
	    --network=rtb-ci-network  ci.bw-sw.com:5000/rtb-dev-team/oracle-java:8 \
	    /usr/bin/java -Dconfig.resource=application.$(ENV).conf -jar `basename $(ASSEMBLY)` && \
	./waitUp.sh localhost:8082

app_stop:
	(docker stop rtb-exchange-e2e && docker rm rtb-exchange-e2e) || true

prepare:
	$(MAKE) -C ../db run env=$(ENV) command=update && \
	./query.sh $(ENV) $(BIDDER_HOST) sql/cleanup.sql

execute : prepare app_start run

run:
	for row in `cat test_cases`; do \
		sqlfile=sql/`echo $$row | cut -d, -f1` && \
		col=`echo $$row | cut -d, -f2` && \
		./query.sh $(ENV) $(BIDDER_HOST) $$sqlfile && \
		sleep 3 && \
		if newman run collections/$$col -e env.newman.$(ENV).json --reporters junit,cli --reporter-junit-export $(REPORT_PATH)$$col.xml ; then \
			echo "passed"; \
		else \
			$(MAKE) app_stop; \
			false; \
			exit; \
		fi; \
	done; \
	$(MAKE) app_stop
