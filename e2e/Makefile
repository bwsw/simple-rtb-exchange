ENV:=e2e
REPORT_PATH:=../target/test-reports/

prepare:
	$(MAKE) -C ../db run env=$(ENV) command=update && \
	./query.sh $(ENV) $(BIDDER_HOST) sql/cleanup.sql

execute: prepare run

run:
	mkdir -p ../target/workdir/logs
	$(MAKE) -C .. app_start ENV=e2e PORT=8082
	./waitUp.sh localhost:8082
	for row in `cat test_cases`; do \
		sqlfile=sql/`echo $$row | cut -d, -f1` && \
		col=`echo $$row | cut -d, -f2` && \
		./query.sh $(ENV) $(BIDDER_HOST) $$sqlfile && \
		sleep 3 && \
		if docker run --volume="`pwd`:/workdir" -w="/workdir" --network=rtb-ci-network -t postman/newman_alpine33 \
		     run collections/$$col -e env.newman.$(ENV).json --reporters junit,cli \
		    --reporter-junit-export $(REPORT_PATH)$$col.xml ; then \
			echo "passed"; \
		else \
			$(MAKE) app_stop; \
			false; \
			exit; \
		fi; \
	done; \
	$(MAKE) -C .. app_stop ENV=e2e
