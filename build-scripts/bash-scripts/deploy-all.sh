#!/bin/bash

kubectl apply -f ../deployments/components.yml
kubectl apply -f ../deployments/elasticsearch-deployment.yaml
kubectl apply -f ../deployments/mongo-deployment.yaml
kubectl apply -f ../deployments/postgres-deployment.yaml
kubectl apply -f ../deployments/zookeeper-deployment.yaml
kubectl apply -f ../deployments/kafka-deployment.yaml
kubectl apply -f ../deployments/logstash-deployment.yaml
kubectl apply -f ../deployments/kibana-deployment.yaml
kubectl apply -f ../deployments/college-logger-deployment.yaml
kubectl apply -f ../deployments/college-core-deployment.yaml
kubectl apply -f ../deployments/college-auth-deployment.yaml
