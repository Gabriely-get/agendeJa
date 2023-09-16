#!/bin/bash

echo "---------- update yum ----------"
sudo yum update -y

echo "---------- install ansible----------"

if ! command -v ansible &> /dev/null; then
    echo "Ansible is not installed. Installing..."
    sudo yum install epel-release -y
    sudo yum install ansible -y
else
    echo "Ansible is already installed."
fi

echo "---------- run playbook ----------"
cd playbooks
ansible-playbook playbook.yml


echo "---------- install ----------"

echo "---------- install ----------"
