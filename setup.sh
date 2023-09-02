#!/bin/bash

echo "---------- update yum ----------"
sudo yum update

echo "---------- install ansible----------"

if ! command -v ansible &> /dev/null; then
    echo "Ansible is not installed. Installing..."
    sudo amazon-linux-extras install ansible2 -y
else
    echo "Ansible is already installed."
fi

echo "---------- run playbook ----------"
cd playbooks
ansible-playbook playbook.yml


echo "---------- install ----------"

echo "---------- install ----------"