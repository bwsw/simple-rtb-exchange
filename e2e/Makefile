SHELL:=bash
ENV:=e2e
REPORT_PATH:=../target/test-reports/
LOGPATH:=../target/logs/
PORT:=8082
IMAGE_NAME:=rtb-exchange
NETWORK:=rtb-$(ENV)-network

app_start:
	mkdir -p $(LOGPATH)
	logpath=`realpath $(LOGPATH)`; \
	docker run --name rtb-exchange-$(ENV) -d -p $(PORT):8081 -v $$logpath:/opt/rtb-exchange/logs \
		--network=$(NETWORK) --network-alias=rtb-application -e env=$(ENV) $(IMAGE_NAME)
	./waitUp.sh localhost:$(PORT)

cleanup:
	docker rm -f -v rtb-exchange-$(ENV) || true && \
	docker rm -f -v rtb-database-$(ENV) || true && \
	docker network rm $(NETWORK) || true

prepare: cleanup
	(docker network create $(NETWORK) || true) && \
	$(MAKE) -C ../db prepare && \
	./../db/initialize.sh e2e && \
	docker run -d --volume=`realpath ../db/init`:/docker-entrypoint-initdb.d/ \
		--name rtb-database-$(ENV)  \
		--network=$(NETWORK) --network-alias=db postgres && \
	echo "waiting for db up" && \
	docker logs -f rtb-database-$(ENV)  | while read LOGLINE ; do \
		echo "$${LOGLINE}" | grep 'PostgreSQL init process complete' && \
		pkill -P $$$$ -f "docker logs" ; \
	done ; \
	docker run --rm --volume=`realpath ../db`:/workdir -w=/workdir --network=$(NETWORK) openjdk \
		liquibase/liquibase --logLevel=info --defaultsFile=$(ENV).liquibase.properties update ;
	docker run --rm --volume=`realpath ..`:/workdir -w=/workdir/$(ENV) --network=$(NETWORK) postgres \
		bash ./query.sh $(ENV) $(BIDDER_HOST) sql/cleanup.sql

execute: prepare app_start run

run:
	for row in `cat test_cases`; do \
		sqlfile=sql/`echo $$row | cut -d, -f1` && \
		col=`echo $$row | cut -d, -f2` && \
		docker run --rm --volume=`realpath ..`:/workdir -w=/workdir/$(ENV) \
			--network=$(NETWORK) postgres \
			bash ./query.sh $(ENV) $(BIDDER_HOST) $$sqlfile && \
		sleep 3 && \
		if docker run -t --volume="`pwd`:/workdir" --volume="`realpath $(REPORT_PATH)`":/workdir/reports -w="/workdir" --network=$(NETWORK) postman/newman_alpine33 \
		     run collections/$$col -e env.newman.$(ENV).json --reporters junit,cli \
		    --reporter-junit-export reports/$$col.xml ; then \
			echo "passed"; \
		else \
			$(MAKE) cleanup; \
			false; \
			exit; \
		fi; \
	done; \
	$(MAKE) cleanup
