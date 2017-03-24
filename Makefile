topic:
	docker-compose run --service-ports -d kafka
	sleep 5
	./kafka_2.10-0.10.1.0/bin/kafka-topics.sh \
		--create \
		--zookeeper 0.0.0.0:2181 \
		--replication-factor 1 --partitions 1 \
		--topic events ; echo "Listing all topics:" ; ./kafka_2.10-0.10.1.0/bin/kafka-topics.sh \
			--list \
			--zookeeper 0.0.0.0:2181
	docker-compose down

up:
	docker-compose up ; docker-compose down

down:
	docker-compose down

