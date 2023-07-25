# POS (Point of sale)
Designed and developed a simple micro-service based POS application using spring  boot framework with JPA and Kafka for message queue  processing and MySQL for database storage. There are three micro-services - customer, sales and inventory. Customer micro-service is for 
registering new customer and their total sale. Inventory micro-service is for inventory management of products. Sales micro-service is for processing of order made by the customers. Synchronization between the micro-services are done using Kafka. 

# Run application using docker
Step 1: run the docker-compose file and run -
  docker compose -f .\pos-docker-compose.yml up
Step 2: go to every module and run - 
  docker build . -t customer:v1 
  docker build . -t sales:v1 
  docker build . -t inventory:v1 
Step 3: run the images - 
  docker run -p 9081:9081 -e DB_URL='con-db-cust:3306' KAFKA_CONSUMER_SERVER_URL='kafka:9092' customer:v1
  docker run -p 9082:9082 -e DB_URL='con-db-sales:3306' KAFKA_CONSUMER_SERVER_URL='kafka:9092' sales:v1
  docker run -p 9083:9083 -e DB_URL='con-db-inventory:3306' KAFKA_CONSUMER_SERVER_URL='kafka:9092' inventory:v1
# NOTE: please check for network if all containers in the same network, if not bring them on same 
# help link for network check: https://stackoverflow.com/questions/43904562/docker-how-to-find-the-network-my-container-is-in

                     
![image](https://github.com/babai-abhishek/Docker-Containerized-Spring-Boot-application/assets/17577922/f0e6e056-10c5-4d52-8529-a898d65b5af8)

