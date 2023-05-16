#!/bin/bash

kubectl delete -f ../deployments/01-elasticsearch-deployment.yaml
kubectl delete -f ../deployments/02-mongo-deployment.yaml
kubectl delete -f ../deployments/03-postgres-deployment.yaml
kubectl delete -f ../deployments/04-zookeeper-deployment.yaml
kubectl delete -f ../deployments/05-kafka-deployment.yaml
kubectl delete -f ../deployments/06-logstash-deployment.yaml
kubectl delete -f ../deployments/07-kibana-deployment.yaml
kubectl delete -f ../deployments/08-college-logger-deployment.yaml
kubectl delete -f ../deployments/09-college-core-deployment.yaml
kubectl delete -f ../deployments/10-college-auth-deployment.yaml
