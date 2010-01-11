#!/bin/bash

cd `dirname $0`

if [ -e caffeine_db ];
then
	echo "caffeine_db exists. Removing"
	rm caffeine_db
fi

echo "Creating caffeine_db..."
sqlite3 caffeine_db < importcmd.txt

if [ ! -d ../assets ];
then
	echo "Assets folder doesn't exist. Creating"
	mkdir ../assets
fi

echo "Copying caffeine_db to assets folder..."
cp caffeine_db ../assets/
