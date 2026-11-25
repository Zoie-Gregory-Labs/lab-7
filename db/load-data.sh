#!/bin/bash
set -e

mysql -uroot -p"$MYSQL_ROOT_PASSWORD" employees < /tmp/load_departments.dump
mysql -uroot -p"$MYSQL_ROOT_PASSWORD" employees < /tmp/load_employees.dump
mysql -uroot -p"$MYSQL_ROOT_PASSWORD" employees < /tmp/load_dept_emp.dump
mysql -uroot -p"$MYSQL_ROOT_PASSWORD" employees < /tmp/load_dept_manager.dump
mysql -uroot -p"$MYSQL_ROOT_PASSWORD" employees < /tmp/load_titles.dump
mysql -uroot -p"$MYSQL_ROOT_PASSWORD" employees < /tmp/load_salaries1.dump
mysql -uroot -p"$MYSQL_ROOT_PASSWORD" employees < /tmp/load_salaries2.dump
mysql -uroot -p"$MYSQL_ROOT_PASSWORD" employees < /tmp/load_salaries3.dump
