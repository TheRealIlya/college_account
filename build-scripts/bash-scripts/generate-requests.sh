#!/bin/bash

while true; do
  curl -H "Content-Type: application/json" \
    -d '{"login": "Admin", "password": "qwe"}' \
    -X POST http://localhost:8080/rest/authenticate
done
