ENV:=e2e
REPORT_PATH:=../target/test-reports/

cleanup:
	docker rm -f -v rtb-exchange-e2e || true && \
	docker rm -f -v rtb-database-e2e || true && \
	docker network rm rtb-e2e-network || true

prepare: cleanup
	(docker network create rtb-e2e-network || true) && \
	$(MAKE) -C ../db prepare && \
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

execute: prepare run

run:
	mkdir -p ../target/workdir/logs
	$(MAKE) -C .. app_start ENV=e2e PORT=8082
	./waitUp.sh localhost:8082
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
	$(MAKE) -C .. app_stop ENV=e2e
	$(MAKE) cleanup
