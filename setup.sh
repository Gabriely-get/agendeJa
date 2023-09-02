#!/bin/bash

echo "---------- update yum ----------"
sudo yum update

echo "---------- install ansible----------"
sudo amazon-linux-extras install ansible2 -y

echo "---------- run playbook ----------"
cd playbooks
ansible-playbook playbook.yml


echo "---------- install ----------"

echo "---------- install ----------"