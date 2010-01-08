#!/bin/bash

cd `dirname $0`
rm caffeine_db
sqlite3 caffeine_db < importcmd.txt
mkdir ../assets/
cp caffeine_db ../assets/