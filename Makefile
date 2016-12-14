TEMP_FILE=.temporary
PORT=8081
LOG_PATH=logs
ENV=prod
DOCKER_NAME=rtb-exchange-$(ENV)

build:
	sbt assembly | tee $(TEMP_FILE)
	if tail -1 $(TEMP_FILE) | grep success > /dev/null; \
	then \
		app_path=`cat $(TEMP_FILE) | sed 's,\x1B\[[0-9;]*[a-zA-Z],,g' \
			| grep -E "^\[info\] Assembly up to date|^\[info] Packaging" \
			| sed -r -e 's/((^\[info\]\sAssembly\sup\sto\sdate:\s)|(^\[info\]\sPackaging\s))(.*?\.jar).*/\4/'`; \
		version=`sbt -no-colors version | tail -1 | sed -r -e 's/^\[info\]\s//'`; \
		app_path=`realpath --relative-to=. $$app_path`; \
		docker build -t rtb-exchange:$$version -t rtb-exchange:latest . --build-arg APP_PATH=$$app_path; \
	fi
	rm -f $(TEMP_FILE)

app_start: app_stop
	log_path=`realpath $(LOG_PATH)`; \
	docker run -d -p$(PORT):8081 --network=rtb-$(ENV)-network --network-alias=rtb-application \
		-v $$log_path:/data/logs --env env=$(ENV) --name=$(DOCKER_NAME) rtb-exchange

app_stop:
	(docker stop $(DOCKER_NAME) && docker rm $(DOCKER_NAME)) || true

test:
	sbt test

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
