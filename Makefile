
db_up: db_down
	./db/initialize.sh test && \
	docker run -d -p 5432:5432 --volume=`realpath db/init`:/docker-entrypoint-initdb.d/ \
	--name rtb-database postgres && \
	echo "waiting for db up" && \
	docker logs -f rtb-database  | while read LOGLINE ; do \
		echo "$${LOGLINE}" | grep 'PostgreSQL init process complete' && \
		pkill -P $$$$ -f "docker logs" ; \
	done ; \
	$(MAKE) -C db run env=test command=update

db_down:
	docker rm -f -v rtb-database || true
