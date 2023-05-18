#!/bin/bash

kubectl delete -f ../deployments/components.yml
kubectl delete -f ../deployments/elasticsearch-deployment.yaml
kubectl delete -f ../deployments/mongo-deployment.yaml
kubectl delete -f ../deployments/postgres-deployment.yaml
kubectl delete -f ../deployments/zookeeper-deployment.yaml
kubectl delete -f ../deployments/kafka-deployment.yaml
kubectl delete -f ../deployments/logstash-deployment.yaml
kubectl delete -f ../deployments/kibana-deployment.yaml
kubectl delete -f ../deployments/college-logger-deployment.yaml
kubectl delete -f ../deployments/college-core-deployment.yaml
kubectl delete -f ../deployments/college-auth-deployment.yaml
