TEMP_FILE=.temporary

build:
	sbt assembly | tee $(TEMP_FILE)
	if tail -1 $(TEMP_FILE) | grep success > /dev/null; \
	then \
		app_path=`cat $(TEMP_FILE) | sed 's,\x1B\[[0-9;]*[a-zA-Z],,g' \
			| grep -E "^\[info\] Assembly up to date|^\[info] Packaging" \
			| sed -r -e 's/((^\[info\]\sAssembly\sup\sto\sdate:\s)|(^\[info\]\sPackaging\s))(.*?\.jar).*/\4/'`; \
		version=`sbt -no-colors version | tail -1 | sed -r -e 's/^\[info\]\s//'`; \
		app_path=`realpath --relative-to=. $$app_path`; \
		docker build -t rtb-exchange:$$version . --build-arg APP_PATH=$$app_path; \
	fi
	rm -f $(TEMP_FILE)
