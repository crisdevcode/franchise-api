#!/bin/bash

sudo su
yum update -y
yum install -y docker
service docker start
usermod -a -G docker ec2-user

# Configure MongoDB URI
MONGODB_URI="${mongodb_uri}"

# Run the Docker Container
docker run -e SPRING_PROFILES_ACTIVE=prod \
  -e SPRING_DATA_MONGODB_URI="$MONGODB_URI" \
  -p 80:8080 cristiandevcode/franchise-api:0.0.1
