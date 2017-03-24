all: topic up

topic:
	docker-compose run --rm --service-ports make-topic sh ./bin/kafka-topics.sh \
		--create \
		--zookeeper kafka:2181 \
		--replication-factor 1 --partitions 1 \
		--topic events
	docker-compose down

up:
	docker-compose up --build ; docker-compose down

down:
	docker-compose down

build-riemann:
	docker-compose build riemann

build-producer:
	docker-compose build producer

