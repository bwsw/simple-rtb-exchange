
db_up: db_down
	docker run -d -p 5432:5432 --volume=`cd db/init && pwd`:/docker-entrypoint-initdb.d/ \
	 --name rtb-database postgres && \
	sleep 20 && \
	$(MAKE) -C db run env=test command=update ; \

db_down:
	docker rm -f rtb-database || true
