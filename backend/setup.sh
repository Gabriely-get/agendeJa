#!/bin/bash

echo "-------- YUM UPDATE --------"

sudo yum update -y 

echo "---------- install Java ----------"

sudo yum install java-17-amazon-corretto-devel

echo "---------- setup MariaDB ----------"
sudo yum install -y mariadb-server
sudo systemctl enable mariadb
sudo systemctl start mariadb

echo "---------- install ----------"

echo "---------- install ----------"

echo "---------- install ----------"