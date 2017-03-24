FROM java:openjdk-8-jre

ENV SCALA_VERSION=2.10 \
    KAFKA_VERSION=0.10.1.0

RUN wget -q http://www.apache.org/dist/kafka/$KAFKA_VERSION/kafka_$SCALA_VERSION-$KAFKA_VERSION.tgz -O /kafka.tgz && \
    tar xfz /kafka.tgz && \
    rm /kafka.tgz

WORKDIR /kafka_$SCALA_VERSION-$KAFKA_VERSION

