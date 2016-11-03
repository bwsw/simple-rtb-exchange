SHELL:= bash
LIQUIBASE_VERSION:=3.4.2
LIQUIBASE_FILE:=liquibase-$(LIQUIBASE_VERSION)-bin.tar.gz
POSTGRESQL_FILE:=postgresql-9.4.1211.jre6.jar

prepare:
	test -d liquibase || \
	(wget --no-cookies --progress=bar:force \
	    "https://github.com/liquibase/liquibase/releases/download/liquibase-parent-$(LIQUIBASE_VERSION)/$(LIQUIBASE_FILE)" && \
	mkdir liquibase && \
	tar -C liquibase -xzf $(LIQUIBASE_FILE) && \
	rm $(LIQUIBASE_FILE) )
	test -f "liquibase/lib/$(POSTGRESQL_FILE)" || wget -P liquibase/lib/ --no-cookies \
	    --progress=bar:force "https://jdbc.postgresql.org/download/$(POSTGRESQL_FILE)"

run: prepare
	liquibase/liquibase --logLevel=info --defaultsFile=$(env).liquibase.properties $(command)

clean:
	rm -rf liquibase
