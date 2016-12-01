DB_DIR:=../db
TEST_CASES:=test_cases.txt
COLLECTIONS_DIR:=collections
BIDDER_SQL_DIR:=bidders
NEWMAN_ENV:=env.json

prepare:
	$(MAKE) -C $(DB_DIR) prepare
	$(MAKE) -C $(DB_DIR) run env=e2e command=update

run: prepare
	echo '<?xml version="1.0" encoding="UTF-8"?>' > report.xml
	for row in `cat $(TEST_CASES)` ; do \
		col=$(COLLECTIONS_DIR)/`echo $$row | cut -d, -f2` ; \
		sqlfile=$(BIDDER_SQL_DIR)/`echo $$row | cut -d, -f2` ; \
		newman run $$col -e $(NEWMAN_ENV) --reporters junit --reporter-junit-export tmp.xml ; \
		tail tmp.xml -n +2 >> report.xml ; \
	done
	rm -f tmp.xml

