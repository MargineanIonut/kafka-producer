# Avro Kafka Producer with AKHQ and Schema Registry

This setup provides a Kafka environment with Zookeeper, Schema Registry, and AKHQ for managing Kafka topics and schemas.

## Prerequisites

Ensure Docker and Docker Compose are installed on your system.

## Setup Instructions

### Step 1: Create Docker Network

Create an external Docker network to allow all services to communicate.

```bash
docker network create kafka-network
Run the Kafka and Zookeeper services using Docker Compose.
docker compose -f <kafka-compose-file.yml> up -d
docker compose -f <schema-registry-compose-file.yml> up -d
docker network connect kafka-network schema
docker network connect kafka-network akhq
http://localhost:8082
docker exec -it kafka /bin/bash
kafka-topics --create --topic my-topic --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1
kafka-topics --list --bootstrap-server localhost:9092
kafka-consumer-groups --bootstrap-server localhost:9092 --list
kafka-topics --describe --topic my-topic --bootstrap-server localhost:9092
mvn clean quarkus:dev
