DB_DIR:=../db
TEST_CASES:=test_cases.txt
COLLECTIONS_DIR:=collections
BIDDER_SQL_DIR:=bidders
NEWMAN_ENV:=env.json
REPORT_FILE:=report.xml

NEWMAN_TEMP_ENV:=.temp_env.json
REPORT_PART:=.report_part.xml

TEMP_FILES:=$(NEWMAN_TEMP_ENV) $(REPORT_PART)

prepare:
	$(MAKE) -C $(DB_DIR) prepare
	$(MAKE) -C $(DB_DIR) run env=e2e command=update

#TODO: place run and stop rtb-exchange commands

run: prepare
	cat $(NEWMAN_ENV) | sed -r -e 's/\{\{rtb-exchange-host\}\}/$(rtb_host)/' > $(NEWMAN_TEMP_ENV)
	echo '<?xml version="1.0" encoding="UTF-8"?>' > $(REPORT_FILE)
	for row in `cat $(TEST_CASES)` ; do \
		sqlfile=$(BIDDER_SQL_DIR)/`echo $$row | cut -d, -f1` ; \
		./query.sh $(bidder_host) $$sqlfile ; \
		java -Dconfig.resource=application.e2e.conf \
			-jar ../target/scala-2.11/rtb-exchange-1.0.0-SNAPSHOT.jar & \
		sleep 3 ; \
		col=$(COLLECTIONS_DIR)/`echo $$row | cut -d, -f2` ; \
		newman run $$col -e $(NEWMAN_TEMP_ENV) --reporters junit --reporter-junit-export $(REPORT_PART) ; \
		kill -KILL `jps | grep rtb-exchange | cut -d' ' -f1` ; \
		tail $(REPORT_PART) -n +2 >> $(REPORT_FILE); \
	done
	rm -f $(TEMP_FILES)
