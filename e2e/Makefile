DB_DIR:=../db

prepare:
	$(MAKE) -C $(DB_DIR) prepare
	$(MAKE) -C $(DB_DIR) run env=e2e command=update
