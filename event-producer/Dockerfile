FROM java:openjdk-8-jdk

# Install maven
RUN apt-get update && \
    apt-get install -y maven && \
    apt-get clean

# Download and install dependencies
RUN mkdir /producer-source
ADD ./pom.xml /producer-source
WORKDIR /producer-source
RUN mvn install

ADD ./src ./src
RUN mvn package

CMD /producer-source/target/kafka-example producer

