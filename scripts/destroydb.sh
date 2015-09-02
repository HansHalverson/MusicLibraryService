#!/bin/bash

if mysql.server start; then
	echo "DROP DATABASE music_library;" | mysql -u root;
else
	echo "Could not start mysql"
fi
