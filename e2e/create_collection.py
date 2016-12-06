#!/usr/bin/env python

import sys
import json
import csv

request_directory = ''
collection_name = ''
collection_filename = ''
csvfile_name = 'collection.csv'

if len(sys.argv) < 3:
    print 'usage: ' + sys.argv[0] + \
          ' <collection_name> <requests_directory> [<filename_for_collection>] [<filename_with_csv>]'
    exit()
else:
    collection_name = sys.argv[1]
    request_directory = sys.argv[2] + '/'
    if len(sys.argv) >= 4:
        collection_filename = sys.argv[3]
        if len(sys.argv) >= 5:
            csvfile_name = sys.argv[4]

collection_items = []

csvfile = open(request_directory + csvfile_name, 'r')
r = csv.DictReader(csvfile)

for row in r:
    req_file = open(request_directory + row['request_file'], 'r')
    request = req_file.read()
    req_file.close()

    collection_item_name = row['collection_name']

    header = []
    content_type = row['content_type']
    if content_type:
        content_type_json = {
            'key': 'Content-Type',
            'value': content_type
        }
        header.append(content_type_json)

    tests = [
        'tests["Status code is 200"] = responseCode.code === 200;',
        'var jsonData = JSON.parse(responseBody);',
        'tests["Error code is correct"] = jsonData.error.code === ' +
        row['error_code'] + ';'
    ]

    if row['id']:
        id_test = 'tests["Id is correct"] = jsonData.id === "' + row['id'] + '"'
        tests.append(id_test)

    collection_item = {
        'name': collection_item_name,
        'event': [
            {
                'listen': 'test',
                'script': {
                    'type': 'text/javascript',
                    'exec': tests
                }
            }
        ],
        'request': {
            'url': '{{url}}',
            'method': 'POST',
            'header': header,
            'body': {
                'mode': 'raw',
                'raw': request
            }
        }
    }

    collection_items.append(collection_item)

collection = {
    'info': {
        'name': collection_name
    },
    'item': collection_items
}

if collection_filename:
    collection_file = open(collection_filename, 'w')
    collection_file.write(json.dumps(collection))
    collection_file.close()
else:
    print json.dumps(collection)
