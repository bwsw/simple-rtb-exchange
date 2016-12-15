ENV:=e2e
REPORT_PATH:=../target/test-reports/

app_start:
	mkdir -p ../target/workdir && \
	workdir=`cd ../target/workdir && pwd` && \
	cp $(ASSEMBLY) $$workdir && \
	docker run --name rtb-exchange-e2e -d -p 8082:8081 -v $$workdir:/workdir -w=/workdir \
	    --network=rtb-e2e-network --network-alias=rtb-application ci.bw-sw.com:5000/rtb-dev-team/oracle-java:8 \
	    /usr/bin/java -Dconfig.resource=application.$(ENV).conf -jar `basename $(ASSEMBLY)` && \
	./waitUp.sh localhost:8082

cleanup:
	docker rm -f -v rtb-exchange-e2e || true && \
	docker rm -f -v rtb-database-e2e || true && \
	docker network rm rtb-e2e-network || true

prepare: cleanup
	(docker network create rtb-e2e-network || true) && \
	$(MAKE) -C ../db prepare && \
	./../db/initialize.sh e2e && \
	docker run -d --volume=`cd ../db/init && pwd`:/docker-entrypoint-initdb.d/ \
		--name rtb-database-e2e  \
		--network=rtb-e2e-network --network-alias=db postgres && \
	echo "waiting for db up" && \
	docker logs -f rtb-database-e2e  | while read LOGLINE ; do \
		echo "$${LOGLINE}" | grep 'PostgreSQL init process complete' && \
		pkill -P $$$$ -f "docker logs" ; \
	done ; \
	docker run --rm --volume=`cd ../db && pwd`:/workdir -w=/workdir --network=rtb-e2e-network openjdk \
		liquibase/liquibase --logLevel=info --defaultsFile=$(ENV).liquibase.properties update ;
	docker run --rm --volume=`cd .. && pwd`:/workdir -w=/workdir/e2e --network=rtb-e2e-network postgres \
		bash ./query.sh $(ENV) $(BIDDER_HOST) sql/cleanup.sql

execute: prepare app_start run

run:
	for row in `cat test_cases`; do \
		sqlfile=sql/`echo $$row | cut -d, -f1` && \
		col=`echo $$row | cut -d, -f2` && \
		docker run --rm --volume=`cd .. && pwd`:/workdir -w=/workdir/e2e \
		--network=rtb-e2e-network postgres \
		bash ./query.sh $(ENV) $(BIDDER_HOST) $$sqlfile && \
		sleep 3 && \
		if docker run --volume="`pwd`:/workdir" -w="/workdir" --network=rtb-e2e-network -t postman/newman_alpine33 \
		     run collections/$$col -e env.newman.$(ENV).json --reporters junit,cli \
		    --reporter-junit-export $(REPORT_PATH)$$col.xml ; then \
			echo "passed"; \
		else \
			$(MAKE) cleanup; \
			false; \
			exit; \
		fi; \
	done; \
	$(MAKE) cleanup
