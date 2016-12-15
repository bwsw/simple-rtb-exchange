SHELL:=bash
TEMP_FILE:=.temporary
DOCKER_NAME:=rtb-exchange-$(ENV)
IMAGE_NAME:=rtb-exchange

build:
	sbt assembly | tee $(TEMP_FILE)
	if tail -1 $(TEMP_FILE) | grep success > /dev/null; \
	then \
		app_path=`cat $(TEMP_FILE) | sed 's,\x1B\[[0-9;]*[a-zA-Z],,g' \
			| grep -E "^\[info\] Assembly up to date|^\[info] Packaging" \
			| sed -r -e 's/((^\[info\]\sAssembly\sup\sto\sdate:\s)|(^\[info\]\sPackaging\s))(.*?\.jar).*/\4/'`; \
		version=`sbt -no-colors version | tail -1 | sed -r -e 's/^\[info\]\s//'`; \
		app_path=`realpath --relative-to=. $$app_path`; \
		docker build -t $(IMAGE_NAME):$$version -t $(IMAGE_NAME):latest . --build-arg APP_PATH=$$app_path; \
	fi
	rm -f $(TEMP_FILE)

db_up: db_down
	docker run -d -p 5432:5432 --volume=`cd db/init && pwd`:/docker-entrypoint-initdb.d/ \
	--name rtb-database postgres && \
	echo "waiting for db up" && \
	docker logs -f rtb-database  | while read LOGLINE ; do \
		echo "$${LOGLINE}" | grep 'PostgreSQL init process complete' && \
		pkill -P $$$$ -f "docker logs" ; \
	done ; \
	$(MAKE) -C db run env=test command=update

db_down:
	docker rm -f -v rtb-database || true
