#!/usr/bin/env bash

kubectl create secret generic mysql-pass --from-literal=password=12345678
kubectl create -f mysql/mysql-pv.yaml
kubectl create -f mysql/mysql-pvc.yaml
kubectl create -f mysql/mysql-deployment.yaml
